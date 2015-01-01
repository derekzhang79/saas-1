package client.core.connection;

import share.core.connection.Response;
import share.core.utils.Serializer;
import client.core.debug.Debug;
import client.core.desktop.Desktop;
import client.core.gui.messages.Message;

public class Communication<ResponseClass>
{
	private final String target;
	private final String user;
	private String pass = "";
	private final int clientID;

	private static String ticket;
	private static String sessionId;
	
	public Communication(String target, String user, int clientID)
	{
		this.target = target;
		this.user = user;
		this.clientID = clientID;
	}
	
	public static void configure(String ip, int port)
	{
		Transmission.configure(ip, port);
	}
	
	public void setPass(String value)
	{
		this.pass = value;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseClass send(Object... parameters)
	{
		Desktop.getDesktop().restartTimer();
		BackgroundWorker worker = new BackgroundWorker(this.target, this.user, this.clientID, Communication.sessionId, Communication.ticket, parameters);
		
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