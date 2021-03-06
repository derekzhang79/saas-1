package client.app.configuration.usergroups.tasks;

import java.awt.Color;
import client.app.configuration.usergroups.gui.def.GUIEditUserGroup;
import client.core.gui.taks.Activity;

public abstract class BaseUserGroup<ResultType> extends Activity<ResultType>
{
	protected GUIEditUserGroup gui = new GUIEditUserGroup();

	public BaseUserGroup()
	{
		super(GUIEditUserGroup.PATH, Type.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.name.isEmpty())
		{
			showWarning(GUIEditUserGroup.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		}
		else
		{
			valid = true;
		}

		return valid;
	}
}