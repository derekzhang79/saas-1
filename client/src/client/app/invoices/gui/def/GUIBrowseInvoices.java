package client.app.invoices.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;
import client.core.gui.components.ExtendedLabel;

public class GUIBrowseInvoices {

	public static final String PATH = Constants.GUI_BASE_PATH + "invoices/gui/xml/browse_invoices";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton print = null;
	public ExtendedDateChooser date = null;
	public ExtendedInputText clientName = null;
	public ExtendedButton searchClient = null;
	public ExtendedButton detail = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedLabel labelDate = null;
	public ExtendedLabel labelClient = null;
	public ExtendedButton edit = null;
	public ExtendedButton clearSearch = null;
	public ExtendedButton delete = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;

}