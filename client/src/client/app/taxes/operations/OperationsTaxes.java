package client.app.taxes.operations;

import share.app.taxes.IOperations;
import share.app.taxes.Tax;
import share.core.objects.Date;
import client.core.operations.Operation;

public class OperationsTaxes implements IOperations
{
	private static OperationsTaxes instance = new OperationsTaxes();

	public static OperationsTaxes call()
	{
		return OperationsTaxes.instance;
	}

	@Override
	public Tax[] getTaxes()
	{
		Operation<Tax[]> operation = new Operation<Tax[]>(IOperations.GET_TAXES);

		return operation.run();
	}

	@Override
	public Tax[] getTaxesFrom(Date from)
	{
		Operation<Tax[]> operation = new Operation<Tax[]>(IOperations.GET_TAXES_FROM);

		return operation.run(from);
	}

	@Override
	public Boolean addTax(Tax tax)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_TAX);

		return operation.run(tax);
	}

	@Override
	public Boolean editTax(Tax original, Tax newTax)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_TAX);

		return operation.run(original, newTax);
	}

	@Override
	public Boolean deleteTax(Tax tax)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_TAX);

		return operation.run(tax);
	}
}