package client.core.gui.components;

import java.awt.Dimension;
import javax.swing.JPanel;

public class ExtendedTab extends JPanel
{
	private static final long serialVersionUID = 7214834676141878119L;

	private String action = "";

	public ExtendedTab(int width, int height)
	{
		super(false);

		setPreferredSize(new Dimension(width, height));
	}

	public void setAction(String code)
	{
		this.action = code;
	}

	public String getAction()
	{
		return this.action;
	}
}