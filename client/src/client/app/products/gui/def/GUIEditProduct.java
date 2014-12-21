package client.app.products.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedTextArea;

public class GUIEditProduct {

	public static final String PATH = Constants.GUI_BASE_PATH + "products/gui/xml/edit_product";

	public enum Literals {
		NAME_REQUIRED, SECTION_REQUIRED, BAR_CODE_REQUIRED, COST_PRICE_REQUIRED, SALE_PRICE_REQUIRED, TAX_REQUIRED, BRAND_REQUIRED, MODEL_REQUIRED, MEASURING_UNIT_REQUIRED, PRODUCT_NOT_CREATED, PRODUCT_NOT_EDITED, TITLE_ADD_PRODUCT, TITLE_EDIT_PRODUCT, ASK_CLOSE_WINDOW
	}

	public ExtendedButton searchSection = null;
	public ExtendedInputDecimal salePrice = null;
	public ExtendedInputInt barCode = null;
	public ExtendedButton cancel = null;
	public ExtendedInputInt length = null;
	public ExtendedInputText model = null;
	public ExtendedLabel labelDescription = null;
	public ExtendedInputText brandName = null;
	public ExtendedInputText sectionName = null;
	public ExtendedInputInt quantity = null;
	public ExtendedLabel labelMeasuringUnit = null;
	public ExtendedLabel labelCostPrice = null;
	public ExtendedLabel labelQuantity = null;
	public ExtendedLabel labelModel = null;
	public ExtendedLabel labelBarCode = null;
	public ExtendedComboBox tax = null;
	public ExtendedLabel labelSalePrice = null;
	public ExtendedLabel labelBrand = null;
	public ExtendedLabel labelLength = null;
	public ExtendedInputText color = null;
	public ExtendedTextArea description = null;
	public ExtendedComboBox measuringUnit = null;
	public ExtendedLabel labelSection = null;
	public ExtendedLabel labelName = null;
	public ExtendedButton save = null;
	public ExtendedInputDecimal costPrice = null;
	public ExtendedLabel labelColor = null;
	public ExtendedLabel labelTax = null;
	public ExtendedButton searchBrand = null;
	public ExtendedInputText name = null;

}