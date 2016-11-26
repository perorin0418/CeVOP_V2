package org.net.perorin.cevop2.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.net.perorin.voiceroid.Voiceroid;

public class PresetUtilty {

	static final String SASARA = "さとうささら";
	static final String TSUDUMI = "すずきつづみ";
	static final String TAKAHASHI = "タカハシ";
	static final String ONE = "ONE";

	public static List<Preset> loadPreset(String cast) throws IOException {
		List<Preset> list = new ArrayList<Preset>();
		File file = null;
		if (cast.equals(SASARA)) {
			file = new File("./presets/sasara.preset");
		} else if (cast.equals(TSUDUMI)) {
			file = new File("./presets/tsudumi.preset");
		} else if (cast.equals(TAKAHASHI)) {
			file = new File("./presets/takahashi.preset");
		} else if (cast.equals(ONE)) {
			file = new File("./presets/one.preset");
		} else if (cast.equals(Voiceroid.YUKARI_NAME)) {
			file = new File("./presets/yukari.preset");
		} else if (cast.equals(Voiceroid.MAKI_NAME)) {
			file = new File("./presets/maki.preset");
		} else if (cast.equals(Voiceroid.ZUNKO_NAME)) {
			file = new File("./presets/zunko.preset");
		} else if (cast.equals(Voiceroid.AKANE_NAME)) {
			file = new File("./presets/akane.preset");
		} else if (cast.equals(Voiceroid.AOI_NAME)) {
			file = new File("./presets/aoi.preset");
		} else if (cast.equals(Voiceroid.YOSHIDA_NAME)) {
			file = new File("./presets/yoshida.preset");
		} else if (cast.equals(Voiceroid.SEIKA_NAME)) {
			file = new File("./presets/seika.preset");
		} else if (cast.equals(Voiceroid.KIRITAN_NAME)) {
			file = new File("./presets/kiritan.preset");
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		String buf;
		String[] bufArray;
		while ((buf = br.readLine()) != null) {
			bufArray = buf.split(",");
			Preset bufPreset = new Preset(
					bufArray[0],
					Integer.parseInt(bufArray[1]),
					Integer.parseInt(bufArray[2]),
					Integer.parseInt(bufArray[3]),
					Integer.parseInt(bufArray[4]),
					Integer.parseInt(bufArray[5]),
					Integer.parseInt(bufArray[6]),
					Integer.parseInt(bufArray[7]),
					Integer.parseInt(bufArray[8]),
					Integer.parseInt(bufArray[9]),
					new Color(
							Integer.parseInt(bufArray[10]),
							Integer.parseInt(bufArray[11]),
							Integer.parseInt(bufArray[12]),
							Integer.parseInt(bufArray[13])),
					new Color(
							Integer.parseInt(bufArray[14]),
							Integer.parseInt(bufArray[15]),
							Integer.parseInt(bufArray[16]),
							Integer.parseInt(bufArray[17])));
			list.add(bufPreset);
		}
		br.close();
		return list;
	}

	public static void savePreset(String cast, List<Preset> list) throws IOException {
		File file = null;
		if (cast.equals(SASARA)) {
			file = new File("./presets/sasara.preset");
		} else if (cast.equals(TSUDUMI)) {
			file = new File("./presets/tsudumi.preset");
		} else if (cast.equals(TAKAHASHI)) {
			file = new File("./presets/takahashi.preset");
		} else if (cast.equals(ONE)) {
			file = new File("./presets/one.preset");
		} else if (cast.equals(Voiceroid.YUKARI_NAME)) {
			file = new File("./presets/yukari.preset");
		} else if (cast.equals(Voiceroid.MAKI_NAME)) {
			file = new File("./presets/maki.preset");
		} else if (cast.equals(Voiceroid.ZUNKO_NAME)) {
			file = new File("./presets/zunko.preset");
		} else if (cast.equals(Voiceroid.AKANE_NAME)) {
			file = new File("./presets/akane.preset");
		} else if (cast.equals(Voiceroid.AOI_NAME)) {
			file = new File("./presets/aoi.preset");
		} else if (cast.equals(Voiceroid.YOSHIDA_NAME)) {
			file = new File("./presets/yoshida.preset");
		} else if (cast.equals(Voiceroid.SEIKA_NAME)) {
			file = new File("./presets/seika.preset");
		} else if (cast.equals(Voiceroid.KIRITAN_NAME)) {
			file = new File("./presets/kiritan.preset");
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < list.size(); i++) {
			bw.write(list.get(i).getName() + ",");
			bw.write(list.get(i).getSize() + ",");
			bw.write(list.get(i).getSpeed() + ",");
			bw.write(list.get(i).getTone() + ",");
			bw.write(list.get(i).getAlpha() + ",");
			bw.write(list.get(i).getToneScale() + ",");
			bw.write(list.get(i).getComp1() + ",");
			bw.write(list.get(i).getComp2() + ",");
			bw.write(list.get(i).getComp3() + ",");
			bw.write(list.get(i).getComp4() + ",");
			bw.write(list.get(i).getColorFont().getRed() + ",");
			bw.write(list.get(i).getColorFont().getGreen() + ",");
			bw.write(list.get(i).getColorFont().getBlue() + ",");
			bw.write(list.get(i).getColorFont().getAlpha() + ",");
			bw.write(list.get(i).getColorFontFrame().getRed() + ",");
			bw.write(list.get(i).getColorFontFrame().getGreen() + ",");
			bw.write(list.get(i).getColorFontFrame().getBlue() + ",");
			bw.write(list.get(i).getColorFontFrame().getAlpha() + ",");
			bw.newLine();
		}
		bw.close();
	}
}
