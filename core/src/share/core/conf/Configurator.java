package share.core.conf;

import share.core.conf.database.ConfDatabase;
import share.core.conf.desktop.ConfDesktop;
import share.core.constants.Constants;

public class Configurator
{
	public static ConfDesktop getDesktop()
	{
		Configuration<ConfDesktop> conf = new Configuration<ConfDesktop>();
		
		return conf.get(ConfDesktop.class, Constants.CONF_DESKTOP);
	}
	
	public static ConfDatabase getDatabase()
	{
		Configuration<ConfDatabase> conf = new Configuration<ConfDatabase>();
		
		return conf.get(ConfDatabase.class, Constants.CONF_DDBB);
	}
}