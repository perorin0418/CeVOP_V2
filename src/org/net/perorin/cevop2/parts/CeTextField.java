package org.net.perorin.cevop2.parts;

import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import org.net.perorin.cevop2.parts.stringLimitation.NumberCharOnly;

public class CeTextField extends JTextField {

	CeFocus focus = null;

	public CeTextField() {
		super();
		this.setForeground(CeCommon.colorFontInactive);
		this.setBackground(CeCommon.colorTextBackground);
		this.setCaretColor(CeCommon.colorFontInactive);
		this.setBorder(BorderFactory.createEmptyBorder());
		CeTextField self = this;
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
				((JTextComponent) e.getComponent()).selectAll();
			}
		});
	}

	protected Document createDefaultModel() {
		return new NumberCharOnly();
	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		focus.setVisible(aFlag);
	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

}
