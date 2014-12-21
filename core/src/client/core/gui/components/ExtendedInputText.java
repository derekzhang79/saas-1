package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import client.core.gui.FontStore;
import client.core.gui.TextCursorInterface;
import client.core.gui.ToolTipInterface;

public class ExtendedInputText extends JTextField implements ToolTipInterface, TextCursorInterface
{
	private static final long serialVersionUID = -2688662916424246754L;
	
	private String tooltip = "";

	public static final int DEFAULT_HEIGHT = 22;
	private static final int DEFAULT_FONT_SIZE = 12;

	public ExtendedInputText(int width)
	{
		setPreferredSize(new Dimension(width, ExtendedInputText.DEFAULT_HEIGHT));
		setFont(FontStore.getDefaultFont(false, ExtendedInputText.DEFAULT_FONT_SIZE));
		clearBorderColor();
	}

	public void set(String text)
	{
		setText(text);
		setCaretPosition(0);
	}

	public void clear()
	{
		set("");
		focus();
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

	public void setLength(int length)
	{
		setColumns(length);
	}

	private Border getDefaultBorder()
	{
		return BorderFactory.createEmptyBorder(0, 4, 0, 2);
	}

	public void setBorderColor(Color color)
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(color), getDefaultBorder()));
	}

	public void clearBorderColor()
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), getDefaultBorder()));
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

	public void focus()
	{
		requestFocus();
	}
}