package client.core.gui.components;

public class ColumnType
{
	private final String name;
	private final String code;
	private final int width;
	private final Type type;

	public enum Type
	{
		STRING, INTEGER, DECIMAL, MONEY, BOOLEAN, DATE
	}

	public ColumnType(String name, String code, int width, Type type)
	{
		this.name = name;
		this.code = code;
		this.width = width;
		this.type = type;
	}

	public String getName()
	{
		return this.name;
	}

	public String getCode()
	{
		return this.code;
	}

	public int getWidth()
	{
		return this.width;
	}

	public Type getRealType()
	{
		return this.type;
	}

	public Class<?> getType()
	{
		Class<?> clazz = null;

		if ((this.type.equals(ColumnType.Type.STRING)) || (this.type.equals(ColumnType.Type.DATE)))
		{
			clazz = String.class;
		}
		else if (this.type.equals(ColumnType.Type.INTEGER))
		{
			clazz = Integer.class;
		}
		else if ((this.type.equals(ColumnType.Type.DECIMAL)) || (this.type.equals(ColumnType.Type.MONEY)))
		{
			clazz = Double.class;
		}
		else if (this.type.equals(ColumnType.Type.BOOLEAN))
		{
			clazz = Boolean.class;
		}

		return clazz;
	}
}