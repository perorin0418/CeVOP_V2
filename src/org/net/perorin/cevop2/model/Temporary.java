package org.net.perorin.cevop2.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Temporary {

	private String font;
	private String fontSize;
	private String fontFrameSize;
	private String path;
	private String editorFontSize;
	private String cevioPath;
	private String yukariPath;
	private String makiPath;
	private String zunkoPath;
	private String akanePath;
	private String aoiPath;
	private String yoshidaPath;
	private String seikaPath;
	private String kiritanPath;

	public String getFont() {
		return font;
	}

	public String getFontSize() {
		return fontSize;
	}

	public String getFontFrameSize() {
		return fontFrameSize;
	}

	public String getPath() {
		return path;
	}

	public String getEditorFontSize() {
		return editorFontSize;
	}

	public String getCevioPath() {
		return cevioPath;
	}

	public String getYukariPath() {
		return yukariPath;
	}

	public String getMakiPath() {
		return makiPath;
	}

	public String getZunkoPath() {
		return zunkoPath;
	}

	public String getAkanePath() {
		return akanePath;
	}

	public String getAoiPath() {
		return aoiPath;
	}

	public String getYoshidaPath() {
		return yoshidaPath;
	}

	public String getSeikaPath() {
		return seikaPath;
	}

	public String getKiritanPath() {
		return kiritanPath;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontFrameSize(String fontFrameSize) {
		this.fontFrameSize = fontFrameSize;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setEditorFontSize(String editorFontSize) {
		this.editorFontSize = editorFontSize;
	}

	public void setCevioPath(String cevioPath) {
		this.cevioPath = cevioPath;
	}

	public void setYukariPath(String yukariPath) {
		this.yukariPath = yukariPath;
	}

	public void setMakiPath(String makiPath) {
		this.makiPath = makiPath;
	}

	public void setZunkoPath(String zunkoPath) {
		this.zunkoPath = zunkoPath;
	}

	public void setAkanePath(String akanePath) {
		this.akanePath = akanePath;
	}

	public void setAoiPath(String aoiPath) {
		this.aoiPath = aoiPath;
	}

	public void setYoshidaPath(String yoshidaPath) {
		this.yoshidaPath = yoshidaPath;
	}

	public void setSeikaPath(String seikaPath) {
		this.seikaPath = seikaPath;
	}

	public void setKiritanPath(String kiritanPath) {
		this.kiritanPath = kiritanPath;
	}

	public static Temporary load() {
		Temporary tmp = new Temporary();
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("./META-INF/temp"));
			String buf;
			while ((buf = br.readLine()) != null) {
				String bufarr[] = buf.split("=");
				list.add(bufarr[1]);
			}
			br.close();
		} catch (Exception e) {
		}
		tmp.setFont(list.get(0));
		tmp.setFontSize(list.get(1));
		tmp.setFontFrameSize(list.get(2));
		tmp.setPath(list.get(3));
		tmp.setEditorFontSize(list.get(4));
		tmp.setCevioPath(list.get(5));
		tmp.setYukariPath(list.get(6));
		tmp.setMakiPath(list.get(7));
		tmp.setZunkoPath(list.get(8));
		tmp.setAkanePath(list.get(9));
		tmp.setAoiPath(list.get(10));
		tmp.setYoshidaPath(list.get(11));
		tmp.setSeikaPath(list.get(12));
		tmp.setKiritanPath(list.get(13));

		return tmp;
	}

	public static void save(Temporary tmp) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("./META-INF/temp"));
			bw.write("フォント=" + tmp.getFont());
			bw.newLine();
			bw.write("フォントサイズ=" + tmp.getFontSize());
			bw.newLine();
			bw.write("フォント枠サイズ=" + tmp.getFontFrameSize());
			bw.newLine();
			bw.write("出力先=" + tmp.getPath());
			bw.newLine();
			bw.write("エディターフォントサイズ=" + tmp.getEditorFontSize());
			bw.newLine();
			bw.write("CeVIOパス=" + tmp.getCevioPath());
			bw.newLine();
			bw.write("結月ゆかりパス=" + tmp.getYukariPath());
			bw.newLine();
			bw.write("弦巻マキパス=" + tmp.getMakiPath());
			bw.newLine();
			bw.write("東北ずん子パス=" + tmp.getZunkoPath());
			bw.newLine();
			bw.write("琴葉茜パス=" + tmp.getAkanePath());
			bw.newLine();
			bw.write("琴葉葵パス=" + tmp.getAoiPath());
			bw.newLine();
			bw.write("吉田くんパス=" + tmp.getYoshidaPath());
			bw.newLine();
			bw.write("京町セイカパス=" + tmp.getSeikaPath());
			bw.newLine();
			bw.write("東北きりたんパス=" + tmp.getKiritanPath());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
		}
	}

}
