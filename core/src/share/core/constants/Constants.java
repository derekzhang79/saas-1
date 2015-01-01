package share.core.constants;

public class Constants
{
	private Constants()
	{
	}

	public enum AppDatabase
	{
		SYSTEM, APPLICATION
	}
	
	public static final String CONF_DDBB = "/server/app/database/conf/ddbb";
	public static final String CONF_DESKTOP = "/client/app/system/conf/desktop";
	public static final String CONF_TOOLBAR = "/client/app/system/conf/toolbar";
	public static final String CONF_MENUBAR = "/client/app/system/conf/menubar";
	
	public static final String BASE_OPTION_TASK = "client.app.";
	public static final String SYSTEM_BLOCKER = "system.tasks.Blocker";
	
	public static final String IMAGE_PATH = "/client/app/system/media/";
	public static final String DESKTOP_TOOLBAR_PATH = "toolbar/";
	public static final String TABLE_TOOLBAR_PATH = "table/";
	public static final String GUI_BASE_PATH = "/client/app/";
	public static final String REPORT_BASE_PATH = "/client/app/";
	public static final String FONT_PATH = "/client/app/system/media/fonts/";
	
	public static final String PUBLIC_KEY_PATH = "/client/app/system/key/public.key";
	public static final String PRIVATE_KEY_PATH = "/server/app/key/private.key";
	
	public static final String APPLICATION_FOLDER = "/.store/";
	public static final String LAST_USER_FILE = "/last_user.dat";
	public static final String ICON_CREATED_FILE = "/icon_created.dat";
	public static final String APP_ICON_FILE = "/app_icon.ico";
	
	public static final String GUI_EXTENSION = ".uil";
	public static final String PRL_EXTENSION = ".prl";
	public static final String XML_EXTENSION = ".xml";
	public static final String PDF_EXTENSION = ".pdf";
	public static final String FONT_EXTENSION = ".ttf";
	public static final String EXCEL_EXTENSION = ".xls";
	
	public static final String CURRENCY_EURO = "€";
}