package org.net.perorin.cevop2.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Temporary {

	public static List<String> load() {
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("./temp"));
			String buf;
			while ((buf = br.readLine()) != null) {
				list.add(buf);
			}
			br.close();
		} catch (Exception e) {
		}

		return list;
	}

	public static void save(
			String font,
			String fontSize,
			String fontFrameSize,
			String path,
			String editorFontSize,
			String cevioPath,
			String yukariPath,
			String makiPath,
			String zunkoPath,
			String akanePath,
			String aoiPath,
			String yoshidaPath,
			String seikaPath,
			String kiritanPath) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("./temp"));
			bw.write(font);
			bw.newLine();
			bw.write(fontSize);
			bw.newLine();
			bw.write(fontFrameSize);
			bw.newLine();
			bw.write(path);
			bw.newLine();
			bw.write(editorFontSize);
			bw.newLine();
			bw.write(cevioPath);
			bw.newLine();
			bw.write(yukariPath);
			bw.newLine();
			bw.write(makiPath);
			bw.newLine();
			bw.write(zunkoPath);
			bw.newLine();
			bw.write(akanePath);
			bw.newLine();
			bw.write(aoiPath);
			bw.newLine();
			bw.write(yoshidaPath);
			bw.newLine();
			bw.write(seikaPath);
			bw.newLine();
			bw.write(kiritanPath);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
		}
	}

}
