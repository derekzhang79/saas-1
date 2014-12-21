package client.app.system.gui.def;

import share.core.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedTextArea;

public class GUILogInfo {

	public static final String PATH = Constants.GUI_BASE_PATH + "system/gui/xml/log_info";

	public enum Literals {
		
	}

	public ExtendedButton accept = null;
	public ExtendedTextArea info = null;
	public ExtendedButton copy = null;

}