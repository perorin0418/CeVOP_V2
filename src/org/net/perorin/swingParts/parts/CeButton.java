package org.net.perorin.swingParts.parts;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.net.perorin.cv.CV;
import org.net.perorin.cv.CVImage;
import org.net.perorin.exception.CVException;
import org.net.perorin.toolkit.FileUtils;
import org.net.perorin.toolkit.Logger;

public class CeButton extends JButton {

	CVImage img = null;
	CVImage img_on = null;
	CVImage img_mouseOver = null;
	ImageIcon img_icon = null;
	ImageIcon img_on_icon = null;
	ImageIcon img_mouseOver_icon = null;
	CeFocus focus = null;
	Font font = new Font("メイリオ", Font.PLAIN, 16);

	String text = null;

	boolean isPress = false;

	public CeButton(String text) {
		super();
		this.text = text;
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		for (MouseListener ml : this.getMouseListeners()) {
			this.removeMouseListener(ml);
		}

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				CeButton.this.setIcon(img_icon);
				if (isPress) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						CeButton.this.mouseClicked();
						isPress = false;
						break;

					default:
						isPress = false;
						break;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				CeButton.this.setIcon(img_on_icon);
				isPress = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				CeButton.this.setIcon(img_icon);
				isPress = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				CeButton.this.setIcon(img_mouseOver_icon);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					CeButton.this.mouseClicked();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		this.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (focus != null) {
					focus.focusLost();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (focus != null) {
					focus.focusIn();
				}
			}
		});
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setFontSize(int fontSize) {
		this.font = new Font(this.font.getName(), this.font.getStyle(), fontSize);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		imageReflesh();
	}

	@Override
	public void setText(String text) {
		this.text = text;
		imageReflesh();
	}

	public void imageReflesh() {
		ArrayList<File> list = FileUtils.listFiles(new File("./META-INF/tmp"));
		for (File file : list) {
			if (file.getName().equals(String.valueOf(text.hashCode()))) {
				CVImage img = new CVImage("./META-INF/tmp/" + text.hashCode());
				if (img.getHeight() == this.getHeight() && img.getWidth() == this.getWidth()) {
					String imgName = "./META-INF/tmp/" + text.hashCode();
					String imgOnName = "./META-INF/tmp/" + (text + "on").hashCode();
					String imgMouseOver = "./META-INF/tmp/" + (text + "mouseOver").hashCode();

					img_icon = new ImageIcon(imgName);
					img_on_icon = new ImageIcon(imgOnName);
					img_mouseOver_icon = new ImageIcon(imgMouseOver);

					this.setIcon(img_icon);
					return;
				}
			}
		}

		img = new CVImage(CeCommon.btnImagePath);
		img_on = new CVImage(CeCommon.btnOnImagePath);
		img_mouseOver = new CVImage(CeCommon.btnMouseOverImagePath);

		CVImage textActiveImage = CV.stringImage(text, font, CeCommon.colorFontActive);
		CVImage textInactiveImage = CV.stringImage(text, font, CeCommon.colorFontInactive);

		img = CV.resize(img, this.getWidth(), this.getHeight());
		img_on = CV.resize(img_on, this.getWidth(), this.getHeight());
		img_mouseOver = CV.resize(img_mouseOver, this.getWidth(), this.getHeight());

		try {
			img = CV.merge(img, textInactiveImage, CV.MODE_CENTER);
			img_on = CV.merge(img_on, textActiveImage, CV.MODE_CENTER);
			img_mouseOver = CV.merge(img_mouseOver, textInactiveImage, CV.MODE_CENTER);
		} catch (CVException e) {
			Logger.error(CeCommon.errorPath, e.getStackTrace());
			//TODO エラー画面を表示する
		}

		String imgName = "./META-INF/tmp/" + text.hashCode();
		String imgOnName = "./META-INF/tmp/" + (text + "on").hashCode();
		String imgMouseOver = "./META-INF/tmp/" + (text + "mouseOver").hashCode();

		img.save(imgName);
		img_on.save(imgOnName);
		img_mouseOver.save(imgMouseOver);

		img_icon = new ImageIcon(imgName);
		img_on_icon = new ImageIcon(imgOnName);
		img_mouseOver_icon = new ImageIcon(imgMouseOver);

		this.setIcon(img_icon);
	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

	public void mouseClicked() {
		this.grabFocus();
	}
}
