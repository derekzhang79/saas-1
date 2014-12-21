package client.core.connection;

import javax.swing.SwingWorker;
import share.core.connection.Parameters;
import client.core.debug.Debug;
import client.core.gui.messages.Message;

public class BackgroundWorker extends SwingWorker<String, String>
{
	private final String user;
	private String pass = "";
	private final int clientID;
	private final String ticket;
	private final String sessionId;
	private final String dbEnvironment;
	private final String target;
	private final Object[] parameters;

	public BackgroundWorker(String target, String user, int clientID, String sessionId, String ticket, String dbEnvironment, Object... parameters)
	{
		this.user = user;
		this.clientID = clientID;
		this.sessionId = sessionId;
		this.ticket = ticket;
		this.target = target;
		this.parameters = parameters;
		this.dbEnvironment = dbEnvironment;
	}

	public void setPass(String value)
	{
		this.pass = value;
	}

	@Override
	protected String doInBackground()
	{
		long init = System.currentTimeMillis();
		Parameters params = new Parameters(this.target, this.user, this.pass, this.clientID, this.sessionId, this.ticket, this.dbEnvironment, this.parameters);
		String response = Transmission.send(params);
		long time = System.currentTimeMillis() - init;

		if (!params.isLogin())
		{
			Debug.setInfo("››› " + this.target + " → " + time + " ms");
		}

		Debug.setTimeRequest(time);

		return response;
	}

	@Override
	protected void done()
	{
		Message.hideWaitMessage();
	}
}