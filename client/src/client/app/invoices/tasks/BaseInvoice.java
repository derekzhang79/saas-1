package client.app.invoices.tasks;

import java.awt.Color;
import share.app.contacts.clients.Client;
import client.app.contacts.clients.tasks.SearchClient;
import client.app.invoices.gui.def.GUIEditInvoice;
import client.core.gui.taks.Activity;

public abstract class BaseInvoice<ResultType> extends Activity<ResultType>
{
	protected int clientID = 0;

	protected GUIEditInvoice gui = new GUIEditInvoice();

	public BaseInvoice()
	{
		super(GUIEditInvoice.PATH, Type.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.clientName.isEmpty())
		{
			showWarning(GUIEditInvoice.Literals.CLIENT_REQUIRED);
			this.gui.clientName.setBorderColor(Color.RED);
			this.gui.clientName.focus();
		}
		else if (this.gui.date.isEmpty())
		{
			showWarning(GUIEditInvoice.Literals.DATE_REQUIRED);
			this.gui.date.setBorderColor(Color.RED);
			this.gui.date.focus();
		}
		else if (this.gui.paymentMethod.isEmpty())
		{
			showWarning(GUIEditInvoice.Literals.PAYMENT_METHOD_REQUIRED);
			this.gui.paymentMethod.setBorderColor(Color.RED);
			this.gui.paymentMethod.focus();
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