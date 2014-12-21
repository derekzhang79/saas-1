package client.app.system.gui.def;

import share.core.constants.Constants;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedInputPassword;
import client.core.gui.components.ExtendedImage;

public class GUILogin {

	public static final String PATH = Constants.GUI_BASE_PATH + "system/gui/xml/login";

	public enum Literals {
		USER_REQUIRED, PASS_REQUIRED, INVALID_USER, INVALID_PASS
	}

	public ExtendedButton login = null;
	public ExtendedInputText user = null;
	public ExtendedButton exit = null;
	public ExtendedLabel userLabel = null;
	public ExtendedInputPassword pass = null;
	public ExtendedImage image = null;
	public ExtendedLabel passLabel = null;

}