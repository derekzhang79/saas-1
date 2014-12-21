package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.contacts.clients.Client;

public class TableClient extends Table
{
	public Integer id = new Integer(0);
	public Integer contact = new Integer(0);
	
	public TableClient(Connection connection)
	{
		super(connection, "CLIENT");
		setTable(this);
	}
	
	public Client getClient(Integer clientID)
	{
		Client result = null;
		
		this.id = clientID;
		
		if (read())
		{
			TableContact table = new TableContact(getConnection());
			
			if (table.getContact(this.contact))
			{
				result = new Client(this.id, table.first_name, table.last_name, table.identification, table.address, table.city, table.postal_code, table.telephone, table.mobile, table.email, table.comments, table.id);
			}
		}
		
		return result;
	}
	
	public Client[] getClients()
	{
		int number = search("id");
		
		Client[] result = new Client[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			TableContact table = new TableContact(getConnection());
			
			if (table.getContact(this.contact))
			{
				result[i] = new Client(this.id, table.first_name, table.last_name, table.identification, table.address, table.city, table.postal_code, table.telephone, table.mobile, table.email, table.comments, table.id);
			}
		}
		
		return result;
	}
	
	public Client add(Client client)
	{
		Client result = null;
		
		beginTransaction();
		
		TableContact table = new TableContact(getConnection());
		
		if (table.add(client.firstName, client.lastName, client.identification, client.address, client.city, client.postalCode, client.telephone, client.mobile, client.email, "", client.comments))
		{
			
			this.contact = table.id;
			
			if (create())
			{
				if (commit())
				{
					result = new Client(getLastId(), table.first_name, table.last_name, table.identification, table.address, table.city, table.postal_code, table.telephone, table.mobile, table.email, table.comments, table.id);
				}
			}
			else
			{
				rollback();
			}
			
		}
		else
		{
			rollback();
		}
		
		return result;
	}
	
	public boolean edit(Client original, Client newClient)
	{
		TableContact table = new TableContact(getConnection());
		
		return table.edit(original.contactID, newClient.firstName, newClient.lastName, newClient.identification, newClient.address, newClient.city, newClient.postalCode, newClient.telephone, newClient.mobile, newClient.email, "", newClient.comments);
	}
	
	public boolean delete(Client client)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = client.id;
		
		if (read())
		{
			if (delete())
			{
				TableContact table = new TableContact(getConnection());
				
				if (table.delete(client.contactID))
				{
					valid = commit();
				}
				else
				{
					rollback();
				}
			}
			else
			{
				rollback();
			}
		}
		else
		{
			rollback();
		}
		
		return valid;
	}
	
	public String getName(int client)
	{
		String result = "";
		
		this.id = client;
		
		if (read())
		{
			result = getContactName(this.contact);
		}
		
		return result;
	}
	
	private String getContactName(int contactID)
	{
		TableContact table = new TableContact(getConnection());
		
		return table.getName(contactID);
	}
}