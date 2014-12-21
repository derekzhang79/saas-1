package client.app.invoices.tasks;

import java.awt.Color;
import share.app.products.Product;
import share.core.constants.Constants;
import client.app.invoices.gui.def.GUIEditInvoiceDetail;
import client.app.products.tasks.SearchProduct;
import client.core.gui.format.DataFormatter;
import client.core.gui.taks.OptionTask;

public abstract class BaseInvoiceDetail extends OptionTask<Boolean> {
	
	protected int productID = 0;
	protected double taxValue = 0;
	
	protected GUIEditInvoiceDetail gui = new GUIEditInvoiceDetail();
	
	public BaseInvoiceDetail() {
		super(GUIEditInvoiceDetail.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.productName.isEmpty()) {
			showWarning(GUIEditInvoiceDetail.Literals.PRODUCT_REQUIRED);
			this.gui.productName.setBorderColor(Color.RED);
			this.gui.productName.focus();
		} else if (this.gui.quantity.isEmpty()) {
			showWarning(GUIEditInvoiceDetail.Literals.QUANTITY_REQUIRED);
			this.gui.quantity.setBorderColor(Color.RED);
			this.gui.quantity.focus();
		} else if (this.gui.tax.isEmpty()) {
			showWarning(GUIEditInvoiceDetail.Literals.TAX_REQUIRED);
			this.gui.tax.setBorderColor(Color.RED);
			this.gui.tax.focus();
		} else if (this.gui.price.isEmpty()) {
			showWarning(GUIEditInvoiceDetail.Literals.PRICE_REQUIRED);
			this.gui.price.setBorderColor(Color.RED);
			this.gui.price.focus();
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	protected void refreshTotalPrice() {
		double totalPrice = this.gui.quantity.getValue() * this.gui.price.getValue();
		totalPrice = totalPrice + (totalPrice * (this.taxValue / 100));
		this.gui.totalPrice.set(DataFormatter.formatDecimal(totalPrice) + " " + Constants.CURRENCY_EURO);
	}
	
	protected void searchProduct() {
		SearchProduct task = new SearchProduct();
		Product product = task.run();
		
		if (product != null) {
			this.productID = product.id;
			this.taxValue = product.taxValue;
			this.gui.productName.set(product.name);
			this.gui.tax.set(product.tax);
			this.gui.price.set(product.salePrice);
			refreshTotalPrice();
		}
		
		this.gui.productName.focus();
	}
}