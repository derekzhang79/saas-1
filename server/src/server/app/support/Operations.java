package server.app.support;

import java.sql.Connection;
import server.app.database.tables.TableSupport;
import server.core.connection.ServerOperation;
import share.app.support.IOperations;
import share.app.support.Support;
import share.core.objects.Date;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Support[] getSupports(Date dateCreationParam, String statusParam)
	{
		TableSupport table = new TableSupport(getSystemConnection());

		return table.getSupports(dateCreationParam, statusParam, getConnection());
	}

	@Override
	public Boolean addSupport(Support support)
	{
		TableSupport table = new TableSupport(getSystemConnection());

		return table.add(support);
	}

	@Override
	public Boolean editSupport(Support original, Support newSupport)
	{
		TableSupport table = new TableSupport(getSystemConnection());

		return table.edit(original, newSupport);
	}
}