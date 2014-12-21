package server.core.connection;

import server.core.debug.ServerError;
import share.core.connection.Parameters;
import share.core.constants.Constants;
import share.core.utils.Crypt;
import share.core.utils.Encoding;
import share.core.utils.Resource;
import share.core.utils.Serializer;

public class RequestParameters
{
	private final String ip;
	private final byte[] request;
	
	public RequestParameters(byte[] request, String ip)
	{
		this.request = request;
		this.ip = ip;
	}
	
	public Parameters getParameters()
	{
		byte[] serverKey = loadServerKey();
		String data = Crypt.decrypt(this.request, serverKey);
		
		Parameters result = (Parameters)Serializer.unserialize(data);
		result.setIP(this.ip);
		
		return result;
	}
	
	private byte[] loadServerKey()
	{
		byte[] key = new byte[0];
		
		try
		{
			key = Encoding.base64DecodeByte(Resource.load(Constants.PRIVATE_KEY_PATH));
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return key;
	}
}