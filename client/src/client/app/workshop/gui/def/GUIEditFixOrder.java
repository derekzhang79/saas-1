package client.app.workshop.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditFixOrder {

	public static final String PATH = Constants.GUI_BASE_PATH + "workshop/gui/xml/edit_fix_order";

	public enum Literals {
		CLIENT_REQUIRED, STATUS_REQUIRED, START_REQUIRED, FINISH_REQUIRED, FINISH_DATE_INVALID, FIX_ORDER_NOT_CREATED, FIX_ORDER_NOT_EDITED, TITLE_ADD_FIX_ORDER, TITLE_EDIT_FIX_ORDER, ASK_CLOSE_WINDOW
	}

	public ExtendedDateChooser start = null;
	public ExtendedLabel labelStatus = null;
	public ExtendedLabel labelFinish = null;
	public ExtendedComboBox status = null;
	public ExtendedInputText clientName = null;
	public ExtendedButton searchClient = null;
	public ExtendedTextArea comments = null;
	public ExtendedLabel labelComments = null;
	public ExtendedButton save = null;
	public ExtendedLabel labelStart = null;
	public ExtendedLabel labelClient = null;
	public ExtendedDateChooser finish = null;
	public ExtendedButton cancel = null;

}