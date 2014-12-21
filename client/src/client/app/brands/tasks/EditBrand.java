package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUIEditBrand;
import client.app.brands.operations.OperationsBrands;

public class EditBrand extends BaseBrand<Boolean> {
	
	private Brand original = null;
	
	public EditBrand(Brand original) {
		this.original = original;
	}
	
	public void start() {
		setTitle(getLiteral(GUIEditBrand.Literals.TITLE_EDIT_BRAND));
		this.gui.name.set(this.original.name);
		this.gui.name.focus();
	}
	
	private void editBrand() {
		if (validate()) {
			Brand newBrand = new Brand(0, this.gui.name.get());
			boolean response = OperationsBrands.call().editBrand(this.original, newBrand);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditBrand.Literals.BRAND_NOT_EDITED);
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
		return (!this.gui.name.equals(this.original.name));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editBrand();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}