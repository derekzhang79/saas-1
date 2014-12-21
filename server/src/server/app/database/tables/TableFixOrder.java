package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import server.core.database.tables.TableDictionary;
import share.app.dictionary.Category.Categories;
import share.app.workshop.FixOrder;
import share.core.Date;

public class TableFixOrder extends Table
{
	public Integer id = new Integer(0);
	public Integer client = new Integer(0);
	public String status = new String();
	public Date start = new Date();
	public Date finish = new Date();
	public String comments = new String();
	
	public TableFixOrder(Connection connection)
	{
		super(connection, "FIX_ORDER");
		setTable(this);
	}
	
	public FixOrder[] getFixOrders(Integer clientParam, String statusParam)
	{
		if (clientParam != 0)
		{
			this.client = clientParam;
		}
		
		if (!statusParam.isEmpty())
		{
			this.status = statusParam;
		}
		
		int number = search("id");
		
		FixOrder[] result = new FixOrder[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			result[i] = new FixOrder(this.id, this.client, getClientName(this.client), this.status, getStatusDescription(this.status), this.start, this.finish, this.comments, getTotalAmount(this.id));
		}
		
		return result;
	}
	
	public FixOrder add(FixOrder fixOrder)
	{
		FixOrder result = null;
		
		this.client = fixOrder.client;
		this.status = fixOrder.status;
		this.start = fixOrder.start;
		this.finish = fixOrder.finish;
		this.comments = fixOrder.comments;
		
		if (create())
		{
			int newID = getLastId();
			result = new FixOrder(newID, this.client, getClientName(this.client), this.status, getStatusDescription(this.status), this.start, this.finish, this.comments, getTotalAmount(newID));
		}
		
		return result;
	}
	
	public boolean edit(FixOrder original, FixOrder newFixOrder)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			this.client = newFixOrder.client;
			this.status = newFixOrder.status;
			this.start = newFixOrder.start;
			this.finish = newFixOrder.finish;
			this.comments = newFixOrder.comments;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(FixOrder fixOrder)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = fixOrder.id;
		
		if (read())
		{
			
			TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
			
			if (table.deleteAll(fixOrder.id))
			{
				if (delete())
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
		
		return valid;
	}
	
	private String getClientName(int clientID)
	{
		TableClient table = new TableClient(getConnection());
		
		return table.getName(clientID);
	}
	
	private String getStatusDescription(String code)
	{
		TableDictionary table = new TableDictionary(getConnection());
		
		return table.getDescription(Categories.FIX_ORDER_STATUS.toString(), code);
	}
	
	private double getTotalAmount(int fixOrderID)
	{
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		
		return table.getTotalAmount(fixOrderID);
	}
}