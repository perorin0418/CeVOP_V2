package org.net.perorin.cevop2.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.net.perorin.cevop2.parts.CeButton;
import org.net.perorin.cevop2.parts.CeCommon;

public class ConfirmDialog {

	public JFrame frame;
	private static final String TITLE_NAME = "確認";
	private JTextArea txtArea;
	private CeButton btnYes;
	private CeButton btnNo;

	public static void showDialog(View v, String message) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v.frame.setEnabled(false);
					ConfirmDialog window = new ConfirmDialog(v, message);
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConfirmDialog(View v, String message) {
		java.awt.Toolkit.getDefaultToolkit().beep();

		int width = 300;
		int height = 180;
		frame = new JFrame();
		frame.setResizable(false);
		if (v != null) {
			frame.setBounds(v.frame.getBounds().x + v.frame.getWidth() / 2 - width / 2, v.frame.getBounds().y + v.frame.getHeight() / 2 - height / 2, width, height);
		} else {
			frame.setBounds(100, 100, width, height);
		}
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(CeCommon.colorBackground);
		frame.setIconImage(new ImageIcon(CeCommon.iconPath).getImage());
		frame.setTitle(TITLE_NAME);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				v.frame.setEnabled(true);
				frame.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		txtArea = new JTextArea();
		txtArea.setFont(new Font("メイリオ", Font.PLAIN, 13));
		txtArea.setBounds(15, 10, 264, 67);
		txtArea.setBackground(CeCommon.colorBackground);
		txtArea.setForeground(CeCommon.colorFontInactive);
		txtArea.setEditable(false);
		txtArea.setLineWrap(true);
		txtArea.setText(message);
		frame.getContentPane().add(txtArea);

		btnYes = new CeButton("はい") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				processYes();
				v.frame.setEnabled(true);
				frame.dispose();
			}
		};
		btnYes.setFontSize(18);
		btnYes.setBounds(47, 96, 95, 38);
		btnYes.setFocusImage(frame);
		frame.getContentPane().add(btnYes);

		btnNo = new CeButton("いいえ") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				processNo();
				v.frame.setEnabled(true);
				frame.dispose();
			}
		};
		btnNo.setFontSize(18);
		btnNo.setBounds(157, 96, 95, 38);
		btnNo.setFocusImage(frame);
		frame.getContentPane().add(btnNo);

	}

	public void processYes() {
	}

	public void processNo() {
	}

}
