package org.net.perorin.cevop2.model;

import javax.swing.ImageIcon;

import org.net.perorin.cevop2.exception.CantMergeImageException;

public class CharacterIcon {

	private final String imgPath = "./img/";
	private ImageIcon icon;
	private ImageIcon iconHide;
	private boolean isHide = false;

	public CharacterIcon(String name) throws CantMergeImageException {
		try {
			icon = new ImageIcon(imgPath + name + ".png");
			iconHide = new ImageIcon(imgPath + name + "_hide.png");
		} catch (Exception e) {
			throw new CantMergeImageException();
		}
	}

	public ImageIcon getImage() {
		if (isHide) {
			return iconHide;
		} else {
			return icon;
		}
	}

	public boolean isHide() {
		return isHide;
	}

	public void setHide(boolean b) {
		isHide = b;
	}
}
