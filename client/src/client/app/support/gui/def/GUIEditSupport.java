package client.app.support.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditSupport
{
	public static final String PATH = Constants.GUI_BASE_PATH + "support/gui/xml/edit_support";
	
	public enum Literals
	{
		NAME_REQUIRED, MODULE_REQUIRED, DESCRIPTION_REQUIRED, SUPPORT_NOT_CREATED, SUPPORT_NOT_EDITED, TITLE_ADD_SUPPORT, TITLE_EDIT_SUPPORT, ASK_CLOSE_WINDOW
	}
	
	public ExtendedInputText name = null;
	public ExtendedLabel labelStatus = null;
	public ExtendedComboBox module = null;
	public ExtendedLabel labelDescription = null;
	public ExtendedComboBox status = null;
	public ExtendedLabel labelName = null;
	public ExtendedButton save = null;
	public ExtendedLabel labelModule = null;
	public ExtendedTextArea description = null;
	public ExtendedButton cancel = null;
}