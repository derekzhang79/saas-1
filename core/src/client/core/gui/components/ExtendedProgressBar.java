package client.core.gui.components;

import java.awt.Dimension;
import javax.swing.JProgressBar;
import client.core.gui.FontStore;

public class ExtendedProgressBar extends JProgressBar
{
	private static final long serialVersionUID = -206831559016927290L;

	private static final int DEFAULT_HEIGHT = 25;

	public ExtendedProgressBar(int width)
	{
		setPreferredSize(new Dimension(width, ExtendedProgressBar.DEFAULT_HEIGHT));
		setFont(FontStore.getDefaultFont());
		setStringPainted(false);
	}

	public void set(int value)
	{
		setValue(value);
	}

	public int get()
	{
		return getValue();
	}

	public void increment(int value)
	{
		setValue(getValue() + value);
	}
}