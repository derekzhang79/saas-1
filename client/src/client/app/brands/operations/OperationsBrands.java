package client.app.brands.operations;

import share.app.brands.Brand;
import share.app.brands.IOperations;
import client.core.operations.Operation;

public class OperationsBrands implements IOperations
{
	private static OperationsBrands instance = new OperationsBrands();

	public static OperationsBrands call()
	{
		return OperationsBrands.instance;
	}

	@Override
	public Brand[] getBrands()
	{
		Operation<Brand[]> operation = new Operation<Brand[]>(IOperations.GET_BRANDS);

		return operation.run();
	}

	@Override
	public Brand addBrand(Brand brand)
	{
		Operation<Brand> operation = new Operation<Brand>(IOperations.ADD_BRAND);

		return operation.run(brand);
	}

	@Override
	public Boolean editBrand(Brand original, Brand newBrand)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_BRAND);

		return operation.run(original, newBrand);
	}

	@Override
	public Boolean deleteBrand(Brand brand)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_BRAND);

		return operation.run(brand);
	}
}