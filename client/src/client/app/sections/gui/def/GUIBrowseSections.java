package client.app.sections.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTable;

public class GUIBrowseSections
{
	public static final String PATH = Constants.GUI_BASE_PATH + "sections/gui/xml/browse_sections";
	
	public enum Literals
	{
		ROW_NOT_SELECTED, LIST_PDF
	}
	
	public ExtendedButton edit = null;
	public ExtendedTable list = null;
	public ExtendedButton add = null;
	public ExtendedButton delete = null;
}