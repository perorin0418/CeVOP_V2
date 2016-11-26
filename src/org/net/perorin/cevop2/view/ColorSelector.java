package org.net.perorin.cevop2.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.net.perorin.cevop2.contoroller.Controller;
import org.net.perorin.cevop2.parts.CeButton;
import org.net.perorin.cevop2.parts.CeCommon;
import org.net.perorin.cevop2.parts.CePanel;
import org.net.perorin.cevop2.parts.CeTextField;
import org.net.perorin.cv.CVImage;

public class ColorSelector implements MouseListener, MouseMotionListener, ChangeListener, KeyListener {

	public JFrame frame;
	private final ImageIcon colorPallet = new ImageIcon("./img/parts/color.png");
	private final CVImage colorPallet_cv = new CVImage("./img/parts/color.png");
	private final BufferedImage colorPallet_bi = colorPallet_cv.getImageBuffer();
	private final WritableRaster colorPallet_wr = colorPallet_bi.getRaster();
	private BufferedImage biPreview = new BufferedImage(138, 45, BufferedImage.TYPE_INT_ARGB);
	private JLabel lblPreview;
	private CeTextField txtFldR;
	private CeTextField txtFldG;
	private CeTextField txtFldB;
	private CeTextField txtFldA;
	private JSlider slider;
	private boolean isFontColor;
	private CePanel pnlColorPallet;
	private JLabel lblColorPallet;
	private JLabel lblR;
	private JLabel lblG;
	private JLabel lblB;
	private JLabel lblA;
	private CePanel pnlPreview;
	private CeButton btnOK;
	private CeButton btnCancel;

	public static void showColorSelector(Color color, View v, boolean isFontColor) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					v.frame.setEnabled(false);
					ColorSelector window = new ColorSelector(color, v, isFontColor);
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 *
	 * @throws IOException
	 */
	private ColorSelector(Color color, View v, boolean isFontColor) {
		int width = 460;
		int height = 430;
		this.isFontColor = isFontColor;

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(v.frame.getBounds().x + v.frame.getWidth() / 2 - width / 2, v.frame.getBounds().y + v.frame.getHeight() / 2 - height / 2, width, height);
		frame.setTitle("色の選択");
		frame.setIconImage(new ImageIcon(CeCommon.iconPath).getImage());
		frame.getContentPane().setBackground(CeCommon.colorBackground);
		frame.getContentPane().setLayout(null);
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

		pnlColorPallet = new CePanel();
		pnlColorPallet.setBounds(12, 10, 400, 286);
		pnlColorPallet.setBackground(CeCommon.colorTextBackground);
		pnlColorPallet.setFocusImage(frame);
		lblColorPallet = new JLabel(colorPallet);
		lblColorPallet.setBounds(0, 0, 400, 286);
		pnlColorPallet.add(lblColorPallet);
		lblColorPallet.addMouseListener(this);
		lblColorPallet.addMouseMotionListener(this);
		frame.getContentPane().add(pnlColorPallet);

		txtFldR = new CeTextField();
		txtFldR.setText(String.valueOf(color.getRed()));
		txtFldR.setHorizontalAlignment(SwingConstants.CENTER);
		txtFldR.setBounds(47, 316, 44, 25);
		txtFldR.addKeyListener(this);
		txtFldR.setFocusImage(frame);
		frame.getContentPane().add(txtFldR);
		txtFldR.setColumns(10);

		txtFldG = new CeTextField();
		txtFldG.setText(String.valueOf(color.getGreen()));
		txtFldG.setHorizontalAlignment(SwingConstants.CENTER);
		txtFldG.setBounds(112, 316, 44, 25);
		txtFldG.addKeyListener(this);
		txtFldG.setFocusImage(frame);
		frame.getContentPane().add(txtFldG);
		txtFldG.setColumns(10);

		txtFldB = new CeTextField();
		txtFldB.setText(String.valueOf(color.getBlue()));
		txtFldB.setHorizontalAlignment(SwingConstants.CENTER);
		txtFldB.setBounds(177, 316, 44, 25);
		txtFldB.addKeyListener(this);
		txtFldB.setFocusImage(frame);
		frame.getContentPane().add(txtFldB);
		txtFldB.setColumns(10);

		txtFldA = new CeTextField();
		txtFldA.setText(String.valueOf(color.getAlpha()));
		txtFldA.setHorizontalAlignment(SwingConstants.CENTER);
		txtFldA.setBounds(242, 316, 44, 25);
		txtFldA.addKeyListener(this);
		txtFldA.setFocusImage(frame);
		frame.getContentPane().add(txtFldA);
		txtFldA.setColumns(10);

		lblR = new JLabel("R");
		lblR.setBounds(32, 321, 14, 13);
		lblR.setForeground(CeCommon.colorFontInactive);
		frame.getContentPane().add(lblR);

		lblG = new JLabel("G");
		lblG.setBounds(97, 321, 14, 13);
		lblG.setForeground(CeCommon.colorFontInactive);
		frame.getContentPane().add(lblG);

		lblB = new JLabel("B");
		lblB.setBounds(162, 321, 14, 13);
		lblB.setForeground(CeCommon.colorFontInactive);
		frame.getContentPane().add(lblB);

		lblA = new JLabel("A");
		lblA.setBounds(227, 321, 14, 13);
		lblA.setForeground(CeCommon.colorFontInactive);
		frame.getContentPane().add(lblA);

		slider = new JSlider();
		slider.setMaximum(255);
		slider.setValue(color.getAlpha());
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(421, 20, 29, 266);
		slider.setBackground(CeCommon.colorBackground);
		slider.addChangeListener(this);
		frame.getContentPane().add(slider);

		pnlPreview = new CePanel();
		pnlPreview.setBounds(304, 306, 138, 45);
		pnlPreview.setFocusImage(frame);
		frame.getContentPane().add(pnlPreview);
		pnlPreview.setLayout(null);
		lblPreview = new JLabel("");
		lblPreview.setBounds(0, 0, 138, 45);
		pnlPreview.add(lblPreview);

		btnOK = new CeButton("決定") {
			@Override
			public void mouseClicked() {
				super.mouseClicked();
				process_OK(v);
				v.frame.setEnabled(true);
				frame.dispose();
			}
		};
		btnOK.setBounds(124, 361, 91, 30);
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
		btnCancel.setBounds(261, 361, 91, 30);
		btnCancel.setFocusImage(frame);
		frame.getContentPane().add(btnCancel);

		previewUpdate(color);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			Point mousePos = e.getPoint();
			int buf[] = new int[colorPallet_wr.getNumDataElements()];
			colorPallet_wr.getPixel(mousePos.x, mousePos.y, buf);
			txtFldR.setText(String.valueOf(buf[0]));
			txtFldG.setText(String.valueOf(buf[1]));
			txtFldB.setText(String.valueOf(buf[2]));
			previewUpdate(new Color(buf[0], buf[1], buf[2], slider.getValue()));
		} catch (ArrayIndexOutOfBoundsException error) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point mousePos = e.getPoint();
		int buf[] = new int[colorPallet_wr.getNumDataElements()];
		colorPallet_wr.getPixel(mousePos.x, mousePos.y, buf);
		txtFldR.setText(String.valueOf(buf[0]));
		txtFldG.setText(String.valueOf(buf[1]));
		txtFldB.setText(String.valueOf(buf[2]));
		previewUpdate(new Color(buf[0], buf[1], buf[2], slider.getValue()));
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		txtFldA.setText(String.valueOf(slider.getValue()));
		previewUpdate(new Color(Integer.parseInt(txtFldR.getText()), Integer.parseInt(txtFldG.getText()), Integer.parseInt(txtFldB.getText()), slider.getValue()));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		try {
			slider.setValue(Integer.parseInt(txtFldA.getText()));
			previewUpdate(new Color(Integer.parseInt(txtFldR.getText()), Integer.parseInt(txtFldG.getText()), Integer.parseInt(txtFldB.getText()), Integer.parseInt(txtFldA.getText())));
		} catch (NumberFormatException error) {
		}
	}

	private void previewUpdate(Color color) {
		Graphics2D g2d = biPreview.createGraphics();
		g2d.setBackground(color);
		g2d.clearRect(0, 0, 138, 45);
		ImageIcon buf = new ImageIcon(biPreview);
		lblPreview.setIcon(buf);
	}

	private void process_OK(View v) {
		if (isFontColor) {
			v.fontColor = new Color(Integer.parseInt(txtFldR.getText()), Integer.parseInt(txtFldG.getText()), Integer.parseInt(txtFldB.getText()), Integer.parseInt(txtFldA.getText()));
			Controller.call(v, Controller.SUBTITLES_PREVIEW);
		} else {
			v.edgeColor = new Color(Integer.parseInt(txtFldR.getText()), Integer.parseInt(txtFldG.getText()), Integer.parseInt(txtFldB.getText()), Integer.parseInt(txtFldA.getText()));
			Controller.call(v, Controller.SUBTITLES_PREVIEW);
		}
	}

	// 以下使用していない

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
