package client.app.contacts.clients.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditClient {

	public static final String PATH = Constants.GUI_BASE_PATH + "contacts/clients/gui/xml/edit_client";

	public enum Literals {
		NAME_REQUIRED, PHONE_REQUIRED, CLIENT_NOT_CREATED, CLIENT_NOT_EDITED, TITLE_ADD_CLIENT, TITLE_EDIT_CLIENT, ASK_CLOSE_WINDOW
	}

	public ExtendedInputText firstName = null;
	public ExtendedLabel labelFirstName = null;
	public ExtendedInputInt mobile = null;
	public ExtendedButton cancel = null;
	public ExtendedLabel labelIdentification = null;
	public ExtendedInputText address = null;
	public ExtendedInputText identification = null;
	public ExtendedTextArea comments = null;
	public ExtendedLabel labelComments = null;
	public ExtendedLabel labelTelephone = null;
	public ExtendedInputInt postalCode = null;
	public ExtendedLabel labelCity = null;
	public ExtendedInputText lastName = null;
	public ExtendedLabel labelMobile = null;
	public ExtendedLabel labelAddress = null;
	public ExtendedInputText email = null;
	public ExtendedLabel labelLastName = null;
	public ExtendedInputText city = null;
	public ExtendedLabel labelPostalCode = null;
	public ExtendedButton save = null;
	public ExtendedInputInt telephone = null;
	public ExtendedLabel labelEmail = null;

}