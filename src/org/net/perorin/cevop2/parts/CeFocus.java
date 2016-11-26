package org.net.perorin.cevop2.parts;

import java.awt.Label;
import java.awt.Rectangle;

public class CeFocus {

	Label top = new Label();;
	Label bottom = new Label();;
	Label right = new Label();;
	Label left = new Label();;

	public CeFocus(Rectangle rec) {
		int x = rec.x;
		int y = rec.y;
		int width = rec.width;
		int height = rec.height;
		top.setBounds(x - 1, y - 1, width + 2, 1);
		bottom.setBounds(x - 1, y + height, width + 2, 1);
		right.setBounds(x + width, y, 1, height);
		left.setBounds(x - 1, y, 1, height);
		top.setBackground(CeCommon.colorBlack);
		bottom.setBackground(CeCommon.colorBlack);
		right.setBackground(CeCommon.colorBlack);
		left.setBackground(CeCommon.colorBlack);
	}

	public void focusIn() {
		top.setBackground(CeCommon.colorFocus);
		bottom.setBackground(CeCommon.colorFocus);
		right.setBackground(CeCommon.colorFocus);
		left.setBackground(CeCommon.colorFocus);
	}

	public void focusLost() {
		top.setBackground(CeCommon.colorBlack);
		bottom.setBackground(CeCommon.colorBlack);
		right.setBackground(CeCommon.colorBlack);
		left.setBackground(CeCommon.colorBlack);
	}

	public void setVisible(boolean aFlag) {
		top.setVisible(aFlag);
		bottom.setVisible(aFlag);
		right.setVisible(aFlag);
		left.setVisible(aFlag);
	}

	public Label[] getLabels() {
		Label rets[] = { top, bottom, right, left };
		return rets;
	}
}
