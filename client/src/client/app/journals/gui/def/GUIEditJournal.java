package client.app.journals.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedButton;

public class GUIEditJournal {

	public static final String PATH = Constants.GUI_BASE_PATH + "journals/gui/xml/edit_journal";

	public enum Literals {
		DATE_REQUIRED, JOURNAL_NOT_CREATED, JOURNAL_NOT_EDITED, TITLE_ADD_JOURNAL, TITLE_EDIT_JOURNAL, ASK_CLOSE_WINDOW
	}

	public ExtendedLabel labelDate = null;
	public ExtendedDateChooser date = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;

}