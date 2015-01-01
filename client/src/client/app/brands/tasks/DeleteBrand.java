package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUIDeleteBrand;
import client.app.brands.operations.OperationsBrands;
import client.core.gui.taks.Activity;

public class DeleteBrand extends Activity<Boolean>
{
	private final Brand brand;

	public DeleteBrand(Brand brand)
	{
		super(GUIDeleteBrand.PATH, Type.SINGLE);

		this.brand = brand;
	}

	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteBrand.Literals.ASK_DELETE, this.brand.name)))
		{
			Boolean response = OperationsBrands.call().deleteBrand(this.brand);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}