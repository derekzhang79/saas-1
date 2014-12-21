package client.app.contacts.clients.tasks;

import java.awt.Color;
import client.app.contacts.clients.gui.def.GUIEditClient;
import client.core.gui.taks.OptionTask;

public abstract class BaseClient<ResultType> extends OptionTask<ResultType>
{
	protected GUIEditClient gui = new GUIEditClient();

	public BaseClient()
	{
		super(GUIEditClient.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.firstName.isEmpty())
		{
			showWarning(GUIEditClient.Literals.NAME_REQUIRED);
			this.gui.firstName.setBorderColor(Color.RED);
			this.gui.firstName.focus();
		}
		else if (this.gui.telephone.isEmpty() && this.gui.mobile.isEmpty())
		{
			showWarning(GUIEditClient.Literals.PHONE_REQUIRED);
			this.gui.telephone.setBorderColor(Color.RED);
			this.gui.mobile.setBorderColor(Color.RED);
			this.gui.telephone.focus();
		}
		else
		{
			valid = true;
		}

		return valid;
	}
}