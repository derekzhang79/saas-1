package client.app.configuration.usergroups.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseUserGroups {

	public static final String PATH = Constants.GUI_BASE_PATH + "configuration/usergroups/gui/xml/browse_user_groups";

	public enum Literals {
		ROW_NOT_SELECTED, IS_ADMINISTRATOR_GROUP, LIST_PDF
	}

	public ExtendedButton detail = null;
	public ExtendedButton edit = null;
	public ExtendedTable list = null;
	public ExtendedButton add = null;
	public ExtendedButton delete = null;

}