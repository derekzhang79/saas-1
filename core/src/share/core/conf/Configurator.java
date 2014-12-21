package share.core.conf;

import share.core.Constants;
import share.core.conf.communication.ConfCommunication;
import share.core.conf.database.ConfDatabase;
import share.core.conf.desktop.ConfDesktop;

public class Configurator {
	
	public static ConfCommunication getCommunication() {
		Configuration<ConfCommunication> conf = new Configuration<ConfCommunication>();
		
		return conf.get(ConfCommunication.class, Constants.CONF_COMMUNICATION);
	}
	
	public static ConfDesktop getDesktop() {
		Configuration<ConfDesktop> conf = new Configuration<ConfDesktop>();
		
		return conf.get(ConfDesktop.class, Constants.CONF_DESKTOP);
	}
	
	public static ConfDatabase getDatabase() {
		Configuration<ConfDatabase> conf = new Configuration<ConfDatabase>();
		
		return conf.get(ConfDatabase.class, Constants.CONF_DDBB);
	}
}