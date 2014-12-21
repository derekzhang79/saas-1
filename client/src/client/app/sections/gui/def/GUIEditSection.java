package client.app.sections.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;

public class GUIEditSection
{
	public static final String PATH = Constants.GUI_BASE_PATH + "sections/gui/xml/edit_section";
	
	public enum Literals
	{
		CODE_REQUIRED, NAME_REQUIRED, SECTION_NOT_CREATED, SECTION_NOT_EDITED, TITLE_ADD_SECTION, TITLE_EDIT_SECTION, ASK_CLOSE_WINDOW
	}
	
	public ExtendedLabel labelName = null;
	public ExtendedLabel labelProfit = null;
	public ExtendedInputDecimal profit = null;
	public ExtendedInputText name = null;
	public ExtendedLabel labelCode = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;
	public ExtendedInputInt code = null;
}