package client.app.brands.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedButton;

public class GUIEditBrand {

	public static final String PATH = Constants.GUI_BASE_PATH + "brands/gui/xml/edit_brand";

	public enum Literals {
		NAME_REQUIRED, BRAND_NOT_CREATED, BRAND_NOT_EDITED, TITLE_ADD_BRAND, TITLE_EDIT_BRAND, ASK_CLOSE_WINDOW
	}

	public ExtendedLabel labelName = null;
	public ExtendedInputText name = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;

}