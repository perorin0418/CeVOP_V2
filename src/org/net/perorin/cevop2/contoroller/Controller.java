package org.net.perorin.cevop2.contoroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.net.perorin.cevop2.exception.CantMergeImageException;
import org.net.perorin.cevop2.exception.FaildChangeCastException;
import org.net.perorin.cevop2.exception.ImageNotFoundException;
import org.net.perorin.cevop2.model.Model;
import org.net.perorin.cevop2.model.Preset;
import org.net.perorin.cevop2.model.PresetUtilty;
import org.net.perorin.cevop2.view.ConfirmDialog;
import org.net.perorin.cevop2.view.MessageDialog;
import org.net.perorin.cevop2.view.View;
import org.net.perorin.exception.CVException;
import org.net.perorin.exception.FailedVoiceSaveException;
import org.net.perorin.exception.HwndNotFoundException;
import org.net.perorin.toolkit.Logger;

public class Controller {

	public static final int RUN = 0;
	public static final int INIT = 1;
	public static final int CONFIG = 2;
	public static final int VOICE_LISTEN = 3;
	public static final int VOICE_SAVE = 4;
	public static final int FONT_COLOR = 5;
	public static final int EDGE_COLOR = 6;
	public static final int SUBTITLES_PREVIEW = 7;
	public static final int SUBTITLES_SAVE = 8;
	public static final int OUT_PATH = 9;
	public static final int PRESET = 10;
	public static final int ALL_SAVE = 11;
	public static final int BOOT_CEVIO = 12;
	public static final int BOOT_YUKARI = 13;
	public static final int BOOT_MAKI = 14;
	public static final int BOOT_ZUNKO = 15;
	public static final int BOOT_AKANE = 16;
	public static final int BOOT_AOI = 17;
	public static final int CAST_CHANGE = 18;
	public static final int FONT_CHANGE = 19;
	public static final int PRESET_SAVE = 20;
	public static final int VOICEROID_ACTIVE = 21;

	public static void call(View v, int command) {
		try {
			if (command < 0) {
				MessageDialog.showDialog(v, "原因不明のエラーが発生しました。");
			} else
				switch (command) {
				case RUN:
					run();
					break;

				case INIT:
					init(v);
					break;

				case CONFIG:
					config(v);
					break;

				case VOICE_LISTEN:
					voiceListen(v);
					break;

				case VOICE_SAVE:
					voiceSave(v);
					break;

				case FONT_COLOR:
					fontColor(v);
					break;

				case EDGE_COLOR:
					edgeColor(v);
					break;

				case SUBTITLES_PREVIEW:
					subtitlesPreview(v);
					break;

				case SUBTITLES_SAVE:
					subtitlesSave(v);
					break;

				case ALL_SAVE:
					allSave(v);
					break;

				case BOOT_CEVIO:
					boot(0);
					break;

				case BOOT_YUKARI:
					boot(4);
					break;

				case BOOT_MAKI:
					boot(5);
					break;

				case BOOT_ZUNKO:
					boot(6);
					break;

				case BOOT_AKANE:
					boot(7);
					break;

				case BOOT_AOI:
					boot(8);
					break;

				case CAST_CHANGE:
					castChange(v);
					break;

				case PRESET_SAVE:
					presetSave(v);
					break;

				case VOICEROID_ACTIVE:
					voiceroidActive(v);
					break;

				default:
					MessageDialog.showDialog(v, "原因不明のエラーが発生しました。");
					System.exit(-1);
					break;
				}
		} catch (CantMergeImageException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "アイコンの初期化に失敗しました。ログを確認してください。");

		} catch (ImageNotFoundException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "画像の読み込みに失敗しました。ログを確認してください。");

		} catch (HwndNotFoundException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog msg = new MessageDialog(v, "ボイスロイドのウィンドウが見つかりませんでした。") {
				@Override
				public void processOK() {
					String buf1[] = e.getMessage().split(Pattern.quote("["));
					String buf2[] = buf1[1].split(Pattern.quote("]"));
					ConfirmDialog con = new ConfirmDialog(v, buf2[0] + "を起動しますか？") {
						public void processYes() {
							try {
								boot(v.comboCast.getSelectedIndex());
							} catch (IOException e) {
								Logger.error("./log/", e.getStackTrace());
								MessageDialog.showDialog(v, "起動に失敗しました。");
							}
						};
					};
					con.frame.setVisible(true);
				}
			};
			msg.frame.setVisible(true);

		} catch (FailedVoiceSaveException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "音声の保存に失敗しました。");

		} catch (FaildChangeCastException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "キャストの変更に失敗しました。");

		} catch (IOException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "読み込みに失敗しました。");

		} catch (CVException e) {
			Logger.error("./log/", e.getStackTrace());
			MessageDialog.showDialog(v, "画像処理中にエラーが発生しました。ログを確認してください。");

		}
		//			catch (Exception e) {
		//			Logger.error("./log/", e.getStackTrace());
		//			MessageDialog.showDialog(v, "原因不明のエラーが発生しました。");
		//		}
	}

	private static void run() throws CVException {
		View.run();
	}

	private static void init(View v) throws CantMergeImageException, ImageNotFoundException, IOException {
		Model.initData(v);
		Model.initTemp(v);
	}

	private static void voiceListen(View v) throws HwndNotFoundException {
		Model.voiceListen(v);
		Model.temporarySave(v);
	}

	private static void voiceSave(View v) throws HwndNotFoundException, FailedVoiceSaveException, IOException {
		File file = Model.voiceSave(v);
		v.lblVoiceFile.setData(file);
		if (v.chckbxTextSave.isCheck) {
			Model.voiceTextSave(v);
		}
		Model.temporarySave(v);
	}

	private static void fontColor(View v) {
		Model.colorSelect(v, 0);
	}

	private static void edgeColor(View v) {
		Model.colorSelect(v, 1);
	}

	private static void subtitlesPreview(View v) {
		BufferedImage buf = Model.subtitlesCreate(v);
		Model.subtitlesPreview(v, buf);
		Model.temporarySave(v);
	}

	private static void subtitlesSave(View v) throws IOException {
		BufferedImage buf = Model.subtitlesCreate(v);
		Model.subtitlesPreview(v, buf);
		File file = Model.subtitlesSave(v, buf);
		v.lblSubtitlesFile.setData(file);
		Model.temporarySave(v);
	}

	private static void allSave(View v) throws IOException, HwndNotFoundException, FailedVoiceSaveException {
		BufferedImage buf = Model.subtitlesCreate(v);
		Model.subtitlesPreview(v, buf);
		Model.subtitlesSave(v, buf);
		Model.voiceSave(v);
		if (v.chckbxTextSave.isCheck) {
			Model.voiceTextSave(v);
		}
		Model.temporarySave(v);
	}

	private static void boot(int castID) throws IOException {
		switch (castID) {
		case 0:
			Runtime.getRuntime().exec(Model.cevioPath);
			break;

		case 4:
			Runtime.getRuntime().exec(Model.yukariPath);
			break;

		case 5:
			Runtime.getRuntime().exec(Model.makiPath);
			break;

		case 6:
			Runtime.getRuntime().exec(Model.zunkoPath);
			break;

		case 7:
			Runtime.getRuntime().exec(Model.akanePath);
			break;

		case 8:
			Runtime.getRuntime().exec(Model.aoiPath);
			break;

		case 9:
			Runtime.getRuntime().exec(Model.yoshidaPath);
			break;

		case 10:
			Runtime.getRuntime().exec(Model.seikaPath);
			break;

		case 11:
			Runtime.getRuntime().exec(Model.kiritanPath);
			break;

		default:
			break;
		}
	}

	private static void castChange(View v) throws FaildChangeCastException, IOException {
		Model.changeCastImage(v);
		Model.changeParameterSet(v);
		List<Preset> list = PresetUtilty.loadPreset((String) v.comboCast.getSelectedItem());
		Model.parameterUpdate(v, list.get(v.selectingPresetNo));
		Model.btnPresetUpdate(v, list);
	}

	private static void config(View v) {

	}

	private static void presetSave(View v) {
		Model.presetSave(v);
	}

	private static void voiceroidActive(View v) throws HwndNotFoundException {
		Model.voiceroidActive(v);
	}

}
