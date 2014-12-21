package client.app.taxes.tasks;

import share.app.dictionary.Category.Categories;
import share.app.dictionary.Category.TAX;
import share.app.taxes.Tax;
import share.core.objects.Date;
import client.app.system.dictionary.DictionaryManager;
import client.app.taxes.gui.def.GUIEditTax;
import client.app.taxes.operations.OperationsTaxes;

public class AddTax extends BaseTax {
	
	public void start() {
		setTitle(getLiteral(GUIEditTax.Literals.TITLE_ADD_TAX));
		this.gui.type.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.type.set(TAX.GENERAL);
		this.gui.start.setToday();
		this.gui.type.focus();
	}
	
	private void addTax() {
		if (validate()) {
			Tax newTax = new Tax(0, this.gui.type.get(), "", this.gui.value.getValue(), this.gui.start.get());
			boolean response = OperationsTaxes.call().addTax(newTax);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditTax.Literals.TAX_NOT_CREATED);
				this.gui.type.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditTax.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.value.isEmpty()) || (!this.gui.start.equals(Date.getTodayDate())));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addTax();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}