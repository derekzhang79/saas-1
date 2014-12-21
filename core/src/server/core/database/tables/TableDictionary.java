package server.core.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;

public class TableDictionary extends Table
{
	public String category = new String();
	public String code = new String();
	public String value = new String();

	public TableDictionary(Connection connection)
	{
		super(connection, "DICTIONARY");
		setTable(this);
	}

	public String getDescription(String categoryName, String codeName)
	{
		String result = "";

		this.category = categoryName;
		this.code = codeName;

		if (read())
		{
			result = this.value;
		}

		return result;
	}
}