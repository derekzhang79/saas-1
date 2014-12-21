package share.core.connection;

import java.io.Serializable;

import share.core.Serializer;

public class Response implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ticket = "";
	private String sessionId = "";
	
	private Object value = null;
	
	public Response(String ticket, String sessionId, Object value) {
		this.ticket = ticket;
		this.sessionId = sessionId;
		this.value = value;
	}
	
	public String getTicket() {
		return this.ticket;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public String getData() {
		return Serializer.serialize(this);
	}
}