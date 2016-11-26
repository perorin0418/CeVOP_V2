package org.net.perorin.cevop2.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.SwingConstants;
import javax.swing.text.Document;

import org.net.perorin.cevop2.contoroller.Controller;
import org.net.perorin.cevop2.model.Model;
import org.net.perorin.cevop2.model.Preset;
import org.net.perorin.cevop2.model.PresetUtilty;
import org.net.perorin.cevop2.parts.CeButton;
import org.net.perorin.cevop2.parts.CeCheckbox;
import org.net.perorin.cevop2.parts.CeCombo;
import org.net.perorin.cevop2.parts.CeCommon;
import org.net.perorin.cevop2.parts.CeFileIcon;
import org.net.perorin.cevop2.parts.CePanel;
import org.net.perorin.cevop2.parts.CeScrollPane;
import org.net.perorin.cevop2.parts.CeTextArea;
import org.net.perorin.cevop2.parts.CeTextField;
import org.net.perorin.cevop2.parts.stringLimitation.TabCharLimitation;
import org.net.perorin.toolkit.FileOperator;

public class View {

	public View self;
	public Color fontColor;
	public Color edgeColor;
	public Font font;
	public int selectingPresetNo;
	public boolean confirm;
	public int i;

	public JFrame frame; // フレーム
	public CeScrollPane textAreaScroll; // テキストエリアのスクロールバー
	public CeTextArea txtArea; // テキストエリア
	public CeButton btnVoiceListen; // 試聴
	public CeButton btnVoiceSave; // 音声保存
	public CeCheckbox chckbxTextSave; // テキスト保存
	public CePanel pnlSubtitles; // 字幕表示
	public JLabel lblSubtitles; // 字幕表示
	public CeCombo comboFont; // フォント
	public CeButton btnFontColor; // フォント色
	public CeButton btnEdgeColor; // フォント縁色
	public CeButton btnSubtitlesPreview; // 字幕プレビュー
	public CeButton btnSubtitlesSave; // 字幕保存
	public JLabel lblFontSize; // 字幕の大きさ
	public JLabel lblEdgeSize; // 字幕の縁の大きさ
	public CeTextField txtfldFontSize; // 字幕の大きさ
	public CeTextField txtfldEdgeSize; // 字幕縁の大きさ
	public CeTextField txtfldOutPath; // 字幕と音声の出力パス
	public CeButton btnOutPath; // 出力パス
	public CeCombo comboCast; // キャストの選択
	public JLabel lblSize; // 音声の大きさ
	public JLabel lblSpeed; // 音声の速さ
	public JLabel lblTone; // 音声の高さ
	public JLabel lblAlpha; // 音声の声質
	public JLabel lblToneScale; // 音声の抑揚
	public JLabel lblParameter1; // 音声のパラメーター1
	public JLabel lblParameter2; // 音声のパラメーター2
	public JLabel lblParameter3; // 音背のパラメーター3
	public JLabel lblParameter4; // 音声のパラメーター4
	public CeTextField txtfldSize;
	public CeTextField txtfldSpeed;
	public CeTextField txtfldTone;
	public CeTextField txtfldAlpha;
	public CeTextField txtfldToneScale;
	public CeTextField txtfldParameter1;
	public CeTextField txtfldParameter2;
	public CeTextField txtfldParameter3;
	public CeTextField txtfldParameter4;
	public CePanel pnlCast;
	public JLabel lblCast;
	public ArrayList<CeButton> btnPreset;
	public CeButton btnAllSave;
	public CePanel pnlVoiceFile;
	public CeFileIcon lblVoiceFile;
	public CePanel pnlSubtitlesFile;
	public CeFileIcon lblSubtitlesFile;

	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View() {
		initialize();
		Controller.call(this, Controller.INIT);
	}

	private void initialize() {
		self = this;

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 770, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(CeCommon.colorBackground);
		frame.setFocusTraversalPolicy(new CeFocusPolicy());
		frame.setIconImage(new ImageIcon(CeCommon.iconPath).getImage());
		frame.setTitle(CeCommon.title);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(CeCommon.colorBackground);
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("ファイル");
		mnFile.setForeground(CeCommon.colorFontInactive);
		mnFile.setBackground(CeCommon.colorTextBackground);
		mnFile.setFont(new Font("メイリオ", Font.PLAIN, 12));
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("終了");
		mntmExit.setForeground(CeCommon.colorFontInactive);
		mntmExit.setBackground(CeCommon.colorTextBackground);
		mntmExit.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("編集");
		mnEdit.setForeground(CeCommon.colorFontInactive);
		mnEdit.setBackground(CeCommon.colorTextBackground);
		mnEdit.setFont(new Font("メイリオ", Font.PLAIN, 12));
		menuBar.add(mnEdit);

		JMenuItem mntmPresetSave = new JMenuItem("プリセット保存");
		mntmPresetSave.setForeground(CeCommon.colorFontInactive);
		mntmPresetSave.setBackground(CeCommon.colorTextBackground);
		mntmPresetSave.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmPresetSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.PRESET_SAVE);
			}
		});
		mnEdit.add(mntmPresetSave);

		JMenuItem mntmConfig = new JMenuItem("設定");
		mntmConfig.setForeground(CeCommon.colorFontInactive);
		mntmConfig.setBackground(CeCommon.colorTextBackground);
		mntmConfig.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mnEdit.add(mntmConfig);

		JMenu mnBoot = new JMenu("起動");
		mnBoot.setForeground(CeCommon.colorFontInactive);
		mnBoot.setBackground(CeCommon.colorTextBackground);
		mnBoot.setFont(new Font("メイリオ", Font.PLAIN, 12));
		menuBar.add(mnBoot);

		JMenuItem mntmBootCeVIO = new JMenuItem("CeVIO");
		mntmBootCeVIO.setForeground(CeCommon.colorFontInactive);
		mntmBootCeVIO.setBackground(CeCommon.colorTextBackground);
		mntmBootCeVIO.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootCeVIO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_CEVIO);
			}
		});
		mnBoot.add(mntmBootCeVIO);

		JMenuItem mntmBootYukari = new JMenuItem("結月ゆかり");
		mntmBootYukari.setForeground(CeCommon.colorFontInactive);
		mntmBootYukari.setBackground(CeCommon.colorTextBackground);
		mntmBootYukari.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootYukari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_YUKARI);
			}
		});
		mnBoot.add(mntmBootYukari);

		JMenuItem mntmBootMaki = new JMenuItem("弦巻マキ");
		mntmBootMaki.setForeground(CeCommon.colorFontInactive);
		mntmBootMaki.setBackground(CeCommon.colorTextBackground);
		mntmBootMaki.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootMaki.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_MAKI);
			}
		});
		mnBoot.add(mntmBootMaki);

		JMenuItem mntmBootZunko = new JMenuItem("東北ずん子");
		mntmBootZunko.setForeground(CeCommon.colorFontInactive);
		mntmBootZunko.setBackground(CeCommon.colorTextBackground);
		mntmBootZunko.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootZunko.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_ZUNKO);
			}
		});
		mnBoot.add(mntmBootZunko);

		JMenuItem mntmBootAkane = new JMenuItem("琴葉茜");
		mntmBootAkane.setForeground(CeCommon.colorFontInactive);
		mntmBootAkane.setBackground(CeCommon.colorTextBackground);
		mntmBootAkane.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootAkane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_AKANE);
			}
		});
		mnBoot.add(mntmBootAkane);

		JMenuItem mntmBootAoi = new JMenuItem("琴葉葵");
		mntmBootAoi.setForeground(CeCommon.colorFontInactive);
		mntmBootAoi.setBackground(CeCommon.colorTextBackground);
		mntmBootAoi.setFont(new Font("メイリオ", Font.PLAIN, 12));
		mntmBootAoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.call(self, Controller.BOOT_AOI);
			}
		});
		mnBoot.add(mntmBootAoi);

		txtArea = new CeTextArea();
		txtArea.setBounds(20, 10, 410, 176);
		txtArea.setFocusImage(frame);
		txtArea.setFont(new Font("メイリオ", Font.PLAIN, 20));
		textAreaScroll = CeScrollPane.setScrollBar(txtArea);
		textAreaScroll.setBounds(20, 10, 410, 176);
		frame.getContentPane().add(textAreaScroll);

		btnVoiceListen = new CeButton("Listen") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.VOICE_LISTEN);
			}
		};
		btnVoiceListen.setFontSize(18);
		btnVoiceListen.setBounds(20, 192, 150, 30);
		btnVoiceListen.setFocusImage(frame);
		frame.getContentPane().add(btnVoiceListen);

		btnVoiceSave = new CeButton("Voice Save") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.VOICE_SAVE);
			}
		};
		btnVoiceSave.setFontSize(18);
		btnVoiceSave.setBounds(180, 192, 150, 30);
		btnVoiceSave.setFocusImage(frame);
		frame.getContentPane().add(btnVoiceSave);

		chckbxTextSave = new CeCheckbox(" Text Output");
		chckbxTextSave.setBounds(340, 202, 90, 15);
		chckbxTextSave.setFocusImage(frame);
		frame.getContentPane().add(chckbxTextSave);

		pnlSubtitles = new CePanel();
		pnlSubtitles.setBounds(20, 233, 410, 104);
		pnlSubtitles.setBackground(CeCommon.colorTextBackground);
		pnlSubtitles.setFocusImage(frame);
		pnlSubtitles.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		lblSubtitles = new JLabel("");
		lblSubtitles.setBounds(0, 0, 410, 104);
		pnlSubtitles.add(lblSubtitles);
		frame.getContentPane().add(pnlSubtitles);

		comboFont = new CeCombo();
		comboFont.setBounds(30, 343, 214, 23);
		comboFont.setFocusImage(frame);
		comboFont.setMaximumRowCount(20);
		frame.getContentPane().add(comboFont);

		btnFontColor = new CeButton("Font Color") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.FONT_COLOR);
			}
		};
		btnFontColor.setBounds(30, 372, 100, 28);
		btnFontColor.setFocusImage(frame);
		frame.getContentPane().add(btnFontColor);

		btnEdgeColor = new CeButton("Edge Color") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.EDGE_COLOR);
			}
		};
		btnEdgeColor.setBounds(136, 372, 100, 28);
		btnEdgeColor.setFocusImage(frame);
		frame.getContentPane().add(btnEdgeColor);

		btnSubtitlesPreview = new CeButton("Preview") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.SUBTITLES_PREVIEW);
			}
		};
		btnSubtitlesPreview.setFontSize(16);
		btnSubtitlesPreview.setBounds(30, 406, 100, 28);
		btnSubtitlesPreview.setFocusImage(frame);
		frame.getContentPane().add(btnSubtitlesPreview);

		btnSubtitlesSave = new CeButton("Save") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.SUBTITLES_SAVE);
			}
		};
		btnSubtitlesSave.setFontSize(16);
		btnSubtitlesSave.setBounds(136, 406, 100, 28);
		btnSubtitlesSave.setFocusImage(frame);
		frame.getContentPane().add(btnSubtitlesSave);

		lblFontSize = new JLabel("Font Size");
		lblFontSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblFontSize.setBounds(261, 366, 90, 15);
		lblFontSize.setForeground(CeCommon.colorFontInactive);
		lblFontSize.setFont(new Font("メイリオ", Font.PLAIN, 16));
		frame.getContentPane().add(lblFontSize);

		lblEdgeSize = new JLabel("Edge Size");
		lblEdgeSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblEdgeSize.setBounds(261, 396, 90, 15);
		lblEdgeSize.setForeground(CeCommon.colorFontInactive);
		lblEdgeSize.setFont(new Font("メイリオ", Font.PLAIN, 16));
		frame.getContentPane().add(lblEdgeSize);

		txtfldFontSize = new CeTextField();
		txtfldFontSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldFontSize.setBounds(355, 363, 75, 21);
		txtfldFontSize.setFocusImage(frame);
		frame.getContentPane().add(txtfldFontSize);

		txtfldEdgeSize = new CeTextField();
		txtfldEdgeSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldEdgeSize.setBounds(355, 393, 75, 21);
		txtfldEdgeSize.setFocusImage(frame);
		frame.getContentPane().add(txtfldEdgeSize);

		txtfldOutPath = new CeTextField() {
			@Override
			protected Document createDefaultModel() {
				return new TabCharLimitation();
			}
		};
		txtfldOutPath.setBounds(20, 445, 363, 21);
		txtfldOutPath.setFocusImage(frame);
		frame.getContentPane().add(txtfldOutPath);

		btnOutPath = new CeButton("...") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				File file = FileOperator.getPath("出力フォルダを選択", FileOperator.DIRECTORIES_ONLY);
				txtfldOutPath.setText(file.getPath());
			}
		};
		btnOutPath.setFontSize(16);
		btnOutPath.setBounds(390, 445, 40, 21);
		btnOutPath.setFocusImage(frame);
		frame.getContentPane().add(btnOutPath);

		comboCast = new CeCombo() {
			@Override
			public void itemStateChanged() {
				Controller.call(self, Controller.CAST_CHANGE);
			};
		};
		comboCast.setBounds(456, 10, 140, 23);
		comboCast.setFocusImage(frame);
		comboCast.setMaximumRowCount(13);
		frame.getContentPane().add(comboCast);

		lblSize = new JLabel("大きさ");
		lblSize.setBounds(458, 42, 50, 15);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setForeground(CeCommon.colorFontInactive);
		lblSize.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblSize);

		lblSpeed = new JLabel("速さ");
		lblSpeed.setBounds(458, 67, 50, 15);
		lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeed.setForeground(CeCommon.colorFontInactive);
		lblSpeed.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblSpeed);

		lblTone = new JLabel("高さ");
		lblTone.setBounds(458, 92, 50, 15);
		lblTone.setHorizontalAlignment(SwingConstants.CENTER);
		lblTone.setForeground(CeCommon.colorFontInactive);
		lblTone.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblTone);

		lblToneScale = new JLabel("抑揚");
		lblToneScale.setBounds(458, 117, 50, 15);
		lblToneScale.setHorizontalAlignment(SwingConstants.CENTER);
		lblToneScale.setForeground(CeCommon.colorFontInactive);
		lblToneScale.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblToneScale);

		lblAlpha = new JLabel("声質");
		lblAlpha.setBounds(458, 142, 50, 15);
		lblAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlpha.setForeground(CeCommon.colorFontInactive);
		lblAlpha.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblAlpha);

		lblParameter1 = new JLabel("パラ１");
		lblParameter1.setBounds(458, 167, 50, 15);
		lblParameter1.setHorizontalAlignment(SwingConstants.CENTER);
		lblParameter1.setForeground(CeCommon.colorFontInactive);
		lblParameter1.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblParameter1);

		lblParameter2 = new JLabel("パラ２");
		lblParameter2.setBounds(458, 192, 50, 15);
		lblParameter2.setHorizontalAlignment(SwingConstants.CENTER);
		lblParameter2.setForeground(CeCommon.colorFontInactive);
		lblParameter2.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblParameter2);

		lblParameter3 = new JLabel("パラ３");
		lblParameter3.setBounds(458, 217, 50, 15);
		lblParameter3.setHorizontalAlignment(SwingConstants.CENTER);
		lblParameter3.setForeground(CeCommon.colorFontInactive);
		lblParameter3.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblParameter3);

		lblParameter4 = new JLabel("パラ４");
		lblParameter4.setBounds(458, 242, 50, 15);
		lblParameter4.setHorizontalAlignment(SwingConstants.CENTER);
		lblParameter4.setForeground(CeCommon.colorFontInactive);
		lblParameter4.setFont(new Font("メイリオ", Font.PLAIN, 12));
		frame.getContentPane().add(lblParameter4);

		txtfldSize = new CeTextField();
		txtfldSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldSize.setBounds(516, 38, 73, 22);
		txtfldSize.setFocusImage(frame);
		frame.getContentPane().add(txtfldSize);

		txtfldSpeed = new CeTextField();
		txtfldSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldSpeed.setBounds(516, 63, 73, 22);
		txtfldSpeed.setFocusImage(frame);
		frame.getContentPane().add(txtfldSpeed);

		txtfldTone = new CeTextField();
		txtfldTone.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldTone.setBounds(516, 88, 73, 22);
		txtfldTone.setFocusImage(frame);
		frame.getContentPane().add(txtfldTone);

		txtfldToneScale = new CeTextField();
		txtfldToneScale.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldToneScale.setBounds(516, 113, 73, 22);
		txtfldToneScale.setFocusImage(frame);
		frame.getContentPane().add(txtfldToneScale);

		txtfldAlpha = new CeTextField();
		txtfldAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldAlpha.setBounds(516, 138, 73, 22);
		txtfldAlpha.setFocusImage(frame);
		frame.getContentPane().add(txtfldAlpha);

		txtfldParameter1 = new CeTextField();
		txtfldParameter1.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldParameter1.setBounds(516, 163, 73, 22);
		txtfldParameter1.setFocusImage(frame);
		frame.getContentPane().add(txtfldParameter1);

		txtfldParameter2 = new CeTextField();
		txtfldParameter2.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldParameter2.setBounds(516, 188, 73, 22);
		txtfldParameter2.setFocusImage(frame);
		frame.getContentPane().add(txtfldParameter2);

		txtfldParameter3 = new CeTextField();
		txtfldParameter3.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldParameter3.setBounds(516, 213, 73, 22);
		txtfldParameter3.setFocusImage(frame);
		frame.getContentPane().add(txtfldParameter3);

		txtfldParameter4 = new CeTextField();
		txtfldParameter4.setHorizontalAlignment(SwingConstants.CENTER);
		txtfldParameter4.setBounds(516, 238, 73, 22);
		txtfldParameter4.setFocusImage(frame);
		frame.getContentPane().add(txtfldParameter4);

		pnlCast = new CePanel();
		pnlCast.setBounds(446, 264, 160, 165);
		pnlCast.setBackground(CeCommon.colorBackground);
		lblCast = new JLabel("");
		lblCast.setBounds(0, 0, 160, 165);
		lblCast.setIcon(new ImageIcon("./img/sasara.png"));
		lblCast.addMouseListener(new MouseListener() {
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
				Controller.call(self, Controller.VOICEROID_ACTIVE);
			}
		});
		pnlCast.add(lblCast);
		frame.getContentPane().add(pnlCast);

		btnPreset = new ArrayList<CeButton>();
		for (i = 0; i < 12; i++) {
			btnPreset.add(new CeButton("Preset") {
				public int myNumber = i;

				@Override
				public void mouseClicked() {
					super.mouseClicked();
					try {
						List<Preset> list = PresetUtilty.loadPreset((String) comboCast.getSelectedItem());
						Model.parameterUpdate(self, list.get(myNumber));
						selectingPresetNo = myNumber;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			btnPreset.get(i).setFontSize(16);
			btnPreset.get(i).setBounds(622, 10 + 31 * i, 122, 25);
			btnPreset.get(i).setFocusImage(frame);
			frame.getContentPane().add(btnPreset.get(i));
		}

		btnAllSave = new CeButton("All Save") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				Controller.call(self, Controller.ALL_SAVE);
			}
		};
		btnAllSave.setFontSize(16);
		btnAllSave.setBounds(456, 435, 288, 36);
		btnAllSave.setFocusImage(frame);
		frame.getContentPane().add(btnAllSave);

		pnlVoiceFile = new CePanel();
		pnlVoiceFile.setBounds(625, 378, 50, 50);
		pnlVoiceFile.setBackground(CeCommon.colorBackground);
		lblVoiceFile = new CeFileIcon(new ImageIcon(CeCommon.voiceFile));
		lblVoiceFile.setBounds(0, 0, 50, 50);
		pnlVoiceFile.add(lblVoiceFile);
		frame.getContentPane().add(pnlVoiceFile);

		pnlSubtitlesFile = new CePanel();
		pnlSubtitlesFile.setBounds(683, 378, 50, 50);
		pnlSubtitlesFile.setBackground(CeCommon.colorBackground);
		lblSubtitlesFile = new CeFileIcon(new ImageIcon(CeCommon.subtitlesFile));
		lblSubtitlesFile.setBounds(0, 0, 50, 50);
		pnlSubtitlesFile.add(lblSubtitlesFile);
		frame.getContentPane().add(pnlSubtitlesFile);
	}

	private class CeFocusPolicy extends LayoutFocusTraversalPolicy {
		@Override
		public Component getComponentAfter(Container aContainer, Component aComponent) {
			if (aComponent == txtArea) {
				return btnVoiceListen;
			} else if (aComponent == btnVoiceListen) {
				return btnVoiceSave;
			} else if (aComponent == btnVoiceSave) {
				return chckbxTextSave.checkbox;
			} else if (aComponent == chckbxTextSave.checkbox) {
				return comboFont;
			} else if (aComponent == comboFont) {
				return btnFontColor;
			} else if (aComponent == btnFontColor) {
				return btnEdgeColor;
			} else if (aComponent == btnEdgeColor) {
				return btnSubtitlesPreview;
			} else if (aComponent == btnSubtitlesPreview) {
				return btnSubtitlesSave;
			} else if (aComponent == btnSubtitlesSave) {
				return txtfldFontSize;
			} else if (aComponent == txtfldFontSize) {
				return txtfldEdgeSize;
			} else if (aComponent == txtfldEdgeSize) {
				return txtfldOutPath;
			} else if (aComponent == txtfldOutPath) {
				return btnOutPath;
			} else if (aComponent == btnOutPath) {
				return comboCast;
			} else if (aComponent == comboCast) {
				return txtfldSize;
			} else if (aComponent == txtfldSize) {
				return txtfldSpeed;
			} else if (aComponent == txtfldSpeed) {
				return txtfldTone;
			} else if (aComponent == txtfldTone) {
				return txtfldToneScale;
			} else if (aComponent == txtfldToneScale) {
				if (txtfldAlpha.isVisible()) {
					return txtfldAlpha;
				} else {
					return btnPreset.get(0);
				}
			} else if (aComponent == txtfldAlpha) {
				return txtfldParameter1;
			} else if (aComponent == txtfldParameter1) {
				return txtfldParameter2;
			} else if (aComponent == txtfldParameter2) {
				if (txtfldParameter3.isVisible()) {
					return txtfldParameter3;
				} else {
					return btnPreset.get(0);
				}
			} else if (aComponent == txtfldParameter3) {
				if (txtfldParameter4.isVisible()) {
					return txtfldParameter4;
				} else {
					return btnPreset.get(0);
				}
			} else if (aComponent == txtfldParameter4) {
				return btnPreset.get(0);
			} else if (aComponent == btnPreset.get(0)) {
				return btnPreset.get(1);
			} else if (aComponent == btnPreset.get(1)) {
				return btnPreset.get(2);
			} else if (aComponent == btnPreset.get(2)) {
				return btnPreset.get(3);
			} else if (aComponent == btnPreset.get(3)) {
				return btnPreset.get(4);
			} else if (aComponent == btnPreset.get(4)) {
				return btnPreset.get(5);
			} else if (aComponent == btnPreset.get(5)) {
				return btnPreset.get(6);
			} else if (aComponent == btnPreset.get(6)) {
				return btnPreset.get(7);
			} else if (aComponent == btnPreset.get(7)) {
				return btnPreset.get(8);
			} else if (aComponent == btnPreset.get(8)) {
				return btnPreset.get(9);
			} else if (aComponent == btnPreset.get(9)) {
				return btnPreset.get(10);
			} else if (aComponent == btnPreset.get(10)) {
				return btnPreset.get(11);
			} else if (aComponent == btnPreset.get(11)) {
				return btnAllSave;
			} else if (aComponent == btnAllSave) {
				return txtArea;
			} else {
				return super.getComponentAfter(aContainer, aComponent);
			}
		}

		@Override
		public Component getComponentBefore(Container aContainer, Component aComponent) {
			if (aComponent == txtArea) {
				return btnAllSave;
			} else if (aComponent == btnVoiceListen) {
				return txtArea;
			} else if (aComponent == btnVoiceSave) {
				return btnVoiceListen;
			} else if (aComponent == chckbxTextSave.checkbox) {
				return btnVoiceSave;
			} else if (aComponent == comboFont) {
				return chckbxTextSave.checkbox;
			} else if (aComponent == btnFontColor) {
				return comboFont;
			} else if (aComponent == btnEdgeColor) {
				return btnFontColor;
			} else if (aComponent == btnSubtitlesPreview) {
				return btnEdgeColor;
			} else if (aComponent == btnSubtitlesSave) {
				return btnSubtitlesPreview;
			} else if (aComponent == txtfldFontSize) {
				return btnSubtitlesSave;
			} else if (aComponent == txtfldEdgeSize) {
				return txtfldFontSize;
			} else if (aComponent == txtfldOutPath) {
				return txtfldEdgeSize;
			} else if (aComponent == btnOutPath) {
				return txtfldOutPath;
			} else if (aComponent == comboCast) {
				return btnOutPath;
			} else if (aComponent == txtfldSize) {
				return comboCast;
			} else if (aComponent == txtfldSpeed) {
				return txtfldSize;
			} else if (aComponent == txtfldTone) {
				return txtfldSpeed;
			} else if (aComponent == txtfldAlpha) {
				return txtfldToneScale;
			} else if (aComponent == txtfldToneScale) {
				return txtfldTone;
			} else if (aComponent == txtfldParameter1) {
				return txtfldAlpha;
			} else if (aComponent == txtfldParameter2) {
				return txtfldParameter1;
			} else if (aComponent == txtfldParameter3) {
				return txtfldParameter2;
			} else if (aComponent == txtfldParameter4) {
				return txtfldParameter3;
			} else if (aComponent == btnPreset.get(0)) {
				if (txtfldParameter4.isVisible()) {
					return txtfldParameter4;
				} else if (txtfldParameter3.isVisible()) {
					return txtfldParameter3;
				} else if (txtfldParameter2.isVisible()) {
					return txtfldParameter2;
				} else {
					return txtfldToneScale;
				}
			} else if (aComponent == btnPreset.get(1)) {
				return btnPreset.get(0);
			} else if (aComponent == btnPreset.get(2)) {
				return btnPreset.get(1);
			} else if (aComponent == btnPreset.get(3)) {
				return btnPreset.get(2);
			} else if (aComponent == btnPreset.get(4)) {
				return btnPreset.get(3);
			} else if (aComponent == btnPreset.get(5)) {
				return btnPreset.get(4);
			} else if (aComponent == btnPreset.get(6)) {
				return btnPreset.get(5);
			} else if (aComponent == btnPreset.get(7)) {
				return btnPreset.get(6);
			} else if (aComponent == btnPreset.get(8)) {
				return btnPreset.get(7);
			} else if (aComponent == btnPreset.get(9)) {
				return btnPreset.get(8);
			} else if (aComponent == btnPreset.get(10)) {
				return btnPreset.get(9);
			} else if (aComponent == btnPreset.get(11)) {
				return btnPreset.get(10);
			} else if (aComponent == btnAllSave) {
				return btnPreset.get(11);
			} else {
				return super.getComponentBefore(aContainer, aComponent);
			}
		}
	}
}
