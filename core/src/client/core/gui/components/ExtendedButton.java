package client.core.gui.components;

import java.awt.Dimension;
import javax.swing.JButton;
import client.core.gui.fonts.FontStore;
import client.core.images.ImageStore;

public class ExtendedButton extends JButton implements ToolTipInterface
{
	private static final long serialVersionUID = 6674172574602555699L;
	
	private String tooltip = "";

	private static final int DEFAULT_BUTTON_HEIGHT = 33;

	public ExtendedButton(String text, int width, int height, String icon)
	{
		super(text);

		setPreferredSize(new Dimension(width, (height == 0) ? ExtendedButton.DEFAULT_BUTTON_HEIGHT : height));
		setFont(FontStore.getDefaultFont());
		setIcon(ImageStore.getIcon(icon));
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