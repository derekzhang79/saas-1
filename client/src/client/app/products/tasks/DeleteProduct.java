package client.app.products.tasks;

import share.app.products.Product;
import client.app.products.gui.def.GUIDeleteProduct;
import client.app.products.operations.OperationsProducts;
import client.core.gui.taks.Activity;

public class DeleteProduct extends Activity<Boolean>
{
	private final Product product;
	
	public DeleteProduct(Product product)
	{
		super(GUIDeleteProduct.PATH, Type.SINGLE);
		
		this.product = product;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteProduct.Literals.ASK_DELETE, this.product.name)))
		{
			Boolean response = OperationsProducts.call().deleteProduct(this.product);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}