package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import client.core.gui.FontStore;
import client.core.gui.TextCursorInterface;
import client.core.gui.ToolTipInterface;

public class ExtendedInputPassword extends JPasswordField implements ToolTipInterface, TextCursorInterface {
	
	private static final long serialVersionUID = 1L;
	
	private String tooltip = "";
	
	public ExtendedInputPassword(int width) {
		setPreferredSize(new Dimension(width, ExtendedInputText.DEFAULT_HEIGHT));
		setFont(FontStore.getDefaultFont(false));
		clearBorderColor();
	}
	
	public void set(String text) {
		setText(text);
		setCaretPosition(0);
	}
	
	public String get() {
		return String.valueOf(getPassword());
	}
	
	public void setLength(int length) {
		setColumns(length);
	}
	
	public boolean isEmpty() {
		return get().isEmpty();
	}
	
	private Border getDefaultBorder() {
		return BorderFactory.createEmptyBorder(0, 4, 0, 2);
	}
	
	public void setBorderColor(Color color) {
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(color), getDefaultBorder()));
	}
	
	public void clearBorderColor() {
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), getDefaultBorder()));
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public String getTooltip() {
		return this.tooltip;
	}
	
	public void focus() {
		requestFocus();
	}
}