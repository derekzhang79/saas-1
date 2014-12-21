package client.app.cashcount.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedButton;

public class GUIBrowseCashCount {

	public static final String PATH = Constants.GUI_BASE_PATH + "cashcount/gui/xml/browse_cash_count";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedLabel labelYear = null;
	public ExtendedLabel labelMonth = null;
	public ExtendedComboBox month = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedInputInt year = null;
	public ExtendedButton edit = null;
	public ExtendedButton delete = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;

}