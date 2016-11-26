package org.net.perorin.cevop2.parts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class CeCombo extends JComboBox<Object> {

	CeFocus focus = null;

	public CeCombo() {
		super();
		this.setBackground(CeCommon.colorTextBackground);
		this.setForeground(CeCommon.colorFontInactive);
		this.setFont(new Font("メイリオ", Font.PLAIN, 12));
		CeCombo self = this;
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
		this.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
				Component c = ((Container) popup).getComponent(0);
				if (c instanceof JScrollPane) {
					JScrollPane scrollpane = (JScrollPane) c;
					JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
					scrollBar.setOpaque(false);
					scrollBar.setUI(new BasicScrollBarUI() {
						private final Dimension d = new Dimension();

						@Override
						protected JButton createDecreaseButton(int orientation) {
							return new JButton() {
								@Override
								public Dimension getPreferredSize() {
									return d;
								}
							};
						}

						@Override
						protected JButton createIncreaseButton(int orientation) {
							return new JButton() {
								@Override
								public Dimension getPreferredSize() {
									return d;
								}
							};
						}

						@Override
						protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
							Color color;
							JScrollBar sb = (JScrollBar) c;
							if (!sb.isEnabled() || r.width > r.height) {
								return;
							} else {
								color = CeCommon.colorTextBackground;
							}
							Graphics2D g2 = (Graphics2D) g.create();
							g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							g2.setPaint(color);
							g2.fillRect(r.x - 1, r.y - 1, r.width + 1, r.height + 1);
							g2.setPaint(Color.WHITE);
							g2.drawRect(r.x - 1, r.y - 1, r.width + 1, r.height + 1);
							g2.dispose();
						}

						@Override
						protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
							Color color;
							JScrollBar sb = (JScrollBar) c;
							if (!sb.isEnabled() || r.width > r.height) {
								return;
							} else if (isDragging) {
								color = CeCommon.colorScrollbarDragging;
							} else if (isThumbRollover()) {
								color = CeCommon.colorScrollbarOver;
							} else {
								color = CeCommon.colorScrollbarDefault;
							}
							Graphics2D g2 = (Graphics2D) g.create();
							g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							g2.setPaint(color);
							g2.fillRect(r.x, r.y, r.width - 1, r.height - 1);
							g2.setPaint(CeCommon.colorBlack);
							g2.drawRect(r.x, r.y, r.width - 1, r.height - 1);
							g2.dispose();
						}

						@Override
						protected void setThumbBounds(int x, int y, int width, int height) {
							super.setThumbBounds(x, y, width, height);
							scrollbar.repaint();
						}
					});
				}
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		this.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				CeCombo.this.itemStateChanged();
			}
		});
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int index = self.getSelectedIndex() + e.getWheelRotation();
				if (index < 0) {
					index = 0;
				} else if (index > self.getItemCount() - 1) {
					index = self.getItemCount() - 1;
				}
				self.setSelectedIndex(index);
			}
		});
	}

	public void itemStateChanged() {

	}

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

	@Override
	public void setUI(ComboBoxUI ui) {
		super.setUI(new CeComboBoxUI());
	}

	private class CeComboBoxUI extends MetalComboBoxUI {
		protected JButton createArrowButton() {
			CeButton btn = new CeButton("▼") {
				@Override
				public void mouseClicked() {
				}
			};
			btn.setFontSize(10);
			btn.setBounds(0, 0, 17, 23);
			return btn;
		}
	}
}
