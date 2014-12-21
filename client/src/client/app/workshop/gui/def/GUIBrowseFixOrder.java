package client.app.workshop.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;

public class GUIBrowseFixOrder {

	public static final String PATH = Constants.GUI_BASE_PATH + "workshop/gui/xml/browse_fix_order";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton print = null;
	public ExtendedLabel labelStatus = null;
	public ExtendedComboBox status = null;
	public ExtendedInputText clientName = null;
	public ExtendedButton searchClient = null;
	public ExtendedButton detail = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedLabel labelClient = null;
	public ExtendedButton edit = null;
	public ExtendedButton clearSearch = null;
	public ExtendedButton delete = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;

}