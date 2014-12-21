package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUIDeleteBrand;
import client.app.brands.operations.OperationsBrands;
import client.core.gui.taks.OptionTask;

public class DeleteBrand extends OptionTask<Boolean> {
	
	private Brand brand = null;
	
	public DeleteBrand(Brand brand) {
		super(GUIDeleteBrand.PATH, TaskType.SINGLE);
		
		this.brand = brand;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteBrand.Literals.ASK_DELETE, this.brand.name))) {
			
			Boolean response = OperationsBrands.call().deleteBrand(this.brand);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}