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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
import org.net.perorin.toolkit.ArrayOperator;
import org.net.perorin.voiceroid.Voiceroid;

public class Model {

	private static ArrayList<CharacterIcon> icons;

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
		icons = new ArrayList<CharacterIcon>();
		icons.add(new CharacterIcon("sasara"));
		icons.add(new CharacterIcon("tsudumi"));
		icons.add(new CharacterIcon("takahashi"));
		icons.add(new CharacterIcon("one"));
		icons.add(new CharacterIcon("yukari"));
		icons.add(new CharacterIcon("maki"));
		icons.add(new CharacterIcon("zunko"));
		icons.add(new CharacterIcon("akane"));
		icons.add(new CharacterIcon("aoi"));
		icons.add(new CharacterIcon("yoshida"));
		icons.add(new CharacterIcon("seika"));
		icons.add(new CharacterIcon("kiritan"));

		v.lblCast.setIcon(icons.get(0).getImage());

		String casts[] = Voiceroid.getCasts();
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
		v.comboFont.setSelectedIndex(ArrayOperator.getIndex(fontCombData, "ＭＳ ゴシック"));

		List<Preset> presets = PresetUtilty.loadPreset(PresetUtilty.SASARA);
		for (int i = 0; i < 12; i++) {
			v.btnPreset.get(i).setText(presets.get(i).getName());
		}
		parameterUpdate(v, presets.get(0));
	}

	public static void initTemp(View v) {
		List<String> tmp = new ArrayList<String>();
		tmp = Temporary.load();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font fonts[] = ge.getAllFonts();
		String[] fontCombData = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			fontCombData[i] = fonts[i].getName();
		}
		v.font = getFont(tmp.get(0));
		v.comboFont.setSelectedIndex(ArrayOperator.getIndex(fontCombData, tmp.get(0)));
		v.txtfldFontSize.setText(tmp.get(1));
		v.txtfldEdgeSize.setText(tmp.get(2));
		v.txtfldOutPath.setText(tmp.get(3));
		//		v.txtArea.setFont(new Font(v.txtArea.getFont().toString(), Integer.parseInt(tmp.get(4)), v.txtArea.getFont().getStyle()));
		cevioPath = tmp.get(5);
		yukariPath = tmp.get(6);
		makiPath = tmp.get(7);
		zunkoPath = tmp.get(8);
		akanePath = tmp.get(9);
		aoiPath = tmp.get(10);
		yoshidaPath = tmp.get(11);
		seikaPath = tmp.get(12);
		kiritanPath = tmp.get(13);
	}

	public static void voiceListen(View v) throws HwndNotFoundException {
		int castID = v.comboCast.getSelectedIndex();

		if (castID <= 3) {

		} else {
			Voiceroid voiceroid = new Voiceroid(castID - 4);

			int volume = Integer.parseInt(v.txtfldSize.getText());
			int speed = Integer.parseInt(v.txtfldSpeed.getText());
			int tone = Integer.parseInt(v.txtfldTone.getText());
			int toneScale = Integer.parseInt(v.txtfldToneScale.getText());
			voiceroid.setParameters(volume, speed, tone, toneScale);

			String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
			voiceroid.voiceListen(voiceText);
		}
	}

	public static File voiceSave(View v) throws HwndNotFoundException, FailedVoiceSaveException {
		int castID = v.comboCast.getSelectedIndex();
		File file = new File("");

		if (castID <= 3) {

		} else {
			Voiceroid voiceroid = new Voiceroid(castID - 4);

			int volume = Integer.parseInt(v.txtfldSize.getText());
			int speed = Integer.parseInt(v.txtfldSpeed.getText());
			int tone = Integer.parseInt(v.txtfldTone.getText());
			int toneScale = Integer.parseInt(v.txtfldToneScale.getText());
			voiceroid.setParameters(volume, speed, tone, toneScale);

			String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
			String savePath = v.txtfldOutPath.getText();
			file = voiceroid.voiceSave(voiceText, savePath);
		}

		return file;
	}

	public static void voiceTextSave(View v) throws IOException {
		String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
		String path = v.txtfldOutPath.getText().replaceAll("\\\\", "/") + "/";
		Calendar now = Calendar.getInstance(); // インスタンス化
		int y = now.get(Calendar.YEAR); // 年を取得
		int mo = now.get(Calendar.MONTH);// 月を取得
		int d = now.get(Calendar.DATE); // 現在の日を取得
		int h = now.get(Calendar.HOUR_OF_DAY);// 時を取得
		int m = now.get(Calendar.MINUTE); // 分を取得
		int s = now.get(Calendar.SECOND);
		String bufStr = String.format("%1$04d", y) + String.format("%1$02d", mo + 1) + String.format("%1$02d", d) + String.format("%1$02d", h) + String.format("%1$02d", m) + String.format("%1$02d", s);
		File file = new File(path + "/" + bufStr + "_" + voiceText + ".txt");
		FileWriter fw;
		fw = new FileWriter(file);
		fw.write(voiceText);
		fw.close();
		file.createNewFile();
	}

	public static void colorSelect(View v, int num) {
		if (num == 0) {
			ColorSelector.showColorSelector(v.fontColor, v, true);
		} else if (num == 1) {
			ColorSelector.showColorSelector(v.fontColor, v, false);
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
		int castID = v.comboCast.getSelectedIndex();
		v.lblCast.setIcon(icons.get(castID).getImage());
	}

	public static void changeParameterSet(View v) throws FaildChangeCastException {
		int castID = v.comboCast.getSelectedIndex();

		switch (castID) {
		case 0: // ささら
			v.lblParameter1.setText("元気");
			v.lblParameter2.setText("普通");
			v.lblParameter3.setText("怒り");
			v.lblParameter4.setText("悲しみ");
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(true);
			return;

		case 1: // つづみ
			v.lblParameter1.setText("クール");
			v.lblParameter2.setText("照れ");
			v.lblParameter3.setText("");
			v.lblParameter4.setText("");
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(false);
			v.txtfldParameter4.setVisible(false);
			return;

		case 2: // タカハシ
			v.lblParameter1.setText("元気");
			v.lblParameter2.setText("普通");
			v.lblParameter3.setText("へこみ");
			v.lblParameter4.setText("");
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(false);
			return;

		case 3: // ONE
			v.lblParameter1.setText("High");
			v.lblParameter2.setText("Mid");
			v.lblParameter3.setText("MidLow");
			v.lblParameter4.setText("Low");
			v.lblAlpha.setVisible(true);
			v.txtfldAlpha.setVisible(true);
			v.txtfldParameter1.setVisible(true);
			v.txtfldParameter2.setVisible(true);
			v.txtfldParameter3.setVisible(true);
			v.txtfldParameter4.setVisible(true);
			return;
		}

		if (4 <= castID && castID <= 13) {
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
		Temporary.save(
				v.font.getFontName(),
				v.txtfldFontSize.getText(),
				v.txtfldEdgeSize.getText(),
				v.txtfldOutPath.getText(),
				String.valueOf(v.txtArea.getFont().getSize()),
				cevioPath,
				yukariPath,
				makiPath,
				zunkoPath,
				akanePath,
				aoiPath,
				yoshidaPath,
				seikaPath,
				kiritanPath);
	}

	public static void voiceroidActive(View v) throws HwndNotFoundException {
		int castID = v.comboCast.getSelectedIndex();
		if (castID >= 4) {
			Voiceroid voiceroid = new Voiceroid(castID - 4);
			String voiceText = v.txtArea.getText().replaceAll("\\r\\n|\\r|\\n", "");
			if (icons.get(castID).isHide()) {
				voiceroid.clearVoiceText(voiceText);
				voiceroid.setMinimize();
				icons.get(castID).setHide(false);
			} else {
				voiceroid.setVoiceText(voiceText);
				voiceroid.foreGround(v.frame.getX(), v.frame.getY());
				icons.get(castID).setHide(true);
			}
			v.lblCast.setIcon(icons.get(castID).getImage());
		}
	}

}
