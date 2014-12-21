package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUIEditBrand;
import client.app.brands.operations.OperationsBrands;

public class AddBrand extends BaseBrand<Brand> {
	
	public void start() {
		setTitle(getLiteral(GUIEditBrand.Literals.TITLE_ADD_BRAND));
		this.gui.name.focus();
	}
	
	private void addBrand() {
		if (validate()) {
			Brand newBrand = new Brand(0, this.gui.name.get());
			Brand response = OperationsBrands.call().addBrand(newBrand);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditBrand.Literals.BRAND_NOT_CREATED);
				this.gui.name.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditBrand.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return (!this.gui.name.isEmpty());
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addBrand();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}