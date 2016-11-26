package org.net.perorin.cevop2.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.net.perorin.cevop2.parts.CeCommon;
import org.net.perorin.cv.CV;
import org.net.perorin.cv.CVImage;

public class BootingWindow {

	private static final String IMG_PATH = "./img/parts/booting.png";
	private static final String IMG_BACK_PATH = "./img/parts/bootingBack.png";

	Window window;
	Window windowBack;
	int x;
	int y;

	public BootingWindow(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void run() {
		windowBack = new Window(new Frame());
		windowBack.setBounds(x, y, 340, 340);
		ImageIcon imgBack = new ImageIcon(new CVImage(IMG_BACK_PATH).getImageBuffer());
		JLabel lblBack = new JLabel(imgBack);
		windowBack.add(lblBack);
		windowBack.setOpacity(0.8f);

		window = new Window(new Frame());
		window.setBounds(x + 20, y + 20, 300, 300);
		CVImage cvImg = new CVImage(IMG_PATH);
		String strs[] = { CeCommon.version };
		CV.writeString(cvImg.getImageBuffer().createGraphics(), strs, 250, 280, new Font("メイリオ", Font.PLAIN, 10), new Color(225, 240, 241));
		ImageIcon img = new ImageIcon(cvImg.getImageBuffer());
		JLabel lbl = new JLabel(img);
		window.add(lbl);

		windowBack.setVisible(true);
		window.setVisible(true);
	}

	public void stop() {
		window.dispose();
		windowBack.dispose();
	}
}
