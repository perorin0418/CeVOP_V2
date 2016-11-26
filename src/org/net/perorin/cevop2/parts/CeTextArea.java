package org.net.perorin.cevop2.parts;

import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.net.perorin.cevop2.parts.stringLimitation.TabCharLimitation;

public class CeTextArea extends JTextArea {

	CeFocus focus = null;

	public CeTextArea() {
		super();
		this.setWrapStyleWord(false);
		this.setBackground(CeCommon.colorTextBackground);
		this.setForeground(CeCommon.colorFontActive);
		this.setCaretColor(CeCommon.colorFontActive);
		CeTextArea self = this;
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				self.setForeground(CeCommon.colorFontInactive);
				if (focus != null) {
					focus.focusLost();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				self.setForeground(CeCommon.colorFontActive);
				if (focus != null) {
					focus.focusIn();
				}
			}
		});
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0) {
						self.transferFocusBackward();
					} else {
						self.transferFocus();
					}
				}
			}
		});
	}

	@Override
	protected Document createDefaultModel() {
		return new TabCharLimitation();
	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

}
