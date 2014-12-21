package client.app.cashcount.operations;

import share.app.cashcount.CashCount;
import share.app.cashcount.IOperations;
import client.core.operations.Operation;

public class OperationsCashCount implements IOperations
{
	private static OperationsCashCount instance = new OperationsCashCount();

	public static OperationsCashCount call()
	{
		return OperationsCashCount.instance;
	}

	@Override
	public CashCount[] getCashCounts(Integer year, String month)
	{
		Operation<CashCount[]> operation = new Operation<CashCount[]>(IOperations.GET_CASH_COUNTS);

		return operation.run(year, month);
	}

	@Override
	public Boolean addCashCount(CashCount cashCount)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_CASH_COUNT);

		return operation.run(cashCount);
	}

	@Override
	public Boolean editCashCount(CashCount original, CashCount newCashCount)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_CASH_COUNT);

		return operation.run(original, newCashCount);
	}

	@Override
	public Boolean deleteCashCount(CashCount cashCount)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_CASH_COUNT);

		return operation.run(cashCount);
	}
}