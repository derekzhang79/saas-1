package client.app.workshop.tasks;

import java.awt.Color;
import share.app.contacts.clients.Client;
import client.app.contacts.clients.tasks.SearchClient;
import client.app.workshop.gui.def.GUIEditFixOrder;
import client.core.gui.taks.Activity;

public abstract class BaseFixOrder<ResultType> extends Activity<ResultType>
{
	protected int clientID = 0;

	protected GUIEditFixOrder gui = new GUIEditFixOrder();

	public BaseFixOrder()
	{
		super(GUIEditFixOrder.PATH, Type.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.clientName.isEmpty())
		{
			showWarning(GUIEditFixOrder.Literals.CLIENT_REQUIRED);
			this.gui.clientName.setBorderColor(Color.RED);
			this.gui.clientName.focus();
		}
		else if (this.gui.start.isEmpty())
		{
			showWarning(GUIEditFixOrder.Literals.START_REQUIRED);
			this.gui.start.setBorderColor(Color.RED);
			this.gui.start.focus();
		}
		else if (this.gui.finish.isEmpty())
		{
			showWarning(GUIEditFixOrder.Literals.FINISH_REQUIRED);
			this.gui.finish.setBorderColor(Color.RED);
			this.gui.finish.focus();
		}
		else if (this.gui.finish.isBefore(this.gui.start))
		{
			showWarning(GUIEditFixOrder.Literals.FINISH_DATE_INVALID);
			this.gui.finish.setBorderColor(Color.RED);
			this.gui.finish.focus();
		}
		else if (this.gui.status.isEmpty())
		{
			showWarning(GUIEditFixOrder.Literals.STATUS_REQUIRED);
			this.gui.status.setBorderColor(Color.RED);
			this.gui.status.focus();
		}
		else
		{
			valid = true;
		}

		return valid;
	}

	protected void searchClient()
	{
		SearchClient task = new SearchClient();
		Client client = task.run();

		if (client != null)
		{
			this.clientID = client.id;
			this.gui.clientName.set(client.name);
		}

		this.gui.clientName.focus();
	}
}