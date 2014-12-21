package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import client.core.gui.FontStore;

public class ExtendedLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private static final Color DEFAULT_BG_COLOR = new Color(220, 220, 220);
	
	public ExtendedLabel(String text) {
		super(text);
		
		setFont(FontStore.getDefaultFont(true));
	}
	
	public void setBorder(int topPadding, int leftPadding, int bottomPadding, int rightPadding) {
		setBorder(topPadding, leftPadding, bottomPadding, rightPadding, ExtendedLabel.DEFAULT_BG_COLOR);
	}
	
	public void setBorder(int topPadding, int leftPadding, int bottomPadding, int rightPadding, Color bgColor) {
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding)));
		setOpaque(true);
		setBackground(bgColor);
	}
	
	public void setLabelSize(int width, int height) {
		setPreferredSize(new Dimension(width, height));
	}
	
	public void setBold(boolean value) {
		setFont(FontStore.getDefaultFont(value));
	}
	
	public void set(String text) {
		setText(text);
	}
}