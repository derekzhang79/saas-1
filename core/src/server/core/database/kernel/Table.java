package server.core.database.kernel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import server.core.debug.ServerError;
import share.core.utils.MapTable;

public class Table
{
	private String database = "";
	private String name = "";
	
	private Connection connection = null;
	private TableRow[] data = new TableRow[0];
	private MapTable<String, String> conditions = new MapTable<String, String>();
	private final List<String> extraConditions = new ArrayList<String>();
	private final List<String> primary = new ArrayList<String>();
	private final MapTable<String, Object> oldValues = new MapTable<String, Object>();
	
	private ObjectField[] fields = null;
	
	private int currentRow = 0;
	private int totalRows = 0;
	private int lastID = 0;
	private long queryTime = 0;
	private String lastQuery = "";
	
	public Table(Connection connection, String table)
	{
		this.connection = connection;
		this.name = table;
		
		try
		{
			this.database = connection.getCatalog();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
	}
	
	private String getTableName()
	{
		return this.database + "." + this.name;
	}
	
	protected void setTable(Object table)
	{
		setPrimaryKey();
		setFields(table);
	}
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	private void setPrimaryKey()
	{
		PreparedStatement statement = null;
		ResultSet result = null;

		try
		{
			String query = "SELECT INDEX_NAME,COLUMN_NAME FROM INFORMATION_SCHEMA.STATISTICS WHERE table_schema = '" + this.database + "' AND table_name = '" + this.name + "'";
			statement = createStatement(query);
			result = statement.executeQuery();
			
			while (result.next())
			{
				if (result.getString("INDEX_NAME").equals("PRIMARY"))
				{
					this.primary.add(result.getString("COLUMN_NAME"));
				}
			}
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if (result != null)
			{
				try
				{
					result.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setFields(Object table)
	{
		Field[] fieldList = table.getClass().getFields();
		this.fields = new ObjectField[fieldList.length];
		
		for (int i = 0; i < this.fields.length; i++)
		{
			this.fields[i] = new ObjectField(fieldList[i], table);
		}
		
		setOldValues();
		clearConditions();
	}
	
	private void setOldValues()
	{
		for (ObjectField field : this.fields)
		{
			this.oldValues.put(field.getName(), field.getObject());
		}
	}
	
	private void clearConditions()
	{
		this.conditions = new MapTable<String, String>();
	}
	
	private TableField[] getFields(boolean modified)
	{
		List<TableField> list = new ArrayList<TableField>();
		
		for (ObjectField field : this.fields)
		{
			TableField current = new TableField(field.getName(), field.getObject());
			current.setCondition(this.conditions.get(field.getName()));
			current.setModified(field.getObject() != this.oldValues.get(field.getName()));
			
			if (!modified || (modified && current.isModified()))
			{
				list.add(current);
			}
		}
		
		TableField[] result = new TableField[list.size()];
		list.toArray(result);
		
		return result;
	}
	
	private TableField[] getFields()
	{
		return getFields(false);
	}
	
	private boolean isPrimaryField(String field)
	{
		return this.primary.contains(field);
	}
	
	private TableField[] getPrimaryFields()
	{
		List<TableField> list = new ArrayList<TableField>();
		TableField[] rowFields = getFields();
		
		for (TableField field : rowFields)
		{
			if (isPrimaryField(field.getName()))
			{
				list.add(field);
			}
		}
		
		TableField[] result = new TableField[list.size()];
		list.toArray(result);
		
		return result;
	}
	
	private int fillStatement(TableField[] rowFields, PreparedStatement statement, int start)
	{
		int index = start;
		
		try
		{
			for (TableField field : rowFields)
			{
				if (field.isString())
				{
					statement.setString(index, field.getString());
				}
				else if (field.isInteger())
				{
					statement.setInt(index, field.getInteger());
				}
				else if (field.isLong())
				{
					statement.setLong(index, field.getLong());
				}
				else if (field.isFloat())
				{
					statement.setFloat(index, field.getFloat());
				}
				else if (field.isDouble())
				{
					statement.setDouble(index, field.getDouble());
				}
				else if (field.isBoolean())
				{
					statement.setBoolean(index, field.getBoolean());
				}
				else if (field.isDate())
				{
					statement.setDate(index, field.getDate().getSQLDate());
				}
				else if (field.isTime())
				{
					statement.setTime(index, field.getTime());
				}
				else if (field.isBinary())
				{
					statement.setBytes(index, field.getBinaryPrimitive());
				}
				
				index++;
			}
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return index;
	}
	
	private int fillStatement(TableField[] rowFields, PreparedStatement statement)
	{
		return fillStatement(rowFields, statement, 1);
	}
	
	private long initQueryTime()
	{
		return System.currentTimeMillis();
	}
	
	private void finishQueryTime(long initTime)
	{
		this.queryTime = initQueryTime() - initTime;
	}
	
	private PreparedStatement createStatement(String query)
	{
		PreparedStatement statement = null;
		
		try
		{
			statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return statement;
	}
	
	private void moveFields()
	{
		TableRow row = this.data[this.currentRow];
		
		for (ObjectField field : this.fields)
		{
			String fieldName = field.getName();
			TableField current = row.getField(fieldName);
			
			if (field.isString())
			{
				field.set(current.getString());
			}
			else if (field.isInteger())
			{
				field.set(current.getInteger());
			}
			else if (field.isLong())
			{
				field.set(current.getLong());
			}
			else if (field.isFloat())
			{
				field.set(current.getFloat());
			}
			else if (field.isDouble())
			{
				field.set(current.getDouble());
			}
			else if (field.isBoolean())
			{
				field.set(current.getBoolean());
			}
			else if (field.isDate())
			{
				field.set(current.getDate());
			}
			else if (field.isTime())
			{
				field.set(current.getTime());
			}
			else if (field.isBinary())
			{
				field.set(current.getBinary());
			}
		}
		
		setOldValues();
		clearConditions();
	}
	
	private void setLastQuery(PreparedStatement statement)
	{
		String result = "";
		String[] split = statement.toString().split(": ");
		
		for (int i = 1; i < split.length; i++)
		{
			if (!result.isEmpty())
			{
				result += ": ";
			}
			
			result += split[i];
		}
		
		this.lastQuery = result;
	}
	
	public boolean beginTransaction()
	{
		boolean valid = false;
		
		try
		{
			this.connection.setAutoCommit(false);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
	
	public boolean rollback()
	{
		boolean valid = false;
		
		try
		{
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
	
	public boolean commit()
	{
		boolean valid = false;
		
		try
		{
			this.connection.commit();
			this.connection.setAutoCommit(true);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
	
	public Time getTime(int hours, int minutes, int seconds)
	{
		return Time.valueOf(hours + ":" + minutes + ":" + seconds);
	}
	
	public long getQueryTime()
	{
		return this.queryTime;
	}
	
	public String getLastQuery()
	{
		return this.lastQuery;
	}
	
	public int getLastId()
	{
		return this.lastID;
	}
	
	public int getNumberOfRows()
	{
		return this.totalRows;
	}
	
	public int getCurrentRow()
	{
		return this.currentRow;
	}
	
	public boolean next()
	{
		this.currentRow++;
		
		boolean valid = validRow();
		
		if (valid)
		{
			select(this.currentRow);
		}
		
		return valid;
	}
	
	public boolean validRow()
	{
		return (this.currentRow < this.totalRows);
	}
	
	public void select(int index)
	{
		this.currentRow = index;
		
		if (validRow())
		{
			moveFields();
		}
	}
	
	public void setCondition(String name, String condition)
	{
		this.conditions.put(name, condition);
	}
	
	public void addCondition(String value)
	{
		this.extraConditions.add(value);
	}
	
	public int getTableRows()
	{
		int rows = 0;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try
		{
			statement = createStatement("SELECT COUNT(*) FROM " + getTableName());
			result = statement.executeQuery();
			setLastQuery(statement);
			result.absolute(1);
			rows = result.getInt(1);
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if (result != null)
			{
				try
				{
					result.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return rows;
	}
	
	public void execute(String query)
	{
		long initTime = initQueryTime();
		PreparedStatement statement = null;

		try
		{
			statement = createStatement(query);
			statement.execute();
			setLastQuery(statement);
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		finishQueryTime(initTime);
	}
	
	public boolean create()
	{
		boolean valid = false;
		
		long initTime = initQueryTime();
		TableField[] rowFields = getFields();
		
		String columns = "";
		String values = "";
		
		for (TableField field : rowFields)
		{
			if (!columns.isEmpty())
			{
				columns += ", ";
				values += ", ";
			}
			
			columns += field.getName();
			values += "?";
		}
		
		String query = "INSERT INTO " + getTableName() + " (" + columns + ") VALUES (" + values + ")";
		PreparedStatement statement = createStatement(query);
		fillStatement(rowFields, statement);
		
		try
		{
			statement.execute();
			setLastQuery(statement);
			
			valid = true;
			ResultSet set = statement.getGeneratedKeys();
			
			while (set.next())
			{
				this.lastID = set.getInt(1);
			}
			
			set.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		try
		{
			statement.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		if (valid)
		{
			this.currentRow = 0;
			this.totalRows = 1;
		}
		else
		{
			this.currentRow = -1;
			this.totalRows = 0;
		}
		
		finishQueryTime(initTime);
		
		return valid;
	}
	
	public boolean read()
	{
		boolean valid = false;
		long initTime = initQueryTime();
		
		String fieldsQuery = "";
		TableField[] rowFields = getPrimaryFields();
		
		for (TableField field : rowFields)
		{
			if (!fieldsQuery.isEmpty())
			{
				fieldsQuery += " AND ";
			}
			
			fieldsQuery += "(" + field.getName() + Condition.EQUALS + "?)";
		}
		
		String query = "SELECT * FROM " + getTableName() + " WHERE (" + fieldsQuery + ") LIMIT 1";
		PreparedStatement statement = createStatement(query);
		fillStatement(rowFields, statement);
		
		try
		{
			ResultSet set = statement.executeQuery();
			setLastQuery(statement);
			
			if (set.absolute(1))
			{
				valid = true;
				this.data = new TableRow[1];
				this.data[0] = new TableRow(set, this.fields);
			}
			
			set.close();
			
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		try
		{
			statement.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		if (valid)
		{
			this.currentRow = 0;
			this.totalRows = 1;
			select(this.currentRow);
		}
		else
		{
			this.currentRow = -1;
			this.totalRows = 0;
		}
		
		finishQueryTime(initTime);
		
		return valid;
	}
	
	public int search()
	{
		return search("", 0, 0);
	}
	
	public int search(String order)
	{
		return search(order, 0, 0);
	}
	
	public int search(String order, int limit)
	{
		return search(order, limit, 0);
	}
	
	public int search(String order, int limit, int from)
	{
		int result = 0;
		long initTime = initQueryTime();
		
		String realLimit = "";
		String realOrder = "";
		
		if (limit > 0)
		{
			realLimit = " LIMIT " + limit;
		}
		
		if (from > 0)
		{
			realLimit = " LIMIT " + from + ", " + limit;
		}
		
		if (!order.equals(""))
		{
			realOrder = " ORDER BY " + order + " ";
		}
		
		String fieldsQuery = "";
		TableField[] rowFields = getFields(true);
		
		for (TableField field : rowFields)
		{
			if (!fieldsQuery.isEmpty())
			{
				fieldsQuery += " AND ";
			}
			
			if (field.getCondition().equals(Condition.LIKE))
			{
				fieldsQuery += "(" + field.getName() + " LIKE CONCAT('%', ?, '%'))";
			}
			else
			{
				fieldsQuery += "(" + field.getName() + field.getCondition() + "?)";
			}
		}
		
		for (String condition : this.extraConditions)
		{
			if (!fieldsQuery.isEmpty())
			{
				fieldsQuery += " AND ";
			}
			
			fieldsQuery += "(" + condition + ")";
		}
		
		String whereCondition = "";
		
		if (!fieldsQuery.isEmpty())
		{
			whereCondition = "WHERE (" + fieldsQuery + ")";
		}
		
		String query = "SELECT * FROM " + getTableName() + " " + whereCondition + " " + realOrder + " " + realLimit;
		PreparedStatement statement = createStatement(query);
		fillStatement(rowFields, statement);
		
		try
		{
			ResultSet set = statement.executeQuery();
			setLastQuery(statement);
			
			set.last();
			result = set.getRow();
			set.first();
			
			this.data = new TableRow[result];
			
			for (int i = 0; i < result; i++)
			{
				set.absolute(i + 1);
				this.data[i] = new TableRow(set, this.fields);
			}
			
			set.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		try
		{
			statement.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		if (result > 0)
		{
			this.currentRow = 0;
			this.totalRows = result;
			select(this.currentRow);
		}
		else
		{
			this.currentRow = -1;
			this.totalRows = 0;
		}
		
		finishQueryTime(initTime);
		
		return result;
	}
	
	public boolean update()
	{
		boolean valid = false;
		long initTime = initQueryTime();
		
		TableField[] rowFields = getFields(true);
		TableField[] primaryFields = getPrimaryFields();
		
		String fieldsSet = "";
		String fieldsWhere = "";
		
		for (TableField field : rowFields)
		{
			if (!fieldsSet.isEmpty())
			{
				fieldsSet += ", ";
			}
			
			fieldsSet += field.getName() + Condition.EQUALS + "?";
		}
		
		for (TableField field : primaryFields)
		{
			if (!fieldsWhere.isEmpty())
			{
				fieldsWhere += " AND ";
			}
			
			fieldsWhere += "(" + field.getName() + Condition.EQUALS + "?)";
		}
		
		String query = "UPDATE " + getTableName() + " SET " + fieldsSet + " WHERE (" + fieldsWhere + ")";
		PreparedStatement statement = createStatement(query);
		
		TableRow current = this.data[this.currentRow];
		int last = fillStatement(rowFields, statement);
		fillStatement(current.getFields(this.primary), statement, last);
		
		try
		{
			statement.execute();
			setLastQuery(statement);
			
			valid = true;
			current.updateFields(rowFields);
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		try
		{
			statement.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		finishQueryTime(initTime);
		
		return valid;
	}
	
	public boolean delete()
	{
		boolean valid = false;
		long initTime = initQueryTime();
		
		TableField[] primaryFields = getPrimaryFields();
		
		String primaryColumns = "";
		
		for (TableField field : primaryFields)
		{
			if (!primaryColumns.isEmpty())
			{
				primaryColumns += " AND ";
			}
			
			primaryColumns += field.getName() + Condition.EQUALS + "?";
		}
		
		String query = "DELETE FROM " + getTableName() + " WHERE (" + primaryColumns + ") LIMIT 1";
		PreparedStatement statement = createStatement(query);
		
		TableRow current = this.data[this.currentRow];
		fillStatement(current.getFields(this.primary), statement);
		
		try
		{
			statement.execute();
			setLastQuery(statement);
			
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		try
		{
			statement.close();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		finishQueryTime(initTime);
		
		return valid;
	}
}