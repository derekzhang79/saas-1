package server.app.contacts.suppliers;

import java.sql.Connection;
import server.app.database.tables.TableSupplier;
import server.core.connection.ServerOperation;
import share.app.contacts.suppliers.IOperations;
import share.app.contacts.suppliers.Supplier;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Supplier[] getSuppliers()
	{
		TableSupplier table = new TableSupplier(getConnection());

		return table.getSuppliers();
	}

	@Override
	public Boolean addSupplier(Supplier supplier)
	{
		TableSupplier table = new TableSupplier(getConnection());

		return table.add(supplier);
	}

	@Override
	public Boolean editSupplier(Supplier original, Supplier newSupplier)
	{
		TableSupplier table = new TableSupplier(getConnection());

		return table.edit(original, newSupplier);
	}

	@Override
	public Boolean deleteSupplier(Supplier supplier)
	{
		TableSupplier table = new TableSupplier(getConnection());

		return table.delete(supplier);
	}
}