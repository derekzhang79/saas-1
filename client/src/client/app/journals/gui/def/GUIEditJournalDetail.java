package client.app.journals.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedButton;

public class GUIEditJournalDetail {

	public static final String PATH = Constants.GUI_BASE_PATH + "journals/gui/xml/edit_journal_detail";

	public enum Literals {
		JOURNAL_DETAIL_NOT_EDITED, ASK_CLOSE_WINDOW
	}

	public ExtendedLabel labelAmount = null;
	public ExtendedInputDecimal amount = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;

}