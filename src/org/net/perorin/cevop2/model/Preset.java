package org.net.perorin.cevop2.model;

import java.awt.Color;

public class Preset {

	private String name;
	private int size;
	private int speed;
	private int tone;
	private int alpha;
	private int toneScale;
	private int comp1;
	private int comp2;
	private int comp3;
	private int comp4;
	private Color font;
	private Color fontFrame;

	public Preset(String name, int size, int speed, int tone, int alpha, int toneScale, int comp1, int comp2, int comp3, int comp4, Color font, Color fontFrame) {
		setName(name);
		setSize(size);
		setSpeed(speed);
		setTone(tone);
		setAlpha(alpha);
		setToneScale(toneScale);
		setComp1(comp1);
		setComp2(comp2);
		setComp3(comp3);
		setComp4(comp4);
		setColorFont(font);
		setColorFontFrame(fontFrame);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setTone(int tone) {
		this.tone = tone;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public void setToneScale(int toneScale) {
		this.toneScale = toneScale;
	}

	public void setComp1(int comp1) {
		this.comp1 = comp1;
	}

	public void setComp2(int comp2) {
		this.comp2 = comp2;
	}

	public void setComp3(int comp3) {
		this.comp3 = comp3;
	}

	public void setComp4(int comp4) {
		this.comp4 = comp4;
	}

	public void setColorFont(Color font) {
		this.font = font;
	}

	public void setColorFontFrame(Color fontFrame) {
		this.fontFrame = fontFrame;
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.size;
	}

	public int getSpeed() {
		return this.speed;
	}

	public int getTone() {
		return this.tone;
	}

	public int getAlpha() {
		return this.alpha;
	}

	public int getToneScale() {
		return this.toneScale;
	}

	public int getComp1() {
		return this.comp1;
	}

	public int getComp2() {
		return this.comp2;
	}

	public int getComp3() {
		return this.comp3;
	}

	public int getComp4() {
		return this.comp4;
	}

	public Color getColorFont() {
		return font;
	}

	public Color getColorFontFrame() {
		return fontFrame;
	}

	public void print() {
		System.out.print(name + ", ");
		System.out.print(size + ", ");
		System.out.print(speed + ", ");
		System.out.print(tone + ", ");
		System.out.print(alpha + ", ");
		System.out.print(toneScale + ", ");
		System.out.print(comp1 + ", ");
		System.out.print(comp2 + ", ");
		System.out.print(comp3 + ", ");
		System.out.print(comp4 + ", ");
		System.out.print(font + ", ");
		System.out.println(fontFrame);

	}

}
