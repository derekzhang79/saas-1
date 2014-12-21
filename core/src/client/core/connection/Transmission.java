package client.core.connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import share.core.Constants;
import share.core.Constants.AppLocation;
import share.core.Crypt;
import share.core.Encoding;
import share.core.Resource;
import share.core.conf.Configurator;
import share.core.connection.Parameters;
import share.core.resources.ResourceUtils;
import client.core.Debug;
import client.core.Main;
import client.core.gui.Message;

public class Transmission
{
	
	private static String SERVER = "";
	
	public static void configure()
	{
		AppLocation location = Main.getLocation();
		
		if (location.equals(AppLocation.LOCAL))
		{
			Transmission.SERVER = Configurator.getCommunication().local;
		}
		else if (location.equals(AppLocation.REMOTE))
		{
			Transmission.SERVER = Configurator.getCommunication().remote;
		}
		
		if (location.equals(AppLocation.REMOTE))
		{
			Transmission.SERVER += "_" + Main.getEnvironment().toString().toLowerCase();
		}
		
		Transmission.SERVER += "/";
		
		Debug.setInfo("CONFIGURED HOST: " + Transmission.SERVER);
	}
	
	public static String send(Parameters data)
	{
		String result = "";
		
		try
		{
			KeyPair keys = Crypt.createKeys();
			PrivateKey privateKey = keys.getPrivate();
			PublicKey publicKey = keys.getPublic();
			
			data.setKey(publicKey.getEncoded());
			
			byte[] serverKey = Encoding.base64DecodeByte(Resource.load(Constants.PUBLIC_KEY_PATH));
			
			byte[] parameters = Crypt.encrypt(data.getData(), serverKey);

			// -----------------
			
			// TODO: CONFIGURE
			Socket socket = new Socket("127.0.0.1", 7777);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			// -----------------

			outputStream.write(parameters);
			outputStream.flush();
			
			// -----------------

			byte[] response = ResourceUtils.readInputStream(inputStream);
			result = Crypt.decrypt(response, privateKey.getEncoded());

			// ----------------
			
			inputStream.close();
			outputStream.close();
			socket.close();
		}
		catch (Exception e)
		{
			Message.communicationError();
			Debug.setError(e);
		}
		
		return result;
	}
}