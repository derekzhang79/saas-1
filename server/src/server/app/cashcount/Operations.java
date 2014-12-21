package server.app.cashcount;

import java.sql.Connection;
import server.app.database.tables.TableCashCount;
import server.core.connection.ServerOperation;
import share.app.cashcount.CashCount;
import share.app.cashcount.IOperations;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public CashCount[] getCashCounts(Integer year, String month)
	{
		TableCashCount table = new TableCashCount(getConnection());

		return table.getCashCounts(year, month);
	}

	@Override
	public Boolean addCashCount(CashCount cashCount)
	{
		TableCashCount table = new TableCashCount(getConnection());

		return table.add(cashCount);
	}

	@Override
	public Boolean editCashCount(CashCount original, CashCount newCashCount)
	{
		TableCashCount table = new TableCashCount(getConnection());

		return table.edit(original, newCashCount);
	}

	@Override
	public Boolean deleteCashCount(CashCount cashCount)
	{
		TableCashCount table = new TableCashCount(getConnection());

		return table.delete(cashCount);
	}
}