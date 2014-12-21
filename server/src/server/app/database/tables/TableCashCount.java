package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.cashcount.CashCount;
import share.core.Date;

public class TableCashCount extends Table
{
	public Integer id = new Integer(0);
	public Date date = new Date();
	public Integer type_500 = new Integer(0);
	public Integer type_200 = new Integer(0);
	public Integer type_100 = new Integer(0);
	public Integer type_50 = new Integer(0);
	public Integer type_20 = new Integer(0);
	public Integer type_10 = new Integer(0);
	public Integer type_5 = new Integer(0);
	public Integer type_2 = new Integer(0);
	public Integer type_1 = new Integer(0);
	public Integer type_0_5 = new Integer(0);
	public Integer type_0_2 = new Integer(0);
	public Integer type_0_1 = new Integer(0);
	public Integer type_0_05 = new Integer(0);
	public Integer type_0_02 = new Integer(0);
	public Integer type_0_01 = new Integer(0);
	
	public TableCashCount(Connection connection)
	{
		super(connection, "CASH_COUNT");
		setTable(this);
	}
	
	public CashCount[] getCashCounts(Integer year, String month)
	{
		if ((year != 0) && (!month.isEmpty()))
		{
			addCondition("date LIKE '" + year + "-" + month + "-%'");
		}
		else if ((year != 0) && (month.isEmpty()))
		{
			addCondition("date LIKE '" + year + "-%'");
		}
		else if ((year == 0) && (!month.isEmpty()))
		{
			addCondition("date LIKE '%-" + month + "-%'");
		}
		
		int number = search("date DESC");
		
		CashCount[] result = new CashCount[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new CashCount(this.id, this.date, this.type_500, this.type_200, this.type_100, this.type_50, this.type_20, this.type_10, this.type_5, this.type_2, this.type_1, this.type_0_5, this.type_0_2, this.type_0_1, this.type_0_05, this.type_0_02, this.type_0_01);
		}
		
		return result;
	}
	
	public boolean add(CashCount cashCount)
	{
		this.id = cashCount.id;
		this.date = cashCount.date;
		this.type_500 = cashCount.type_500;
		this.type_200 = cashCount.type_200;
		this.type_100 = cashCount.type_100;
		this.type_50 = cashCount.type_50;
		this.type_20 = cashCount.type_20;
		this.type_10 = cashCount.type_10;
		this.type_5 = cashCount.type_5;
		this.type_2 = cashCount.type_2;
		this.type_1 = cashCount.type_1;
		this.type_0_5 = cashCount.type_0_5;
		this.type_0_2 = cashCount.type_0_2;
		this.type_0_1 = cashCount.type_0_1;
		this.type_0_05 = cashCount.type_0_05;
		this.type_0_02 = cashCount.type_0_02;
		this.type_0_01 = cashCount.type_0_01;
		
		return create();
	}
	
	public boolean edit(CashCount original, CashCount newCashCount)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			this.id = newCashCount.id;
			this.date = newCashCount.date;
			this.type_500 = newCashCount.type_500;
			this.type_200 = newCashCount.type_200;
			this.type_100 = newCashCount.type_100;
			this.type_50 = newCashCount.type_50;
			this.type_20 = newCashCount.type_20;
			this.type_10 = newCashCount.type_10;
			this.type_5 = newCashCount.type_5;
			this.type_2 = newCashCount.type_2;
			this.type_1 = newCashCount.type_1;
			this.type_0_5 = newCashCount.type_0_5;
			this.type_0_2 = newCashCount.type_0_2;
			this.type_0_1 = newCashCount.type_0_1;
			this.type_0_05 = newCashCount.type_0_05;
			this.type_0_02 = newCashCount.type_0_02;
			this.type_0_01 = newCashCount.type_0_01;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(CashCount cashCount)
	{
		boolean valid = false;
		
		this.id = cashCount.id;
		
		if (read())
		{
			valid = delete();
		}
		
		return valid;
	}
}