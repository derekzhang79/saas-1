package client.app.configuration.users.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputPassword;

public class GUIAddUser {

	public static final String PATH = Constants.GUI_BASE_PATH + "configuration/users/gui/xml/add_user";

	public enum Literals {
		NAME_REQUIRED, PASSWORD_REQUIRED, GROUP_REQUIRED, USER_NOT_CREATED, ASK_CLOSE_WINDOW
	}

	public ExtendedButton searchGroup = null;
	public ExtendedInputText name = null;
	public ExtendedLabel labelGroup = null;
	public ExtendedLabel labelName = null;
	public ExtendedButton save = null;
	public ExtendedInputPassword password = null;
	public ExtendedLabel labelPassword = null;
	public ExtendedButton cancel = null;
	public ExtendedInputText groupName = null;

}