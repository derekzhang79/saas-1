package client.app.products.operations;

import share.app.products.IOperations;
import share.app.products.Product;
import client.core.operations.Operation;

public class OperationsProducts implements IOperations
{
	private static OperationsProducts instance = new OperationsProducts();

	public static OperationsProducts call()
	{
		return OperationsProducts.instance;
	}

	@Override
	public Product[] getProducts(Long barCode, Integer sectionID, Integer brand)
	{
		Operation<Product[]> operation = new Operation<Product[]>(IOperations.GET_PRODUCTS);

		return operation.run(barCode, sectionID, brand);
	}

	@Override
	public Product addProduct(Product product)
	{
		Operation<Product> operation = new Operation<Product>(IOperations.ADD_PRODUCT);

		return operation.run(product);
	}

	@Override
	public Boolean editProduct(Product original, Product newProduct)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_PRODUCT);

		return operation.run(original, newProduct);
	}

	@Override
	public Boolean deleteProduct(Product product)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_PRODUCT);

		return operation.run(product);
	}
}