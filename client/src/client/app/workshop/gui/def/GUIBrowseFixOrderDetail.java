package client.app.workshop.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseFixOrderDetail {

	public static final String PATH = Constants.GUI_BASE_PATH + "workshop/gui/xml/browse_fix_order_detail";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton edit = null;
	public ExtendedTable list = null;
	public ExtendedButton add = null;
	public ExtendedButton delete = null;

}