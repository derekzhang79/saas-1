package client.core.gui.export;

import java.lang.reflect.Field;
import share.core.constants.Constants;
import share.core.objects.Date;
import client.core.debug.Debug;
import client.core.gui.components.ColumnType;
import client.core.gui.format.DataFormatter;
import client.core.gui.messages.Message;

public class Exporter
{
	public static String getValue(Field field, Object row, ColumnType.Type type)
	{
		String result = "";
		
		try
		{
			if (type.equals(ColumnType.Type.STRING))
			{
				result = field.get(row).toString();
			}
			else if (type.equals(ColumnType.Type.BOOLEAN))
			{
				result = Message.getBooleanString(field.getBoolean(row));
			}
			else if (type.equals(ColumnType.Type.DATE))
			{
				Date dateValue = (Date)field.get(row);
				result = dateValue.toString();
			}
			else if (type.equals(ColumnType.Type.INTEGER))
			{
				long intValue = field.getLong(row);
				result = String.valueOf(intValue);
			}
			else if (type.equals(ColumnType.Type.DECIMAL))
			{
				double doubleValue = field.getDouble(row);
				result = DataFormatter.formatDecimal(doubleValue);
			}
			else if (type.equals(ColumnType.Type.MONEY))
			{
				double doubleValue = field.getDouble(row);
				result = DataFormatter.formatDecimal(doubleValue) + " " + Constants.CURRENCY_EURO;
			}
			
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
		
		return result;
	}
}