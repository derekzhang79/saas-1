package server.app.brands;

import java.sql.Connection;
import server.app.database.tables.TableBrand;
import server.core.connection.ServerOperation;
import share.app.brands.Brand;
import share.app.brands.IOperations;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Brand[] getBrands()
	{
		TableBrand table = new TableBrand(getConnection());

		return table.getBrands();
	}

	@Override
	public Brand addBrand(Brand brand)
	{
		TableBrand table = new TableBrand(getConnection());

		return table.add(brand);
	}

	@Override
	public Boolean editBrand(Brand original, Brand newBrand)
	{
		TableBrand table = new TableBrand(getConnection());

		return table.edit(original, newBrand);
	}

	@Override
	public Boolean deleteBrand(Brand brand)
	{
		TableBrand table = new TableBrand(getConnection());

		return table.delete(brand);
	}
}