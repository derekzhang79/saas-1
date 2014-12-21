package client.app.contacts.clients.tasks;

import share.app.contacts.clients.Client;
import client.app.contacts.clients.gui.def.GUIDeleteClient;
import client.app.contacts.clients.operations.OperationsClients;
import client.core.gui.taks.OptionTask;

public class DeleteClient extends OptionTask<Boolean>
{
	private final Client client;

	public DeleteClient(Client client)
	{
		super(GUIDeleteClient.PATH, TaskType.SINGLE);

		this.client = client;
	}

	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteClient.Literals.ASK_DELETE, this.client.name)))
		{
			Boolean response = OperationsClients.call().deleteClient(this.client);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}