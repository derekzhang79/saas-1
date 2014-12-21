package client.app.configuration.usergroups.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedCheckBox;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;

public class GUIEditUserGroup
{
	public static final String PATH = Constants.GUI_BASE_PATH + "configuration/usergroups/gui/xml/edit_user_group";
	
	public enum Literals
	{
		NAME_REQUIRED, USER_GROUP_NOT_CREATED, USER_GROUP_NOT_EDITED, TITLE_ADD_USER_GROUP, TITLE_EDIT_USER_GROUP, ASK_CLOSE_WINDOW
	}
	
	public ExtendedLabel labelName = null;
	public ExtendedInputText name = null;
	public ExtendedLabel labelAdministrator = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;
	public ExtendedCheckBox administrator = null;
}