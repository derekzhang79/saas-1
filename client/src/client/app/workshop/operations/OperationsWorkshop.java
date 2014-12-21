package client.app.workshop.operations;

import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import share.app.workshop.IOperations;
import client.core.operations.Operation;

public class OperationsWorkshop implements IOperations
{
	private static OperationsWorkshop instance = new OperationsWorkshop();

	public static OperationsWorkshop call()
	{
		return OperationsWorkshop.instance;
	}

	@Override
	public FixOrder[] getFixOrders(Integer clientParam, String statusParam)
	{
		Operation<FixOrder[]> operation = new Operation<FixOrder[]>(IOperations.GET_FIX_ORDERS);

		return operation.run(clientParam, statusParam);
	}

	@Override
	public FixOrder addFixOrder(FixOrder fixOrder)
	{
		Operation<FixOrder> operation = new Operation<FixOrder>(IOperations.ADD_FIX_ORDER);

		return operation.run(fixOrder);
	}

	@Override
	public Boolean editFixOrder(FixOrder original, FixOrder newFixOrder)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_FIX_ORDER);

		return operation.run(original, newFixOrder);
	}

	@Override
	public Boolean deleteFixOrder(FixOrder fixOrder)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_FIX_ORDER);

		return operation.run(fixOrder);
	}

	@Override
	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID)
	{
		Operation<FixOrderDetail[]> operation = new Operation<FixOrderDetail[]>(IOperations.GET_FIX_ORDERS_DETAIL);

		return operation.run(fixOrderID);
	}

	@Override
	public Boolean addFixOrderDetail(FixOrderDetail fixOrderDetail)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_FIX_ORDER_DETAIL);

		return operation.run(fixOrderDetail);
	}

	@Override
	public Boolean editFixOrderDetail(FixOrderDetail original, FixOrderDetail newFixOrderDetail)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_FIX_ORDER_DETAIL);

		return operation.run(original, newFixOrderDetail);
	}

	@Override
	public Boolean deleteFixOrderDetail(FixOrderDetail fixOrderDetail)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_FIX_ORDER_DETAIL);

		return operation.run(fixOrderDetail);
	}
}