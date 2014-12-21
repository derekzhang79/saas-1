package share.app.contacts.clients;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.contacts.clients.Operations:";
	public static final String GET_CLIENT = IOperations.BASE_OPERATIONS + "getClient";
	public static final String GET_CLIENTS = IOperations.BASE_OPERATIONS + "getClients";
	public static final String ADD_CLIENT = IOperations.BASE_OPERATIONS + "addClient";
	public static final String EDIT_CLIENT = IOperations.BASE_OPERATIONS + "editClient";
	public static final String DELETE_CLIENT = IOperations.BASE_OPERATIONS + "deleteClient";
	
	public Client getClient(Integer clientID);
	
	public Client[] getClients();
	
	public Client addClient(Client client);
	
	public Boolean editClient(Client original, Client newClient);
	
	public Boolean deleteClient(Client client);
}