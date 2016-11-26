package org.net.perorin.cevop2.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.net.perorin.cevop2.model.Preset;
import org.net.perorin.cevop2.model.PresetUtilty;
import org.net.perorin.cevop2.parts.CeButton;
import org.net.perorin.cevop2.parts.CeCombo;
import org.net.perorin.cevop2.parts.CeCommon;
import org.net.perorin.cevop2.parts.CeTextField;
import org.net.perorin.cevop2.parts.stringLimitation.TabCharLimitation;

public class PresetSaver {

	private static final String TITLE_NAME = "プリセットの保存";

	private JFrame frame;
	private JTextArea txtArea;
	private CeCombo combo;
	private CeTextField txtFld;
	private CeButton btnOK;
	private CeButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void showPresetSaver(View v) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v.frame.setEnabled(false);
					PresetSaver window = new PresetSaver(v);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException
	 */
	public PresetSaver(View v) throws IOException {
		int width = 315;
		int height = 190;

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
		txtArea.setText("現在のパラメーターをプリセットに保存します。");
		txtArea.setFont(new Font("メイリオ", Font.PLAIN, 13));
		txtArea.setBounds(15, 10, 290, 20);
		txtArea.setBackground(CeCommon.colorBackground);
		txtArea.setForeground(CeCommon.colorFontInactive);
		txtArea.setEditable(false);
		frame.getContentPane().add(txtArea);

		combo = new CeCombo();
		combo.setBounds(50, 40, 214, 23);
		combo.setFocusImage(frame);
		combo.setMaximumRowCount(12);
		setData(v);
		frame.getContentPane().add(combo);

		txtFld = new CeTextField() {
			@Override
			protected Document createDefaultModel() {
				return new TabCharLimitation();
			}
		};
		txtFld.setBounds(50, 73, 214, 23);
		txtFld.setFocusImage(frame);
		frame.getContentPane().add(txtFld);

		btnOK = new CeButton("決定") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				presetSave(v);
				v.frame.setEnabled(true);
				frame.dispose();
			}
		};
		btnOK.setFontSize(18);
		btnOK.setBounds(58, 108, 95, 38);
		btnOK.setFocusImage(frame);
		frame.getContentPane().add(btnOK);

		btnCancel = new CeButton("取り消し") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				v.frame.setEnabled(true);
				frame.dispose();
			}
		};
		btnCancel.setFontSize(18);
		btnCancel.setBounds(158, 108, 95, 38);
		btnCancel.setFocusImage(frame);
		frame.getContentPane().add(btnCancel);

	}

	private void setData(View v) throws IOException {
		List<Preset> list = PresetUtilty.loadPreset((String) v.comboCast.getSelectedItem());
		for (Preset preset : list) {
			combo.addItem(preset.getName());
		}
	}

	private void presetSave(View v) {
		try {
			List<Preset> list = PresetUtilty.loadPreset((String) v.comboCast.getSelectedItem());
			String str = "";
			if ("".equals(txtFld.getText())) {
				str = list.get(combo.getSelectedIndex()).getName();
			} else {
				str = txtFld.getText();
			}
			Preset preset = new Preset(
					str,
					Integer.parseInt(v.txtfldSize.getText()),
					Integer.parseInt(v.txtfldSpeed.getText()),
					Integer.parseInt(v.txtfldTone.getText()),
					Integer.parseInt(v.txtfldAlpha.getText()),
					Integer.parseInt(v.txtfldToneScale.getText()),
					Integer.parseInt(v.txtfldParameter1.getText()),
					Integer.parseInt(v.txtfldParameter2.getText()),
					Integer.parseInt(v.txtfldParameter3.getText()),
					Integer.parseInt(v.txtfldParameter4.getText()),
					v.fontColor,
					v.edgeColor);
			list.set(combo.getSelectedIndex(), preset);
			PresetUtilty.savePreset((String) v.comboCast.getSelectedItem(), list);
			v.btnPreset.get(combo.getSelectedIndex()).setText(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
