package client.app.system.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputPassword;
import client.core.gui.components.ExtendedLabel;

public class GUIBlocker
{
	public static final String PATH = Constants.GUI_BASE_PATH + "system/gui/xml/blocker";
	
	public enum Literals
	{
		PASS_REQUIRED, INVALID_PASS
	}
	
	public ExtendedButton exit = null;
	public ExtendedLabel labelPass = null;
	public ExtendedButton accept = null;
	public ExtendedInputPassword pass = null;
}