package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.brands.Brand;

public class TableBrand extends Table
{
	public Integer id = new Integer(0);
	public String name = new String();

	public TableBrand(Connection connection)
	{
		super(connection, "BRAND");
		setTable(this);
	}

	public Brand[] getBrands()
	{
		int number = search("id");

		Brand[] result = new Brand[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Brand(this.id, this.name);
		}

		return result;
	}

	public Brand add(Brand brand)
	{
		Brand result = null;

		this.name = brand.name;

		if (create())
		{
			result = new Brand(getLastId(), this.name);
		}

		return result;
	}

	public boolean edit(Brand original, Brand newBrand)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.name = newBrand.name;

			valid = update();
		}

		return valid;
	}

	public boolean delete(Brand brand)
	{
		boolean valid = false;

		this.id = brand.id;

		if (read())
		{
			valid = delete();
		}

		return valid;
	}

	public String getName(int brandID)
	{
		String result = "";

		this.id = brandID;

		if (read())
		{
			result = this.name;
		}

		return result;
	}
}