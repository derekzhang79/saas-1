package server.app.taxes;

import java.sql.Connection;
import server.app.db.tables.TableTax;
import server.core.ServerOperation;
import share.app.taxes.IOperations;
import share.app.taxes.Tax;
import share.core.Date;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Tax[] getTaxes()
	{
		TableTax table = new TableTax(getConnection());

		return table.getTaxes();
	}

	@Override
	public Tax[] getTaxesFrom(Date from)
	{
		TableTax table = new TableTax(getConnection());

		return table.getTaxesFrom(from);
	}

	@Override
	public Boolean addTax(Tax tax)
	{
		TableTax table = new TableTax(getConnection());

		return table.add(tax);
	}

	@Override
	public Boolean editTax(Tax original, Tax newTax)
	{
		TableTax table = new TableTax(getConnection());

		return table.edit(original, newTax);
	}

	@Override
	public Boolean deleteTax(Tax tax)
	{
		TableTax table = new TableTax(getConnection());

		return table.delete(tax);
	}
}