package client.app.invoices.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditInvoice
{
	public static final String PATH = Constants.GUI_BASE_PATH + "invoices/gui/xml/edit_invoice";
	
	public enum Literals
	{
		CLIENT_REQUIRED, DATE_REQUIRED, PAYMENT_METHOD_REQUIRED, INVOICE_ORDER_NOT_CREATED, INVOICE_ORDER_NOT_EDITED, TITLE_ADD_INVOICE, TITLE_EDIT_INVOICE, ASK_CLOSE_WINDOW
	}
	
	public ExtendedDateChooser date = null;
	public ExtendedInputInt number = null;
	public ExtendedInputText clientName = null;
	public ExtendedButton searchClient = null;
	public ExtendedTextArea comments = null;
	public ExtendedLabel labelComments = null;
	public ExtendedComboBox paymentMethod = null;
	public ExtendedLabel labelDate = null;
	public ExtendedButton save = null;
	public ExtendedLabel labelClient = null;
	public ExtendedLabel labelPaymentMethod = null;
	public ExtendedLabel labelNumber = null;
	public ExtendedButton cancel = null;
	public ExtendedInputDecimal discount = null;
	public ExtendedLabel labelDiscount = null;
}