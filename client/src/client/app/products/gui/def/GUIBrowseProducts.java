package client.app.products.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedGroupBox;

public class GUIBrowseProducts {

	public static final String PATH = Constants.GUI_BASE_PATH + "products/gui/xml/browse_products";

	public enum Literals {
		ROW_NOT_SELECTED, LIST_PDF
	}

	public ExtendedButton clearSearchSection = null;
	public ExtendedLabel labelSection = null;
	public ExtendedInputText sectionName = null;
	public ExtendedInputInt barCode = null;
	public ExtendedButton searchBrand = null;
	public ExtendedButton searchSection = null;
	public ExtendedTable list = null;
	public ExtendedGroupBox groupBox = null;
	public ExtendedLabel labelBrand = null;
	public ExtendedButton edit = null;
	public ExtendedButton clearSearchBrand = null;
	public ExtendedLabel labelBarCode = null;
	public ExtendedButton delete = null;
	public ExtendedButton add = null;
	public ExtendedButton search = null;
	public ExtendedInputText brandName = null;

}