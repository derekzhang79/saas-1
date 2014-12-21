package share.app.contacts.clients;

public interface IOperations
{
	public static final String GET_CLIENT = "server.app.contacts.clients.Operations:getClient";
	public static final String GET_CLIENTS = "server.app.contacts.clients.Operations:getClients";
	public static final String ADD_CLIENT = "server.app.contacts.clients.Operations:addClient";
	public static final String EDIT_CLIENT = "server.app.contacts.clients.Operations:editClient";
	public static final String DELETE_CLIENT = "server.app.contacts.clients.Operations:deleteClient";

	public Client getClient(Integer clientID);

	public Client[] getClients();

	public Client addClient(Client client);

	public Boolean editClient(Client original, Client newClient);

	public Boolean deleteClient(Client client);
}