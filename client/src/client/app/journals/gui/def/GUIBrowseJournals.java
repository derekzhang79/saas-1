package client.app.journals.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedButton;

public class GUIBrowseJournals {

	public static final String PATH = Constants.GUI_BASE_PATH + "journals/gui/xml/browse_journals";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedLabel labelYear = null;
	public ExtendedLabel labelTotalProfitTitle = null;
	public ExtendedLabel labelMonth = null;
	public ExtendedLabel labelTotalProfit = null;
	public ExtendedLabel labelTotalSaleTitle = null;
	public ExtendedComboBox month = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedLabel labelTotalSale = null;
	public ExtendedInputInt year = null;
	public ExtendedButton view = null;
	public ExtendedButton edit = null;
	public ExtendedButton delete = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;

}