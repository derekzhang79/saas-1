package client.app.journals.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseJournalDetail {

	public static final String PATH = Constants.GUI_BASE_PATH + "journals/gui/xml/browse_journal_detail";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedTable list = null;

}