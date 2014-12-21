package server.app.init;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.Connection;
import server.core.CommunicationManager;
import server.core.RequestParameters;
import server.core.ServerError;
import server.core.ServerOperation;
import share.core.Crypt;
import share.core.connection.Parameters;
import share.core.connection.Response;
import share.core.resources.ResourceUtils;

public class ClientProcessor implements Runnable
{
	private final Socket socket;

	public ClientProcessor(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try
		{
			inputStream = this.socket.getInputStream();
			outputStream = this.socket.getOutputStream();
			
			String ip = this.socket.getInetAddress().getHostAddress();

			int read = -1;
			
			byte[] bytes = new byte[1024 * 1024 * 1]; // 1 MB
			
			while ((read = inputStream.read(bytes)) != -1)
			{
				byte[] request = new byte[read];
				System.arraycopy(bytes, 0, request, 0, read);

				byte[] response = new byte[0];
				
				try
				{
					response = processRequest(request, ip);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				outputStream.write(response);
				outputStream.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ResourceUtils.close(inputStream);
			ResourceUtils.close(outputStream);
			ResourceUtils.close(this.socket);
		}
	}

	private byte[] processRequest(byte[] request, String ip)
	{
		RequestParameters requestParameters = new RequestParameters(request, ip);
		Parameters parameters = requestParameters.getParameters();

		Object result = null;
		
		CommunicationManager manager = new CommunicationManager(parameters.getDBEnvironment());
		
		if (parameters.isLogin())
		{
			result = manager.userLogin(parameters);
		}
		else if (manager.isValid(parameters))
		{
			result = process(parameters, manager.getApplicationConnection(), manager.getSystemConnection());
		}
		
		byte[] response = respond(result, manager.getNewTicket(), manager.getSessionId(), parameters.getKey());
		
		manager.closeConnections();

		return response;
	}
	
	private byte[] respond(Object result, String ticket, String sessionId, byte[] key)
	{
		Response data = new Response(ticket, sessionId, result);
		
		return Crypt.encrypt(data.getData(), key);
	}
	
	private Object process(Parameters parameters, Connection appConnection, Connection sysConnection)
	{
		Object result = null;
		
		try
		{
			Class<?> clazz = Class.forName(parameters.getClazz());
			
			Constructor<?> cons = clazz.getConstructor(Connection.class, Connection.class);
			ServerOperation operation = (ServerOperation)cons.newInstance(appConnection, sysConnection);
			
			Method method = clazz.getMethod(parameters.getMethod(), parameters.getParameterTypes());
			result = method.invoke(operation, parameters.getParameters());
			
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return result;
	}
}