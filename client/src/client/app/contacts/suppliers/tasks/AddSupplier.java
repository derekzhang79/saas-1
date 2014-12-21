package client.app.contacts.suppliers.tasks;

import share.app.contacts.suppliers.Supplier;
import client.app.contacts.suppliers.gui.def.GUIEditSupplier;
import client.app.contacts.suppliers.operations.OperationsSuppliers;

public class AddSupplier extends BaseSupplier {
	
	public void start() {
		setTitle(getLiteral(GUIEditSupplier.Literals.TITLE_ADD_SUPPLIER));
		this.gui.name.focus();
	}
	
	private void addSupplier() {
		if (validate()) {
			Supplier newSupplier = new Supplier(0, this.gui.name.get(), this.gui.identification.get(), this.gui.address.get(), this.gui.city.get(), this.gui.postalCode.getInt(), this.gui.telephone.getInt(), this.gui.mobile.getInt(), this.gui.email.get(), this.gui.contactPerson.get(), this.gui.comments.get(), 0);
			boolean response = OperationsSuppliers.call().addSupplier(newSupplier);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditSupplier.Literals.SUPPLIER_NOT_CREATED);
				this.gui.name.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditSupplier.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.name.isEmpty()) || (!this.gui.identification.isEmpty()) || (!this.gui.address.isEmpty()) || (!this.gui.city.isEmpty()) || (!this.gui.postalCode.isEmpty()) || (!this.gui.telephone.isEmpty()) || (!this.gui.mobile.isEmpty()) || (!this.gui.contactPerson.isEmpty()) || (!this.gui.email.isEmpty()) || (!this.gui.comments.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addSupplier();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}