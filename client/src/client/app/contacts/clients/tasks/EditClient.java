package client.app.contacts.clients.tasks;

import share.app.contacts.clients.Client;
import client.app.contacts.clients.gui.def.GUIEditClient;
import client.app.contacts.clients.operations.OperationsClients;

public class EditClient extends BaseClient<Boolean>
{
	private final Client original;

	public EditClient(Client original)
	{
		this.original = original;
	}

	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditClient.Literals.TITLE_EDIT_CLIENT));

		this.gui.firstName.set(this.original.firstName);
		this.gui.lastName.set(this.original.lastName);
		this.gui.identification.set(this.original.identification);
		this.gui.address.set(this.original.address);
		this.gui.city.set(this.original.city);
		this.gui.postalCode.set(this.original.postalCode);
		this.gui.telephone.set(this.original.telephone);
		this.gui.mobile.set(this.original.mobile);
		this.gui.email.set(this.original.email);
		this.gui.comments.set(this.original.comments);

		this.gui.firstName.focus();
	}

	private void editClient()
	{
		if (validate())
		{
			Client newClient = new Client(0, this.gui.firstName.get(), this.gui.lastName.get(), this.gui.identification.get(), this.gui.address.get(), this.gui.city.get(), this.gui.postalCode.getInt(), this.gui.telephone.getInt(), this.gui.mobile.getInt(), this.gui.email.get(), this.gui.comments.get(), 0);
			boolean response = OperationsClients.call().editClient(this.original, newClient);

			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditClient.Literals.CLIENT_NOT_EDITED);
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
		return ((!this.gui.firstName.equals(this.original.firstName)) || (!this.gui.lastName.equals(this.original.lastName)) || (!this.gui.identification.equals(this.original.identification)) || (!this.gui.address.equals(this.original.address)) || (!this.gui.city.equals(this.original.city)) || (!this.gui.postalCode.equals(this.original.postalCode)) || (!this.gui.telephone.equals(this.original.telephone)) || (!this.gui.mobile.equals(this.original.mobile)) || (!this.gui.email.equals(this.original.email)) || (!this.gui.comments.equals(this.original.comments)));
	}

	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editClient();
				break;

			case CANCEL:
				closing();
				break;

			default:
				break;
		}
	}
}