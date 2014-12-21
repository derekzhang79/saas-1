package client.app.workshop.tasks;

import java.awt.Color;

import client.app.workshop.gui.def.GUIEditFixOrderDetail;
import client.core.gui.OptionTask;

public abstract class BaseFixOrderDetail extends OptionTask<Boolean> {
	
	protected GUIEditFixOrderDetail gui = new GUIEditFixOrderDetail();
	
	public BaseFixOrderDetail() {
		super(GUIEditFixOrderDetail.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.detail.isEmpty()) {
			showWarning(GUIEditFixOrderDetail.Literals.DETAIL_REQUIRED);
			this.gui.detail.setBorderColor(Color.RED);
			this.gui.detail.focus();
		} else if (this.gui.quantity.isEmpty()) {
			showWarning(GUIEditFixOrderDetail.Literals.QUANTITY_REQUIRED);
			this.gui.quantity.setBorderColor(Color.RED);
			this.gui.quantity.focus();
		} else if (this.gui.amount.isEmpty()) {
			showWarning(GUIEditFixOrderDetail.Literals.AMOUNT_REQUIRED);
			this.gui.amount.setBorderColor(Color.RED);
			this.gui.amount.focus();
		} else {
			valid = true;
		}
		
		return valid;
	}
}