package client.app.support.tasks;

import java.awt.Color;
import client.app.support.gui.def.GUIEditSupport;
import client.core.gui.taks.Activity;

public abstract class BaseSupport extends Activity<Boolean>
{
	protected GUIEditSupport gui = new GUIEditSupport();

	public BaseSupport()
	{
		super(GUIEditSupport.PATH, Type.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.name.isEmpty())
		{
			showWarning(GUIEditSupport.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		}
		else if (this.gui.module.isEmpty())
		{
			showWarning(GUIEditSupport.Literals.MODULE_REQUIRED);
			this.gui.module.focus();
			this.gui.module.setBorderColor(Color.RED);
		}
		else if (this.gui.description.isEmpty())
		{
			showWarning(GUIEditSupport.Literals.DESCRIPTION_REQUIRED);
			this.gui.description.focus();
			this.gui.description.setBorderColor(Color.RED);
		}
		else
		{
			valid = true;
		}

		return valid;
	}
}