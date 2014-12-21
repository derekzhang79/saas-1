package share.core.connection;

import java.io.Serializable;
import share.core.utils.Serializer;

public class Response implements Serializable
{
	private static final long serialVersionUID = 7623519621928853232L;

	private final String ticket;
	private final String sessionId;
	private final Object value;
	
	public Response(String ticket, String sessionId, Object value)
	{
		this.ticket = ticket;
		this.sessionId = sessionId;
		this.value = value;
	}
	
	public String getTicket()
	{
		return this.ticket;
	}
	
	public String getSessionId()
	{
		return this.sessionId;
	}
	
	public Object getValue()
	{
		return this.value;
	}
	
	public String getData()
	{
		return Serializer.serialize(this);
	}
}