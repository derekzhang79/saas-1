package client.core.connection;

import javax.swing.SwingWorker;

import share.core.connection.Parameters;
import client.core.Debug;
import client.core.Main;
import client.core.TimeManager;
import client.core.gui.Message;

public class BackgroundWorker extends SwingWorker<String, String> {
	
	private String user = "";
	private String pass = "";
	private int clientID = 0;
	private String ticket = "";
	private String sessionId = "";
	
	private String target = "";
	private Object[] parameters = null;
	
	public BackgroundWorker(String target, String user, int clientID, String sessionId, String ticket, final Object... parameters) {
		this.user = user;
		this.clientID = clientID;
		this.sessionId = sessionId;
		this.ticket = ticket;
		
		this.target = target;
		this.parameters = parameters;
	}
	
	public void setPass(String value) {
		this.pass = value;
	}
	
	protected String doInBackground() {
		long init = TimeManager.getMilliseconds();
		Parameters params = new Parameters(this.target, this.user, this.pass, this.clientID, this.sessionId, this.ticket, Main.getEnvironment().toString(), this.parameters);
		String response = Transmission.send(params);
		long time = TimeManager.getMilliseconds() - init;
		
		if (!params.isLogin()) {
			Debug.setInfo("››› " + this.target + " → " + time + " ms");
		}
		
		Debug.setTimeRequest(time);
		
		return response;
	}
	
	protected void done() {
		Message.hideWaitMessage();
	}
}