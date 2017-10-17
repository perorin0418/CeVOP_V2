package org.net.perorin.cevop2.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.net.perorin.cevio.CevioDriver;
import org.net.perorin.cevio.IaDriver;
import org.net.perorin.cevio.OneDriver;
import org.net.perorin.cevio.SasaraDriver;
import org.net.perorin.cevio.TakahashiDriver;
import org.net.perorin.cevio.TsudumiDriver;
import org.net.perorin.cevop2.contoroller.Controller;
import org.net.perorin.cevop2.exception.CantMergeImageException;
import org.net.perorin.cevop2.exception.FaildChangeCastException;
import org.net.perorin.cevop2.view.ColorSelector;
import org.net.perorin.cevop2.view.MessageDialog;
import org.net.perorin.cevop2.view.PresetSaver;
import org.net.perorin.cevop2.view.View;
import org.net.perorin.cv.CV;
import org.net.perorin.cv.CVImage;
import org.net.perorin.exception.FailedVoiceSaveException;
import org.net.perorin.exception.HwndNotFoundException;
import org.net.perorin.toolkit.ArrayUtils;
import org.net.perorin.voiceroid.Voiceroid;

public class Model {

	private static HashMap<String, CharacterIcon> icons;

	public static String cevioPath;
	public static String yukariPath;
	public static String makiPath;
	public static String zunkoPath;
	public static String akanePath;
	public static String aoiPath;
	public static String yoshidaPath;
	public static String seikaPath;
	public static String kiritanPath;

	public static void initData(View v) throws CantMergeImageException, IOException {
		icons = new HashMap<String, CharacterIcon>();
		icons.put(SasaraDriver.CAST_NAME, new CharacterIcon("sasara"));
		icons.put(TsudumiDriver.CAST_NAME, new CharacterIcon("tsudumi"));
		icons.put(TakahashiDriver.CAST_NAME, new CharacterIcon("takahashi"));
		icons.put(OneDriver.CAST_NAME, new CharacterIcon("one"));
		icons.put(IaDriver.CAST_NAME, new CharacterIcon("ia"));
		icons.put(Voiceroid.YUKARI_NAME, new CharacterIcon("yukari"));
		icons.put(Voiceroid.MAKI_NAME, new CharacterIcon("maki"));
		icons.put(Voiceroid.ZUNKO_NAME, new CharacterIcon("zunko"));
		icons.put(Voiceroid.AKANE_NAME, new CharacterIcon("akane"));
		icons.put(Voiceroid.AOI_NAME, new CharacterIcon("aoi"));
		icons.put(Voiceroid.YOSHIDA_NAME, new CharacterIcon("yoshida"));
		icons.put(Voiceroid.SEIKA_NAME, new CharacterIcon("seika"));
		icons.put(Voiceroid.KIRITAN_NAME, new CharacterIcon("kiritan"));

		v.lblCast.setIcon(icons.get(SasaraDriver.CAST_NAME).getImage());

		ArrayList<String> casts = new ArrayList<String>();
		casts.add(SasaraDriver.CAST_NAME);
		casts.add(TsudumiDriver.CAST_NAME);
		casts.add(TakahashiDriver.CAST_NAME);
		casts.add(OneDriver.CAST_NAME);
		casts.add(IaDriver.CAST_NAME);
		casts.add(Voiceroid.YUKARI_NAME);
		casts.add(Voiceroid.MAKI_NAME);
		casts.add(Voiceroid.AKANE_NAME);
		casts.add(Voiceroid.AOI_NAME);
		casts.add(Voiceroid.YOSHIDA_NAME);
		casts.add(Voiceroid.SEIKA_NAME);
		casts.add(Voiceroid.KIRITAN_NAME);
		for (String buf : casts) {
			v.comboCast.addItem(buf);
		}
		v.comboCast.setSelectedIndex(0);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font fonts[] = ge.getAllFonts();
		String[] fontCombData = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			fontCombData[i] = fonts[i].getName();
		}
		for (String buf : fontCombData) {
			v.comboFont.addItem(buf);
		}
		v.comboFont.setSelectedIndex(ArrayUtils.getIndex(fontCombData, "ＭＳ ゴシック"));

		List<Preset> presets = PresetUtilty.loadPreset(SasaraDriver.CAST_NAME);
		for (int i = 0; i < 12; i++) {
			v.btnPreset.get(i).setText(presets.get(i).getName());
		}
		parameterUpdate(v, presets.get(0));
	}

	public static void initTemp(View v) {
		Temporary tmp = new Temporary();
		tmp = Temporary.load();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font fonts[] = ge.getAllFonts();
		String[] fontCombData = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			fontCombData[i] = fonts[i].getName();
		}
		v.font = getFont(tmp.getFont());
		v.comboFont.setSelectedIndex(ArrayUtils.getIndex(fontCombData, tmp.getFont()));
		v.txtfldFontSize.setText(tmp.getFontSize());
		v.txtfldEdgeSize.setText(tmp.getFontFrameSize());
		v.txtfldOutPath.setText(tmp.getPath());
		//		v.txtArea.setFont(new Font(v.txtArea.getFont().toString(), Integer.parseInt(tmp.get(4)), v.txtArea.getFont().getStyle()));
		cevioPath = tmp.getCevioPath();
		yukariPath = tmp.getYukariPath();
		makiPath = tmp.getMakiPath();
		zunkoPath = tmp.getZunkoPath();
		akanePath = tmp.getAkanePath();
		aoiPath = tmp.getAoiPath();
		yoshidaPath = tmp.getYoshidaPath();
		seikaPath = tmp.getSeikaPath();
		kiritanPath = tmp.getKiritanPath();
	}

	public static void voiceListen(View v) throws HwndNotFoundException {
		String castName = (String) v.comboCast.getSelectedItem();
		int volume = Integer.parseInt(v.txtfldSize.getText());
		int speed = Integer.parseInt(v.txtfldSpeed.getText());
		int tone = Integer.parseInt(v.txtfldTone.getText());
		int toneScale = Integer.parseInt(v.txtfldToneScale.getText());

		String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		CevioDriver driver = null;

		switch (castName) {
		case SasaraDriver.CAST_NAME:
			driver = new SasaraDriver();
			setCevioParameters(v, driver);
			driver.listen();
			break;

		case TsudumiDriver.CAST_NAME:
			driver = new TsudumiDriver();
			setCevioParameters(v, driver);
			driver.listen();
			break;

		case TakahashiDriver.CAST_NAME:
			driver = new TakahashiDriver();
			setCevioParameters(v, driver);
			driver.listen();
			break;

		case OneDriver.CAST_NAME:
			driver = new OneDriver();
			setCevioParameters(v, driver);
			driver.listen();
			break;

		case IaDriver.CAST_NAME:
			driver = new IaDriver();
			setCevioParameters(v, driver);
			driver.listen();
			break;

		case Voiceroid.YUKARI_NAME:
		case Voiceroid.MAKI_NAME:
		case Voiceroid.ZUNKO_NAME:
		case Voiceroid.AKANE_NAME:
		case Voiceroid.AOI_NAME:
		case Voiceroid.YOSHIDA_NAME:
		case Voiceroid.SEIKA_NAME:
		case Voiceroid.KIRITAN_NAME:
			Voiceroid voiceroid = new Voiceroid(castName);
			voiceroid.setParameters(volume, speed, tone, toneScale);
			voiceroid.voiceListen(voiceText);
			break;

		default:
			break;
		}
	}

	public static File voiceSave(View v) throws HwndNotFoundException, FailedVoiceSaveException {
		String castName = (String) v.comboCast.getSelectedItem();
		int volume = Integer.parseInt(v.txtfldSize.getText());
		int speed = Integer.parseInt(v.txtfldSpeed.getText());
		int tone = Integer.parseInt(v.txtfldTone.getText());
		int toneScale = Integer.parseInt(v.txtfldToneScale.getText());

		String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		String outPath = v.txtfldOutPath.getText();
		File file = new File("");
		CevioDriver driver = null;

		switch (castName) {
		case SasaraDriver.CAST_NAME:
			driver = new SasaraDriver();
			file = setCevioParameters(v, driver);
			driver.save();
			break;

		case TsudumiDriver.CAST_NAME:
			driver = new TsudumiDriver();
			file = setCevioParameters(v, driver);
			driver.save();
			break;

		case TakahashiDriver.CAST_NAME:
			driver = new TakahashiDriver();
			file = setCevioParameters(v, driver);
			driver.save();
			break;

		case OneDriver.CAST_NAME:
			driver = new OneDriver();
			file = setCevioParameters(v, driver);
			driver.save();
			break;

		case IaDriver.CAST_NAME:
			driver = new IaDriver();
			file = setCevioParameters(v, driver);
			driver.save();
			break;

		case Voiceroid.YUKARI_NAME:
		case Voiceroid.MAKI_NAME:
		case Voiceroid.ZUNKO_NAME:
		case Voiceroid.AKANE_NAME:
		case Voiceroid.AOI_NAME:
		case Voiceroid.YOSHIDA_NAME:
		case Voiceroid.SEIKA_NAME:
		case Voiceroid.KIRITAN_NAME:
			Voiceroid voiceroid = new Voiceroid(castName);
			voiceroid.setParameters(volume, speed, tone, toneScale);
			file = voiceroid.voiceSave(voiceText, outPath);
			break;

		default:
			break;
		}

		return file;
	}

	private static File setCevioParameters(View v, CevioDriver driver) {
		int volume = Integer.parseInt(v.txtfldSize.getText());
		int speed = Integer.parseInt(v.txtfldSpeed.getText());
		int tone = Integer.parseInt(v.txtfldTone.getText());
		int alpha = Integer.parseInt(v.txtfldAlpha.getText());
		int toneScale = Integer.parseInt(v.txtfldToneScale.getText());
		int comp1 = Integer.parseInt(v.txtfldParameter1.getText());
		int comp2 = Integer.parseInt(v.txtfldParameter2.getText());
		int comp3 = Integer.parseInt(v.txtfldParameter3.getText());
		int comp4 = Integer.parseInt(v.txtfldParameter4.getText());
		String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		String voiceTextBuf = voiceText;
		if (voiceTextBuf.length() > 10) {
			voiceTextBuf = voiceTextBuf.substring(0, 10);
		}
		String outPath = v.txtfldOutPath.getText() + "\\" + getTime() + "_" + voiceTextBuf + ".wav";

		driver.setDriverPath("./META-INF/exe/cevio/");

		driver.setVolume(volume);
		driver.setSpeed(speed);
		driver.setTone(tone);
		driver.setAlpha(alpha);
		driver.setToneScale(toneScale);
		driver.setParam1(comp1);
		driver.setParam2(comp2);
		driver.setParam3(comp3);
		driver.setParam4(comp4);
		driver.setText(voiceText);
		driver.setSavePath(outPath);

		return new File(outPath);
	}

	public static void voiceTextSave(View v) throws IOException {
		String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		String path = v.txtfldOutPath.getText().replaceAll("\\\\", "/") + "/";
		String bufStr = getTime();
		File file = new File(path + "/" + bufStr + "_" + voiceText + ".txt");
		FileWriter fw;
		fw = new FileWriter(file);
		fw.write(voiceText);
		fw.close();
		file.createNewFile();
	}

	private static String getTime() {
		Calendar now = Calendar.getInstance(); // インスタンス化
		int y = now.get(Calendar.YEAR); // 年を取得
		int mo = now.get(Calendar.MONTH);// 月を取得
		int d = now.get(Calendar.DATE); // 現在の日を取得
		int h = now.get(Calendar.HOUR_OF_DAY);// 時を取得
		int m = now.get(Calendar.MINUTE); // 分を取得
		int s = now.get(Calendar.SECOND);
		String bufStr = String.format("%1$04d", y) + String.format("%1$02d", mo + 1) + String.format("%1$02d", d) + String.format("%1$02d", h) + String.format("%1$02d", m) + String.format("%1$02d", s);
		return bufStr;
	}

	public static void colorSelect(Controller c, View v, int num) {
		if (num == 0) {
			ColorSelector.showColorSelector(v, c, true);
		} else if (num == 1) {
			ColorSelector.showColorSelector(v, c, false);
		}
	}

	public static void subtitlesPreview(View v, BufferedImage img) {
		v.lblSubtitles.setIcon(new ImageIcon(img));
	}

	public static File subtitlesSave(View v, BufferedImage img) throws IOException {
		String path = v.txtfldOutPath.getText().replaceAll("\\\\", "/") + "/";
		String str = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		Calendar now = Calendar.getInstance(); // インスタンス化
		int y = now.get(Calendar.YEAR); // 年を取得
		int mo = now.get(Calendar.MONTH);// 月を取得
		int d = now.get(Calendar.DATE); // 現在の日を取得
		int h = now.get(Calendar.HOUR_OF_DAY);// 時を取得
		int m = now.get(Calendar.MINUTE); // 分を取得
		int s = now.get(Calendar.SECOND);
		String bufStr = String.format("%1$04d", y) + String.format("%1$02d", mo + 1) + String.format("%1$02d", d) + String.format("%1$02d", h) + String.format("%1$02d", m) + String.format("%1$02d", s);
		File file = new File(path + "/" + bufStr + "_" + str + ".png");
		ImageIO.write(img, "PNG", file);
		return file;
	}

	public static BufferedImage subtitlesCreate(View v) {
		String str = v.txtArea.getText();
		CVImage image = null;
		Color color = v.fontColor;
		Color colorFontFrame = v.edgeColor;
		Font font = new Font(v.font.getName(), v.font.getStyle(), Integer.parseInt(v.txtfldFontSize.getText()));
		int edgeSize = Integer.parseInt(v.txtfldEdgeSize.getText());

		if (!"".equals(str)) {
			String[] strs = str.split("\\r\\n|\\r|\\n");
			int mag = 2;
			int max = 0;
			for (int i = 0; i < strs.length; i++) {
				if (max < strs[i].length()) {
					max = strs[i].length();
				}
			}
			font = new Font(font.getName(), font.getStyle(), font.getSize() * mag);
			image = new CVImage(new BufferedImage((int) (max * font.getSize() + font.getSize() * 1.5), (int) (strs.length * (font.getSize() * 1.5 + 1)), BufferedImage.TYPE_INT_ARGB));
			Graphics2D g2d = image.getImageBuffer().createGraphics();
			g2d.setBackground(new Color(255, 255, 255, 0));
			g2d.clearRect(0, 0, 100, 100);
			g2d.setFont(font);

			if (edgeSize > 0) {
				g2d.setColor(colorFontFrame);
				for (int i = 0; i < strs.length; i++) {
					for (int x = (int) (-1 * edgeSize * mag); x < (int) (mag * edgeSize + 1); x++) {
						for (int y = (int) (-1 * edgeSize * mag); y < (int) (mag * edgeSize); y++) {
							g2d.drawString(strs[i], 5 + x, font.getSize() * (i + 1) + y);
						}
					}
				}
			}

			g2d.setColor(color);
			for (int i = 0; i < strs.length; i++) {
				g2d.drawString(strs[i], 5, font.getSize() * (i + 1));
			}
			image = CV.resize(image, (int) (image.getWidth() / mag), (int) (image.getHeight() / mag));

			try {
				image = CV.imageNormalize(image);
			} catch (ArrayIndexOutOfBoundsException e) {
				MessageDialog.showDialog(v, "文字縁サイズが大きすぎます。");
			}

			return image.getImageBuffer();
		} else {
			return new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		}
	}

	public static void changeCastImage(View v) {
		String castName = (String) v.comboCast.getSelectedItem();
		v.lblCast.setIcon(icons.get(castName).getImage());
	}

	public static void changeParameterSet(View v) throws FaildChangeCastException {
		String castName = (String) v.comboCast.getSelectedItem();

		switch (castName) {
		case SasaraDriver.CAST_NAME:
			v.lblParameter1.setText(SasaraDriver.PARAMETERS[0]);
			v.lblParameter2.setText(SasaraDriver.PARAMETERS[1]);
			v.lblParameter3.setText(SasaraDriver.PARAMETERS[2]);
			v.lblParameter4.setText(SasaraDriver.PARAMETERS[3]);
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(true);
			return;

		case TsudumiDriver.CAST_NAME: // つづみ
			v.lblParameter1.setText(TsudumiDriver.PARAMETERS[0]);
			v.lblParameter2.setText(TsudumiDriver.PARAMETERS[1]);
			v.lblParameter3.setText(TsudumiDriver.PARAMETERS[2]);
			v.lblParameter4.setText(TsudumiDriver.PARAMETERS[3]);
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(false);
			v.txtfldParameter4.setVisible(false);
			return;

		case TakahashiDriver.CAST_NAME: // タカハシ
			v.lblParameter1.setText(TakahashiDriver.PARAMETERS[0]);
			v.lblParameter2.setText(TakahashiDriver.PARAMETERS[1]);
			v.lblParameter3.setText(TakahashiDriver.PARAMETERS[2]);
			v.lblParameter4.setText(TakahashiDriver.PARAMETERS[3]);
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(false);
			return;

		case OneDriver.CAST_NAME: // ONE
			v.lblParameter1.setText(OneDriver.PARAMETERS[0]);
			v.lblParameter2.setText(OneDriver.PARAMETERS[1]);
			v.lblParameter3.setText(OneDriver.PARAMETERS[2]);
			v.lblParameter4.setText(OneDriver.PARAMETERS[3]);
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(true);
			return;

		case IaDriver.CAST_NAME:
			v.lblParameter1.setText(IaDriver.PARAMETERS[0]);
			v.lblParameter2.setText(IaDriver.PARAMETERS[1]);
			v.lblParameter3.setText(IaDriver.PARAMETERS[2]);
			v.lblParameter4.setText(IaDriver.PARAMETERS[3]);
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(true);
			return;

		case Voiceroid.YUKARI_NAME:
		case Voiceroid.MAKI_NAME:
		case Voiceroid.ZUNKO_NAME:
		case Voiceroid.AKANE_NAME:
		case Voiceroid.AOI_NAME:
		case Voiceroid.YOSHIDA_NAME:
		case Voiceroid.SEIKA_NAME:
		case Voiceroid.KIRITAN_NAME:
			v.lblParameter1.setText("");
			v.lblParameter2.setText("");
			v.lblParameter3.setText("");
			v.lblParameter4.setText("");
			v.lblAlpha.setVisible(false);
			v.txtfldAlpha.setVisible(false);
			v.txtfldParameter1.setVisible(false);
			v.txtfldParameter2.setVisible(false);
			v.txtfldParameter3.setVisible(false);
			v.txtfldParameter4.setVisible(false);
			return;
		}

		throw new FaildChangeCastException("パラメーターセットの変更に失敗しました。。");
	}

	public static void btnPresetUpdate(View v, List<Preset> presets) {
		for (int i = 0; i < 12; i++) {
			v.btnPreset.get(i).setText(presets.get(i).getName());
		}
	}

	private static Font getFont(String name) {
		Font ret = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font fonts[] = ge.getAllFonts();
		for (Font font : fonts) {
			if (font.getName().equals(name)) {
				ret = font;
				break;
			}
		}
		return ret;
	}

	public static void parameterUpdate(View v, Preset preset) {
		v.txtfldSize.setText(String.valueOf(preset.getSize()));
		v.txtfldSpeed.setText(String.valueOf(preset.getSpeed()));
		v.txtfldTone.setText(String.valueOf(preset.getTone()));
		v.txtfldAlpha.setText(String.valueOf(preset.getAlpha()));
		v.txtfldToneScale.setText(String.valueOf(preset.getToneScale()));
		v.txtfldParameter1.setText(String.valueOf(preset.getComp1()));
		v.txtfldParameter2.setText(String.valueOf(preset.getComp2()));
		v.txtfldParameter3.setText(String.valueOf(preset.getComp3()));
		v.txtfldParameter4.setText(String.valueOf(preset.getComp4()));
		v.fontColor = preset.getColorFont();
		v.edgeColor = preset.getColorFontFrame();
	}

	public static void presetSave(View v) {
		PresetSaver.showPresetSaver(v);
	}

	public static void temporarySave(View v) {
		Temporary tmp = new Temporary();
		tmp.setFont(v.font.getFontName());
		tmp.setFontSize(v.txtfldFontSize.getText());
		tmp.setFontFrameSize(v.txtfldEdgeSize.getText());
		tmp.setPath(v.txtfldOutPath.getText());
		tmp.setEditorFontSize(String.valueOf(v.txtArea.getFont().getSize()));
		tmp.setCevioPath(cevioPath);
		tmp.setYukariPath(yukariPath);
		tmp.setMakiPath(makiPath);
		tmp.setZunkoPath(zunkoPath);
		tmp.setAkanePath(akanePath);
		tmp.setAoiPath(aoiPath);
		tmp.setYoshidaPath(yoshidaPath);
		tmp.setSeikaPath(seikaPath);
		tmp.setKiritanPath(kiritanPath);
		Temporary.save(tmp);
	}

	public static void voiceroidActive(View v) throws HwndNotFoundException {
		String castName = (String) v.comboCast.getSelectedItem();

		switch (castName) {
		case SasaraDriver.CAST_NAME:
		case TsudumiDriver.CAST_NAME:
		case TakahashiDriver.CAST_NAME:
		case OneDriver.CAST_NAME:
		case IaDriver.CAST_NAME:
			break;

		case Voiceroid.YUKARI_NAME:
		case Voiceroid.MAKI_NAME:
		case Voiceroid.ZUNKO_NAME:
		case Voiceroid.AKANE_NAME:
		case Voiceroid.AOI_NAME:
		case Voiceroid.YOSHIDA_NAME:
		case Voiceroid.SEIKA_NAME:
		case Voiceroid.KIRITAN_NAME:
			Voiceroid voiceroid = new Voiceroid(castName);
			String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
			if (icons.get(castName).isHide()) {
				voiceroid.clearVoiceText(voiceText);
				voiceroid.setMinimize();
				icons.get(castName).setHide(false);
			} else {
				voiceroid.setVoiceText(voiceText);
				voiceroid.foreGround(v.frame.getX(), v.frame.getY());
				icons.get(castName).setHide(true);
			}
			v.lblCast.setIcon(icons.get(castName).getImage());

		default:
			break;
		}
	}

}
