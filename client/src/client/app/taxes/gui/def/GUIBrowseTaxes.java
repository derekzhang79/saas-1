package client.app.taxes.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseTaxes {

	public static final String PATH = Constants.GUI_BASE_PATH + "taxes/gui/xml/browse_taxes";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton edit = null;
	public ExtendedTable list = null;
	public ExtendedButton add = null;
	public ExtendedButton delete = null;

}