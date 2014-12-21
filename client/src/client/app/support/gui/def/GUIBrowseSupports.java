package client.app.support.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;
import client.core.gui.components.ExtendedButton;

public class GUIBrowseSupports {

	public static final String PATH = Constants.GUI_BASE_PATH + "support/gui/xml/browse_supports";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedDateChooser dateCreation = null;
	public ExtendedLabel labelStatus = null;
	public ExtendedComboBox status = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedLabel labelDate = null;
	public ExtendedButton edit = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;

}