package client.core.gui.reports;

import share.core.Date;
import share.core.MapTable;
import client.core.gui.format.DataFormatter;

public class ReportParameter
{
	private final MapTable<String, Object> parameters = new MapTable<String, Object>();
	
	public void add(String name, String value)
	{
		this.parameters.put(name, value);
	}
	
	public void add(String name, int value)
	{
		this.parameters.put(name, String.valueOf(value));
	}
	
	public void add(String name, double value)
	{
		this.parameters.put(name, DataFormatter.formatDecimal(value));
	}
	
	public void add(String name, Date value)
	{
		this.parameters.put(name, value.toString());
	}
	
	public void addTable(String name, Object[] rows)
	{
		this.parameters.put(name, rows);
	}
	
	public String getValue(String name)
	{
		return this.parameters.get(name).toString();
	}
	
	public Object[] getTable(String name)
	{
		return (Object[])this.parameters.get(name);
	}
}