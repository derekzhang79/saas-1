package client.app.invoices.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputText;

public class GUIEditInvoiceDetail {

	public static final String PATH = Constants.GUI_BASE_PATH + "invoices/gui/xml/edit_invoice_detail";

	public enum Literals {
		PRODUCT_REQUIRED, QUANTITY_REQUIRED, TAX_REQUIRED, PRICE_REQUIRED, INVOICE_DETAIL_NOT_CREATED, INVOICE_DETAIL_NOT_EDITED, TITLE_ADD_INVOICE_DETAIL, TITLE_EDIT_INVOICE_DETAIL, ASK_CLOSE_WINDOW
	}

	public ExtendedLabel labelPrice = null;
	public ExtendedComboBox tax = null;
	public ExtendedInputDecimal quantity = null;
	public ExtendedLabel labelQuantity = null;
	public ExtendedLabel labelProduct = null;
	public ExtendedInputDecimal price = null;
	public ExtendedButton searchProduct = null;
	public ExtendedButton save = null;
	public ExtendedLabel labelTotalPrice = null;
	public ExtendedLabel labelTax = null;
	public ExtendedInputText productName = null;
	public ExtendedLabel totalPrice = null;
	public ExtendedButton cancel = null;

}