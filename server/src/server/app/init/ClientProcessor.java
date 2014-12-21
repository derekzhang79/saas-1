package server.app.init;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.Connection;
import server.core.connection.CommunicationManager;
import server.core.connection.RequestParameters;
import server.core.connection.ServerOperation;
import server.core.debug.ServerError;
import share.core.connection.Parameters;
import share.core.connection.Response;
import share.core.resources.ResourceUtils;
import share.core.utils.Crypt;

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
			
			byte[] buffer = new byte[1024 * 1024 * 10]; // 10 MB buffer
			
			while ((read = inputStream.read(buffer)) != -1)
			{
				byte[] request = new byte[read];
				System.arraycopy(buffer, 0, request, 0, read);

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
			result = processOperation(parameters, manager.getApplicationConnection(), manager.getSystemConnection());
		}
		
		byte[] response = getResponse(result, manager.getNewTicket(), manager.getSessionId(), parameters.getKey());
		
		manager.closeConnections();

		return response;
	}
	
	private byte[] getResponse(Object result, String ticket, String sessionId, byte[] key)
	{
		Response data = new Response(ticket, sessionId, result);
		
		return Crypt.encrypt(data.getData(), key);
	}
	
	private Object processOperation(Parameters parameters, Connection appConnection, Connection sysConnection)
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