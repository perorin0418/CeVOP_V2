package org.net.perorin.swingParts.parts;

import java.awt.Font;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CeCheckbox extends JPanel {

	public CeButton checkbox = null;
	public boolean isCheck = false;
	CeFocus focus = null;

	public CeCheckbox(String text) {
		super();
		this.setLayout(null);
		this.setBackground(CeCommon.colorBackground);
		CeCheckbox self = this;
		checkbox = new CeButton("") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				if (CeCheckbox.this.isCheck) {
					CeCheckbox.this.isCheck = false;
					text = "";
					imageReflesh();
				} else {
					CeCheckbox.this.isCheck = true;
					text = "✔";
					imageReflesh();
				}
			}
		};
		checkbox.setBounds(0, 0, 15, 15);
		checkbox.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				focus.focusLost();
			}

			@Override
			public void focusGained(FocusEvent e) {
				focus.focusIn();
			}
		});
		this.add(checkbox);

		JLabel lblText = new JLabel(text);
		lblText.setBounds(18, 0, 70, 15);
		lblText.setFont(new Font("メイリオ", Font.PLAIN, 11));
		lblText.setForeground(CeCommon.colorFontInactive);
		lblText.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				checkbox.grabFocus();
				if (CeCheckbox.this.isCheck) {
					CeCheckbox.this.isCheck = false;
					checkbox.text = "";
					checkbox.imageReflesh();
				} else {
					CeCheckbox.this.isCheck = true;
					checkbox.text = "✔";
					checkbox.imageReflesh();
				}
			}
		});
		this.add(lblText);
	}

	public CeButton getCheckbox() {
		return checkbox;
	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(new Rectangle(this.getX() + checkbox.getX(), this.getY() + checkbox.getY(), checkbox.getWidth(), checkbox.getHeight()));
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

}
