package server.core;

import share.core.Constants;
import share.core.Crypt;
import share.core.Encoding;
import share.core.Resource;
import share.core.Serializer;
import share.core.connection.Parameters;

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
	
	public static Parameters getParameters(byte[] request, String ip)
	{
		RequestParameters parameters = new RequestParameters(request, ip);
		
		return parameters.getParameters();
	}
}