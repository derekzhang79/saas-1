package client.app.workshop.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputInt;

public class GUIEditFixOrderDetail {

	public static final String PATH = Constants.GUI_BASE_PATH + "workshop/gui/xml/edit_fix_order_detail";

	public enum Literals {
		DETAIL_REQUIRED, QUANTITY_REQUIRED, AMOUNT_REQUIRED, FIX_ORDER_DETAIL_NOT_CREATED, FIX_ORDER_DETAIL_NOT_EDITED, TITLE_ADD_FIX_ORDER_DETAIL, TITLE_EDIT_FIX_ORDER_DETAIL, ASK_CLOSE_WINDOW
	}

	public ExtendedLabel labelAmount = null;
	public ExtendedLabel labelDetail = null;
	public ExtendedInputDecimal amount = null;
	public ExtendedInputText detail = null;
	public ExtendedButton cancel = null;
	public ExtendedButton save = null;
	public ExtendedLabel labelQuantity = null;
	public ExtendedInputInt quantity = null;

}