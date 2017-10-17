package org.net.perorin.cevop2.contoroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.net.perorin.cevio.SasaraDriver;
import org.net.perorin.cevop2.exception.CantMergeImageException;
import org.net.perorin.cevop2.exception.FaildChangeCastException;
import org.net.perorin.cevop2.model.Model;
import org.net.perorin.cevop2.model.Preset;
import org.net.perorin.cevop2.model.PresetUtilty;
import org.net.perorin.cevop2.view.ConfirmDialog;
import org.net.perorin.cevop2.view.MessageDialog;
import org.net.perorin.cevop2.view.View;
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

	private View view;

	public void run() {
		view = new View(this);
		this.init();
		view.frame.setVisible(true);
	}

	public void init() {
		try {
			Model.initData(view);
			Model.initTemp(view);
		} catch (CantMergeImageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void voiceListen() {
		try {
			Model.voiceListen(view);
			Model.temporarySave(view);
		} catch (HwndNotFoundException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog msg = new MessageDialog(view, "ボイスロイドのウィンドウが見つかりませんでした。") {
				@Override
				public void processOK() {
					String buf1[] = e.getMessage().split(Pattern.quote("["));
					String buf2[] = buf1[1].split(Pattern.quote("]"));
					ConfirmDialog con = new ConfirmDialog(view, buf2[0] + "を起動しますか？") {
						public void processYes() {
							bootVoiceroid((String) view.comboCast.getSelectedItem());
						};
					};
					con.frame.setVisible(true);
				}
			};
			msg.frame.setVisible(true);
		}
	}

	public void voiceSave() {
		try {
			File file = Model.voiceSave(view);
			view.lblVoiceFile.setData(file);
			if (view.chckbxTextSave.isCheck) {
				Model.voiceTextSave(view);
			}
			Model.temporarySave(view);
		} catch (HwndNotFoundException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog msg = new MessageDialog(view, "ボイスロイドのウィンドウが見つかりませんでした。") {
				@Override
				public void processOK() {
					String buf1[] = e.getMessage().split(Pattern.quote("["));
					String buf2[] = buf1[1].split(Pattern.quote("]"));
					ConfirmDialog con = new ConfirmDialog(view, buf2[0] + "を起動しますか？") {
						public void processYes() {
							bootVoiceroid((String) view.comboCast.getSelectedItem());
						};
					};
					con.frame.setVisible(true);
				}
			};
			msg.frame.setVisible(true);
		} catch (FailedVoiceSaveException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "音声の保存に失敗しました。");
		} catch (IOException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "テキストの保存に失敗しました。");
		}
	}

	public void fontColor() {
		Model.colorSelect(this,view, 0);
	}

	public void edgeColor() {
		Model.colorSelect(this,view, 1);
	}

	public void subtitlesPreview() {
		BufferedImage buf = Model.subtitlesCreate(view);
		Model.subtitlesPreview(view, buf);
		Model.temporarySave(view);
	}

	public void subtitlesSave() {
		try {
			BufferedImage buf = Model.subtitlesCreate(view);
			Model.subtitlesPreview(view, buf);
			File file = Model.subtitlesSave(view, buf);
			view.lblSubtitlesFile.setData(file);
			Model.temporarySave(view);
		} catch (IOException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "字幕の保存に失敗しました。");
		}
	}

	public void allSave() {
		this.subtitlesSave();
		this.voiceSave();
	}

	public void bootCevio() {
		SasaraDriver driver = new SasaraDriver();
		driver.setDriverPath("./META-INF/exe/cevio/");
		driver.listen();
	}

	public void bootVoiceroid(String castName) {
		try {
			switch (castName) {
			case "結月ゆかり":
				Runtime.getRuntime().exec(Model.yukariPath);
				break;

			case "弦巻マキ":
				Runtime.getRuntime().exec(Model.makiPath);
				break;

			case "東北ずん子":
				Runtime.getRuntime().exec(Model.zunkoPath);
				break;

			case "琴葉茜":
				Runtime.getRuntime().exec(Model.akanePath);
				break;

			case "琴葉葵":
				Runtime.getRuntime().exec(Model.aoiPath);
				break;

			case "吉田くん":
				Runtime.getRuntime().exec(Model.yoshidaPath);
				break;

			case "京町セイカ":
				Runtime.getRuntime().exec(Model.seikaPath);
				break;

			case "東北きりたん":
				Runtime.getRuntime().exec(Model.kiritanPath);
				break;

			default:
				break;
			}
		} catch (IOException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "起動に失敗しました。");
			e.printStackTrace();
		}
	}

	public void castChange() {
		try {
			Model.changeCastImage(view);
			Model.changeParameterSet(view);
			List<Preset> list = PresetUtilty.loadPreset((String) view.comboCast.getSelectedItem());
			Model.parameterUpdate(view, list.get(view.selectingPresetNo));
			Model.btnPresetUpdate(view, list);
		} catch (FaildChangeCastException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "キャストの変更に失敗しました。");
		} catch (IOException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog.showDialog(view, "プリセットの読み込みに失敗しました。");
		}
	}

	private static void config(View v) {

	}

	public void presetSave() {
		Model.presetSave(view);
	}

	public void voiceroidActive() {
		try {
			Model.voiceroidActive(view);
		} catch (HwndNotFoundException e) {
			Logger.error("./META-INF/log/", e.getStackTrace());
			MessageDialog msg = new MessageDialog(view, "ボイスロイドのウィンドウが見つかりませんでした。") {
				@Override
				public void processOK() {
					String buf1[] = e.getMessage().split(Pattern.quote("["));
					String buf2[] = buf1[1].split(Pattern.quote("]"));
					ConfirmDialog con = new ConfirmDialog(view, buf2[0] + "を起動しますか？") {
						public void processYes() {
							bootVoiceroid((String) view.comboCast.getSelectedItem());
						};
					};
					con.frame.setVisible(true);
				}
			};
			msg.frame.setVisible(true);
		}
	}

}
