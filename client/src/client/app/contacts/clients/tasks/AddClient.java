package client.app.contacts.clients.tasks;

import share.app.contacts.clients.Client;
import client.app.contacts.clients.gui.def.GUIEditClient;
import client.app.contacts.clients.operations.OperationsClients;

public class AddClient extends BaseClient<Client>
{
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditClient.Literals.TITLE_ADD_CLIENT));
		this.gui.firstName.focus();
	}
	
	private void addClient()
	{
		if (validate())
		{
			Client newClient = new Client(0, this.gui.firstName.get(), this.gui.lastName.get(), this.gui.identification.get(), this.gui.address.get(), this.gui.city.get(), this.gui.postalCode.getInt(), this.gui.telephone.getInt(), this.gui.mobile.getInt(), this.gui.email.get(), this.gui.comments.get(), 0);
			Client response = OperationsClients.call().addClient(newClient);
			
			if (response != null)
			{
				close(response);
			}
			else
			{
				showWarning(GUIEditClient.Literals.CLIENT_NOT_CREATED);
				this.gui.firstName.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditClient.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.firstName.isEmpty()) || (!this.gui.lastName.isEmpty()) || (!this.gui.identification.isEmpty()) || (!this.gui.address.isEmpty()) || (!this.gui.city.isEmpty()) || (!this.gui.postalCode.isEmpty()) || (!this.gui.telephone.isEmpty()) || (!this.gui.mobile.isEmpty()) || (!this.gui.email.isEmpty()) || (!this.gui.comments.isEmpty()));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addClient();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}