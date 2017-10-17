package org.net.perorin.swingParts.parts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CeScrollPane extends JScrollPane {

	CeFocus focus = null;

	public CeScrollPane(JComponent c) {
		super(c);
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

	public void setFocusImage(JFrame frame) {
		focus = new CeFocus(this.getBounds());
		for (Label buf : focus.getLabels()) {
			frame.getContentPane().add(buf);
		}
	}

	public static CeScrollPane setScrollBar(JComponent c) {
		CeScrollPane scrollPane = new CeScrollPane(c) {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false; // JScrollBar is overlap
			}
		};
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setComponentZOrder(scrollPane.getVerticalScrollBar(), 0);
		scrollPane.setComponentZOrder(scrollPane.getViewport(), 1);
		scrollPane.getVerticalScrollBar().setOpaque(false);

		scrollPane.setLayout(new ScrollPaneLayout() {
			@Override
			public void layoutContainer(Container parent) {
				JScrollPane scrollPane = (JScrollPane) parent;

				Rectangle availR = scrollPane.getBounds();
				availR.x = availR.y = 0;

				Insets insets = parent.getInsets();
				availR.x = insets.left - 1;
				availR.y = insets.top - 1;
				availR.width -= insets.left + insets.right - 3;
				availR.height -= insets.top + insets.bottom - 3;

				Rectangle vsbR = new Rectangle();
				vsbR.width = 12;
				vsbR.height = availR.height;
				vsbR.x = availR.x + availR.width - vsbR.width;
				vsbR.y = availR.y;

				if (viewport != null) {
					viewport.setBounds(availR);
				}
				if (vsb != null) {
					vsb.setVisible(true);
					vsb.setBounds(vsbR);
				}
			}
		});
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			private final Color defaultColor = CeCommon.colorScrollbarDefault;
			private final Color draggingColor = CeCommon.colorScrollbarDragging;
			private final Color rolloverColor = CeCommon.colorScrollbarOver;
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
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
				Color color;
				JScrollBar sb = (JScrollBar) c;
				if (!sb.isEnabled() || r.width > r.height) {
					return;
				} else if (isDragging) {
					color = draggingColor;
				} else if (isThumbRollover()) {
					color = rolloverColor;
				} else {
					color = defaultColor;
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
		return scrollPane;
	}

}
