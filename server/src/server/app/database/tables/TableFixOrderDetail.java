package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.workshop.FixOrderDetail;

public class TableFixOrderDetail extends Table
{
	public Integer id = new Integer(0);
	public Integer fix_order = new Integer(0);
	public Integer line = new Integer(0);
	public Integer quantity = new Integer(0);
	public String detail = new String();
	public Double amount = new Double(0);
	
	public TableFixOrderDetail(Connection connection)
	{
		super(connection, "FIX_ORDER_DETAIL");
		setTable(this);
	}
	
	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID)
	{
		this.fix_order = fixOrderID;
		
		int number = search("line");
		
		FixOrderDetail[] result = new FixOrderDetail[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			result[i] = new FixOrderDetail(this.id, this.fix_order, this.line, this.quantity, this.detail, this.amount);
		}
		
		return result;
	}
	
	public boolean add(FixOrderDetail fixOrderDetail)
	{
		this.id = fixOrderDetail.id;
		this.fix_order = fixOrderDetail.fixOrder;
		this.line = nextLine(fixOrderDetail.fixOrder);
		this.quantity = fixOrderDetail.quantity;
		this.detail = fixOrderDetail.detail;
		this.amount = fixOrderDetail.amount;
		
		return create();
	}
	
	public boolean edit(FixOrderDetail original, FixOrderDetail newFixOrderDetail)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			this.quantity = newFixOrderDetail.quantity;
			this.detail = newFixOrderDetail.detail;
			this.amount = newFixOrderDetail.amount;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(FixOrderDetail fixOrderDetail)
	{
		boolean valid = false;
		
		this.id = fixOrderDetail.id;
		
		if (read())
		{
			valid = delete();
		}
		
		return valid;
	}
	
	public boolean deleteAll(Integer fixOrderID)
	{
		this.fix_order = fixOrderID;
		
		int number = search();
		int deleted = 0;
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
			FixOrderDetail fixOrderDetail = new FixOrderDetail(this.id, this.fix_order, this.line, this.quantity, this.detail, this.amount);
			
			if (table.delete(fixOrderDetail))
			{
				deleted++;
			}
		}
		
		return (number == deleted);
	}
	
	public double getTotalAmount(int fixOrderID)
	{
		double result = 0;
		
		this.fix_order = fixOrderID;
		
		int number = search();
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			result += this.amount;
		}
		
		return result;
	}
	
	private int nextLine(int fixOrderID)
	{
		int result = 1;
		
		TableFixOrderDetail table = new TableFixOrderDetail(getConnection());
		table.fix_order = fixOrderID;
		
		int rows = table.search("line DESC");
		
		if (rows > 0)
		{
			table.select(0);
			result = table.line + 1;
		}
		
		return result;
	}
}