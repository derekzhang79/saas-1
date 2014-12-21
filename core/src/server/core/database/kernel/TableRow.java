package server.core.database.kernel;

import java.sql.ResultSet;
import java.util.List;
import server.core.debug.ServerError;
import share.core.objects.Date;
import share.core.utils.MapTable;

public class TableRow
{
	private final MapTable<String, TableField> fields = new MapTable<String, TableField>();
	
	public TableRow(ResultSet set, ObjectField[] fields)
	{
		for (ObjectField field : fields)
		{
			String name = field.getName();
			Object value = null;
			
			try
			{
				if (field.isString())
				{
					value = set.getString(name);
				}
				else if (field.isInteger())
				{
					value = set.getInt(name);
				}
				else if (field.isLong())
				{
					value = set.getLong(name);
				}
				else if (field.isFloat())
				{
					value = set.getFloat(name);
				}
				else if (field.isDouble())
				{
					value = set.getDouble(name);
				}
				else if (field.isBoolean())
				{
					value = set.getBoolean(name);
				}
				else if (field.isDate())
				{
					try
					{
						value = new Date(set.getDate(name));
					}
					catch (Exception e)
					{
						value = new Date();
					}
				}
				else if (field.isTime())
				{
					value = set.getTime(name);
				}
				else if (field.isBinary())
				{
					byte[] original = set.getBytes(name);
					Byte[] byteArray = new Byte[original.length];
					
					for (int i = 0; i < original.length; i++)
					{
						byteArray[i] = original[i];
					}
					
					value = byteArray;
				}
				else
				{
					value = set.getObject(name);
				}
			}
			catch (Exception e)
			{
				ServerError.setError(e);
			}
			
			this.fields.put(name, new TableField(name, value));
		}
	}
	
	public TableField getField(String name)
	{
		return this.fields.get(name);
	}
	
	public TableField[] getFields(List<String> fieldNames)
	{
		TableField[] result = new TableField[fieldNames.size()];
		
		for (int i = 0; i < result.length; i++)
		{
			result[i] = getField(fieldNames.get(i));
		}
		
		return result;
	}
	
	public void updateFields(TableField[] newFields)
	{
		for (TableField field : newFields)
		{
			TableField current = getField(field.getName());
			current.setValue(field.getValue());
		}
	}
	
	public String getString(String name)
	{
		return this.fields.get(name).getString();
	}
	
	public Integer getInteger(String name)
	{
		return this.fields.get(name).getInteger();
	}
	
	public Long getLong(String name)
	{
		return this.fields.get(name).getLong();
	}
	
	public Float getFloat(String name)
	{
		return this.fields.get(name).getFloat();
	}
	
	public Double getDouble(String name)
	{
		return this.fields.get(name).getDouble();
	}
}