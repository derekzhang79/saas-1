package client.app.invoices.tasks;

import share.app.dictionary.Category.Categories;
import share.app.invoices.Invoice;
import client.app.invoices.gui.def.GUIEditInvoice;
import client.app.invoices.operations.OperationsInvoices;
import client.app.system.dictionary.DictionaryManager;

public class EditInvoice extends BaseInvoice<Boolean> {
	
	private Invoice original = null;
	
	public EditInvoice(Invoice original) {
		this.original = original;
	}
	
	public void start() {
		setTitle(getLiteral(GUIEditInvoice.Literals.TITLE_EDIT_INVOICE));
		
		this.clientID = this.original.id;
		
		this.gui.clientName.set(this.original.clientName);
		this.gui.discount.set(this.original.discount);
		this.gui.number.set(this.original.number);
		this.gui.date.set(this.original.date);
		this.gui.paymentMethod.setItems(DictionaryManager.get(Categories.PAYMENT_METHOD));
		this.gui.paymentMethod.set(this.original.paymentMethod);
		this.gui.comments.set(this.original.comments);
		
		this.gui.clientName.focus();
	}
	
	private void editInvoice() {
		if (validate()) {
			Invoice newInvoice = new Invoice(0, this.gui.number.getInt(), this.gui.date.get(), this.clientID, this.gui.discount.getValue(), this.gui.paymentMethod.get(), this.gui.comments.get());
			boolean response = OperationsInvoices.call().editInvoice(this.original, newInvoice);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditInvoice.Literals.INVOICE_ORDER_NOT_EDITED);
				this.gui.clientName.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditInvoice.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.clientName.equals(this.original.clientName)) || (!this.gui.number.equals(this.original.number)) || (!this.gui.date.equals(this.original.date)) || (!this.gui.discount.equals(this.original.discount)) || (!this.gui.paymentMethod.equals(this.original.paymentMethod)) || (!this.gui.comments.equals(this.original.comments)));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editInvoice();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_CLIENT:
				searchClient();
				break;
			
			default:
				break;
		}
	}
}