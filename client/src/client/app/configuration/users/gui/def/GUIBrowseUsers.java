package client.app.configuration.users.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseUsers {

	public static final String PATH = Constants.GUI_BASE_PATH + "configuration/users/gui/xml/browse_users";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton edit = null;
	public ExtendedTable list = null;
	public ExtendedButton add = null;
	public ExtendedButton delete = null;

}