package client.core.connection;

import share.core.Constants.AppEnvironment;
import share.core.Constants.AppLocation;
import share.core.Serializer;
import share.core.conf.Configurator;
import share.core.conf.communication.ConfCommunication;
import share.core.connection.Response;
import client.core.Debug;
import client.core.Desktop;
import client.core.gui.Message;

public class Communication<ResponseClass>
{
	private final String target;
	private final String user;
	private String pass = "";
	private final int clientID;

	private static String ticket;
	private static String sessionId;
	private static String dbEnvironment;
	
	public Communication(String target, String user, int clientID)
	{
		this.target = target;
		this.user = user;
		this.clientID = clientID;
	}
	
	public static void configure(AppLocation location, AppEnvironment environment)
	{
		Communication.dbEnvironment = environment.toString();
		
		ConfCommunication cofCommunication = Configurator.getCommunication();

		if (location.equals(AppLocation.LOCAL))
		{
			String ip = cofCommunication.getIp(true);
			int port = cofCommunication.getPort(true);
			
			Transmission.configure(ip, port);
		}
		else if (location.equals(AppLocation.REMOTE))
		{
			String ip = cofCommunication.getIp(false);
			int port = cofCommunication.getPort(false);
			
			Transmission.configure(ip, port);
		}
	}
	
	public void setPass(String value)
	{
		this.pass = value;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseClass send(Object... parameters)
	{
		Desktop.getDesktop().restartTimer();
		BackgroundWorker worker = new BackgroundWorker(this.target, this.user, this.clientID, Communication.sessionId, Communication.ticket, Communication.dbEnvironment, parameters);
		
		if (!this.pass.isEmpty())
		{
			worker.setPass(this.pass);
		}
		
		worker.execute();
		Message.showWaitMessage();
		String response = "";
		
		try
		{
			response = worker.get();
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
		
		Response data = (Response)Serializer.unserialize(response);
		Communication.ticket = data.getTicket();
		Communication.sessionId = data.getSessionId();
		
		return (ResponseClass)data.getValue();
	}
}