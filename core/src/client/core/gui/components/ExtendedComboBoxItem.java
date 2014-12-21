package client.core.gui.components;

import javax.swing.Icon;
import client.core.images.ImageStore;

public class ExtendedComboBoxItem
{
	private final String code;
	private final String value;
	private Icon icon = null;

	public ExtendedComboBoxItem(String code, String value)
	{
		this.code = code;
		this.value = value;
	}

	public String getCode()
	{
		return this.code;
	}

	public String getValue()
	{
		return this.value;
	}

	public Icon getIcon()
	{
		return this.icon;
	}

	public boolean hasIcon()
	{
		return (this.icon != null);
	}

	public void setIcon(String name)
	{
		this.icon = ImageStore.getIcon(name);
	}

	@Override
	public String toString()
	{
		return this.value;
	}
}