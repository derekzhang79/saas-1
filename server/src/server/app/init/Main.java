package server.app.init;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import share.core.resources.ResourceUtils;

public class Main
{
	private final ExecutorService clientPool = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			Integer port = Integer.parseInt(args[0]);

			Main main = new Main();
			main.start(port);
		}
		else
		{
			System.err.println("Usage: server.jar [port]");
		}
	}
	
	private void start(int port)
	{
		ServerSocket serverSocket = null;
		
		try
		{
			serverSocket = new ServerSocket(port);

			System.out.println("LISTENING: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);

			while (true)
			{
				Socket clientSocket = serverSocket.accept();

				try
				{
					this.clientPool.submit(new ClientProcessor(clientSocket));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			ResourceUtils.close(serverSocket);
		}
	}
}