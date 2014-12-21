package client.app.taxes.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedLabel;

public class GUIEditTax
{
	public static final String PATH = Constants.GUI_BASE_PATH + "taxes/gui/xml/edit_tax";
	
	public enum Literals
	{
		TYPE_REQUIRED, VALUE_REQUIRED, DATE_REQUIRED, TAX_NOT_CREATED, TAX_NOT_EDITED, TITLE_ADD_TAX, TITLE_EDIT_TAX, ASK_CLOSE_WINDOW
	}
	
	public ExtendedLabel labelDate = null;
	public ExtendedLabel labelValue = null;
	public ExtendedLabel labelType = null;
	public ExtendedComboBox type = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;
	public ExtendedInputDecimal value = null;
	public ExtendedDateChooser start = null;
}