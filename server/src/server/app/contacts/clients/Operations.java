package server.app.contacts.clients;

import java.sql.Connection;

import server.app.db.tables.TableClient;
import server.core.ServerOperation;
import share.app.contacts.clients.Client;
import share.app.contacts.clients.IOperations;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public Client getClient(Integer clientID) {
		TableClient table = new TableClient(getConnection());
		
		return table.getClient(clientID);
	}
	
	public Client[] getClients() {
		TableClient table = new TableClient(getConnection());
		
		return table.getClients();
	}
	
	public Client addClient(Client client) {
		TableClient table = new TableClient(getConnection());
		
		return table.add(client);
	}
	
	public Boolean editClient(Client original, Client newClient) {
		TableClient table = new TableClient(getConnection());
		
		return table.edit(original, newClient);
	}
	
	public Boolean deleteClient(Client client) {
		TableClient table = new TableClient(getConnection());
		
		return table.delete(client);
	}
}