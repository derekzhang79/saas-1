package server.app.init;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import share.core.resources.ResourceUtils;

public class Main
{
	private final int port;
	private final ExecutorService clientPool;

	public Main(int port)
	{
		this.port = port;
		this.clientPool = Executors.newFixedThreadPool(10);
	}

	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			Integer port = Integer.parseInt(args[0]);

			Main main = new Main(port);
			main.start();
		}
		else
		{
			System.err.println("Usage: server.jar PORT");
		}
	}
	
	private void start()
	{
		ServerSocket serverSocket = null;
		
		try
		{
			serverSocket = new ServerSocket(this.port);

			System.out.println(InetAddress.getLocalHost().getHostAddress() + ":" + this.port);

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