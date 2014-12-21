package client.app.support.operations;

import share.app.support.IOperations;
import share.app.support.Support;
import share.core.objects.Date;
import client.core.operations.Operation;

public class OperationsSupports implements IOperations
{
	private static OperationsSupports instance = new OperationsSupports();

	public static OperationsSupports call()
	{
		return OperationsSupports.instance;
	}

	@Override
	public Support[] getSupports(Date dateCreationParam, String statusParam)
	{
		Operation<Support[]> operation = new Operation<Support[]>(IOperations.GET_SUPPORTS);

		return operation.run(dateCreationParam, statusParam);
	}

	@Override
	public Boolean addSupport(Support support)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_SUPPORT);

		return operation.run(support);
	}

	@Override
	public Boolean editSupport(Support original, Support newSupport)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_SUPPORT);

		return operation.run(original, newSupport);
	}
}