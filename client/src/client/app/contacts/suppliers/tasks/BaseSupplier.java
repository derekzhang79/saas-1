package client.app.contacts.suppliers.tasks;

import java.awt.Color;

import client.app.contacts.suppliers.gui.def.GUIEditSupplier;
import client.core.gui.OptionTask;

public abstract class BaseSupplier extends OptionTask<Boolean> {
	
	protected GUIEditSupplier gui = new GUIEditSupplier();
	
	public BaseSupplier() {
		super(GUIEditSupplier.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.name.isEmpty()) {
			showWarning(GUIEditSupplier.Literals.NAME_REQUIRED);
			this.gui.name.setBorderColor(Color.RED);
			this.gui.name.focus();
		} else if (this.gui.telephone.isEmpty() && this.gui.mobile.isEmpty()) {
			showWarning(GUIEditSupplier.Literals.PHONE_REQUIRED);
			this.gui.telephone.setBorderColor(Color.RED);
			this.gui.mobile.setBorderColor(Color.RED);
			this.gui.telephone.focus();
		} else {
			valid = true;
		}
		
		return valid;
	}
}