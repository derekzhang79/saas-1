package client.app.invoices.tasks;

import share.app.dictionary.Category.Categories;
import share.app.invoices.InvoiceDetail;
import client.app.invoices.gui.def.GUIEditInvoiceDetail;
import client.app.invoices.operations.OperationsInvoices;
import client.app.system.dictionary.DictionaryManager;

public class EditInvoiceDetail extends BaseInvoiceDetail {
	
	private InvoiceDetail original = null;
	
	public EditInvoiceDetail(InvoiceDetail original) {
		this.original = original;
	}
	
	public void start() {
		setTitle(getLiteral(GUIEditInvoiceDetail.Literals.TITLE_EDIT_INVOICE_DETAIL));
		
		this.productID = this.original.product;
		
		this.gui.productName.set(this.original.productDescription);
		this.gui.quantity.set(this.original.quantity);
		this.gui.tax.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.tax.set(this.original.tax);
		this.gui.price.set(this.original.price);
		refreshTotalPrice();
		this.gui.productName.focus();
	}
	
	private void editInvoiceDetail() {
		if (validate()) {
			InvoiceDetail newInvoiceDetail = new InvoiceDetail(0, this.original.invoice, 0, this.productID, "", this.gui.quantity.getValue(), this.gui.tax.get(), this.taxValue, this.gui.price.getValue());
			boolean response = OperationsInvoices.call().editInvoiceDetail(this.original, newInvoiceDetail);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditInvoiceDetail.Literals.INVOICE_DETAIL_NOT_EDITED);
				this.gui.productName.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditInvoiceDetail.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.productName.equals(this.original.productDescription)) || (!this.gui.quantity.equals(this.original.quantity)) || (!this.gui.price.equals(this.original.price)) || (!this.gui.tax.equals(this.original.tax)));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editInvoiceDetail();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_PRODUCT:
				searchProduct();
				break;
			
			case REFRESH:
				refreshTotalPrice();
				break;
			
			default:
				break;
		}
	}
}