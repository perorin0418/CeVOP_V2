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

import org.net.perorin.cevio.IaDriver;
import org.net.perorin.cevio.OneDriver;
import org.net.perorin.cevio.SasaraDriver;
import org.net.perorin.cevio.TakahashiDriver;
import org.net.perorin.cevio.TsudumiDriver;
import org.net.perorin.voiceroid.Voiceroid;

public class PresetUtilty {

	public static List<Preset> loadPreset(String cast) throws IOException {
		List<Preset> list = new ArrayList<Preset>();
		File file = null;
		switch (cast) {
		case SasaraDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case TsudumiDriver.CAST_NAME:
			file = new File("./META-INF/presets/tsudumi.preset");
			break;

		case TakahashiDriver.CAST_NAME:
			file = new File("./META-INF/presets/takahashi.preset");
			break;

		case OneDriver.CAST_NAME:
			file = new File("./META-INF/presets/one.preset");
			break;

		case IaDriver.CAST_NAME:
			file = new File("./META-INF/presets/ia.preset");
			break;

		case Voiceroid.YUKARI_NAME:
			file = new File("./META-INF/presets/yukari.preset");

		case Voiceroid.MAKI_NAME:
			file = new File("./META-INF/presets/maki.preset");
			break;

		case Voiceroid.ZUNKO_NAME:
			file = new File("./META-INF/presets/zunko.preset");
			break;

		case Voiceroid.AKANE_NAME:
			file = new File("./META-INF/presets/akane.preset");
			break;

		case Voiceroid.AOI_NAME:
			file = new File("./META-INF/presets/aoi.preset");
			break;

		case Voiceroid.YOSHIDA_NAME:
			file = new File("./META-INF/presets/yoshida.preset");
			break;

		case Voiceroid.SEIKA_NAME:
			file = new File("./META-INF/presets/seika.preset");
			break;

		case Voiceroid.KIRITAN_NAME:
			file = new File("./META-INF/presets/kiritan.preset");
			break;

		default:
			break;
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
		switch (cast) {
		case SasaraDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case TsudumiDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case TakahashiDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case OneDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case IaDriver.CAST_NAME:
			file = new File("./META-INF/presets/sasara.preset");
			break;

		case Voiceroid.YUKARI_NAME:
			file = new File("./META-INF/presets/yukari.preset");
			break;

		case Voiceroid.MAKI_NAME:
			file = new File("./META-INF/presets/maki.preset");
			break;

		case Voiceroid.ZUNKO_NAME:
			file = new File("./META-INF/presets/zunko.preset");
			break;

		case Voiceroid.AKANE_NAME:
			file = new File("./META-INF/presets/akane.preset");
			break;

		case Voiceroid.AOI_NAME:
			file = new File("./META-INF/presets/aoi.preset");
			break;

		case Voiceroid.YOSHIDA_NAME:
			file = new File("./META-INF/presets/yoshida.preset");
			break;

		case Voiceroid.SEIKA_NAME:
			file = new File("./META-INF/presets/seika.preset");
			break;

		case Voiceroid.KIRITAN_NAME:
			file = new File("./META-INF/presets/kiritan.preset");
			break;

		default:
			break;
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
