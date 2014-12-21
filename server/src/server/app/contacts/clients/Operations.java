package server.app.contacts.clients;

import java.sql.Connection;
import server.app.db.tables.TableClient;
import server.core.ServerOperation;
import share.app.contacts.clients.Client;
import share.app.contacts.clients.IOperations;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Client getClient(Integer clientID)
	{
		TableClient table = new TableClient(getConnection());

		return table.getClient(clientID);
	}

	@Override
	public Client[] getClients()
	{
		TableClient table = new TableClient(getConnection());

		return table.getClients();
	}

	@Override
	public Client addClient(Client client)
	{
		TableClient table = new TableClient(getConnection());

		return table.add(client);
	}

	@Override
	public Boolean editClient(Client original, Client newClient)
	{
		TableClient table = new TableClient(getConnection());

		return table.edit(original, newClient);
	}

	@Override
	public Boolean deleteClient(Client client)
	{
		TableClient table = new TableClient(getConnection());

		return table.delete(client);
	}
}