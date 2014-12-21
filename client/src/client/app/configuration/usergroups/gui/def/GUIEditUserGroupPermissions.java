package client.app.configuration.usergroups.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedTable;

public class GUIEditUserGroupPermissions
{
	public static final String PATH = Constants.GUI_BASE_PATH + "configuration/usergroups/gui/xml/edit_user_group_permissions";
	
	public enum Literals
	{
		ROW_NOT_SELECTED, PREMISSION_NOT_ADDED, PREMISSION_NOT_DELETED
	}
	
	public ExtendedTable list_in = null;
	public ExtendedLabel labelOut = null;
	public ExtendedTable list_out = null;
	public ExtendedLabel labelIn = null;
}