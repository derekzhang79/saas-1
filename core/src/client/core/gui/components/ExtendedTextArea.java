package client.core.gui.components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import client.core.gui.fonts.FontStore;

public class ExtendedTextArea extends JTextArea implements ToolTipInterface, TextCursorInterface
{
	private static final long serialVersionUID = 3597254527053828041L;

	private String tooltip = "";

	public ExtendedTextArea()
	{
		setCaretPosition(0);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setFont(FontStore.getDefaultFont());
		setLineWrap(true);
		setWrapStyleWord(true);
		clearBorderColor();
	}

	@Override
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}

	@Override
	public String getTooltip()
	{
		return this.tooltip;
	}

	public void set(String text)
	{
		setText(text);
		setCaretPosition(0);
	}

	public String get()
	{
		return getText();
	}

	public boolean equals(String text)
	{
		return get().equals(text);
	}

	public boolean isEmpty()
	{
		return get().isEmpty();
	}

	public void setBottom()
	{
		setCaretPosition(getDocument().getLength());
	}

	private Border getDefaultBorder()
	{
		return BorderFactory.createEmptyBorder(2, 4, 0, 4);
	}

	public void setBorderColor(Color color)
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(color, 1), getDefaultBorder()));
	}

	public void clearBorderColor()
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY, 0), getDefaultBorder()));
	}

	public void focus()
	{
		requestFocus();
	}
}