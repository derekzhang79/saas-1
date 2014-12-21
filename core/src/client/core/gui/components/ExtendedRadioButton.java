package client.core.gui.components;

import java.awt.Dimension;
import javax.swing.JRadioButton;

public class ExtendedRadioButton extends JRadioButton implements ToolTipInterface
{
	private static final long serialVersionUID = -3890402544245786320L;
	
	private String tooltip = "";

	private static final int DEFAULT_WIDTH = 17;

	public ExtendedRadioButton()
	{
		setPreferredSize(new Dimension(ExtendedRadioButton.DEFAULT_WIDTH, ExtendedRadioButton.DEFAULT_WIDTH));
	}

	public void select(boolean select)
	{
		setSelected(select);
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
}