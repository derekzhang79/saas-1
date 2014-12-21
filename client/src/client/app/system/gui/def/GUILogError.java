package client.app.system.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTextArea;

public class GUILogError
{
	public static final String PATH = Constants.GUI_BASE_PATH + "system/gui/xml/log_error";
	
	public ExtendedTextArea error = null;
	public ExtendedButton accept = null;
	public ExtendedButton copy = null;
}