package org.net.perorin.swingParts.parts;

import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CePanel extends JPanel {

	CeFocus focus = null;

	public CePanel() {
		super();
	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}
}
