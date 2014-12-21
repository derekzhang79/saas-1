package client.app.contacts.suppliers.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditSupplier {

	public static final String PATH = Constants.GUI_BASE_PATH + "contacts/suppliers/gui/xml/edit_supplier";

	public enum Literals {
		NAME_REQUIRED, PHONE_REQUIRED, SUPPLIER_NOT_CREATED, SUPPLIER_NOT_EDITED, TITLE_ADD_SUPPLIER, TITLE_EDIT_SUPPLIER, ASK_CLOSE_WINDOW
	}

	public ExtendedInputInt mobile = null;
	public ExtendedButton cancel = null;
	public ExtendedLabel labelIdentification = null;
	public ExtendedInputText address = null;
	public ExtendedInputText identification = null;
	public ExtendedTextArea comments = null;
	public ExtendedLabel labelComments = null;
	public ExtendedLabel labelTelephone = null;
	public ExtendedInputInt postalCode = null;
	public ExtendedLabel labelContactPerson = null;
	public ExtendedLabel labelCity = null;
	public ExtendedLabel labelMobile = null;
	public ExtendedLabel labelAddress = null;
	public ExtendedInputText email = null;
	public ExtendedInputText city = null;
	public ExtendedLabel labelName = null;
	public ExtendedLabel labelPostalCode = null;
	public ExtendedButton save = null;
	public ExtendedInputText contactPerson = null;
	public ExtendedInputInt telephone = null;
	public ExtendedLabel labelEmail = null;
	public ExtendedInputText name = null;

}