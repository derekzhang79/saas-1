package client.app.invoices.tasks;

import share.app.dictionary.Category.Categories;
import share.app.invoices.Invoice;
import share.core.Date;
import client.app.invoices.gui.def.GUIEditInvoice;
import client.app.invoices.operations.OperationsInvoices;
import client.app.system.dictionary.DictionaryManager;

public class AddInvoice extends BaseInvoice<Invoice> {
	
	public void start() {
		setTitle(getLiteral(GUIEditInvoice.Literals.TITLE_ADD_INVOICE));
		this.gui.paymentMethod.setItems(DictionaryManager.get(Categories.PAYMENT_METHOD));
		this.gui.paymentMethod.selectNone();
		this.gui.date.setToday();
		this.gui.clientName.focus();
	}
	
	private void addInvoice() {
		if (validate()) {
			Invoice newInvoice = new Invoice(0, this.gui.number.getInt(), this.gui.date.get(), this.clientID, this.gui.discount.getValue(), this.gui.paymentMethod.get(), this.gui.comments.get());
			Invoice response = OperationsInvoices.call().addInvoice(newInvoice);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditInvoice.Literals.INVOICE_ORDER_NOT_CREATED);
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
		return ((!this.gui.clientName.isEmpty()) || (!this.gui.number.isEmpty()) || (!this.gui.date.equals(Date.getTodayDate())) || (!this.gui.discount.isEmpty()) || (!this.gui.paymentMethod.isEmpty()) || (!this.gui.comments.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addInvoice();
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