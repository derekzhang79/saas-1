package client.core.desktop;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import share.core.conf.Configurator;
import share.core.utils.Environment;
import client.core.connection.Communication;
import client.core.gui.fonts.FontStore;
import client.core.images.ImageStore;

public class MainClient
{
	public static void main(String[] args)
	{
		if (args.length == 2)
		{
			String serverIP = args[0];
			int serverPort = Integer.parseInt(args[1]);
			
			MainClient mainClient = new MainClient();
			mainClient.start(serverIP, serverPort);
		}
		else
		{
			System.out.println("Usage: client.jar SERVER_IP SERVER_PORT");
		}
	}
	
	private void start(String serverIP, int serverPort)
	{
		configure(serverIP, serverPort);
		startDesktop();
	}

	private void configure(String serverIP, int serverPort)
	{
		Environment.createApplicationPath();
		Communication.configure(serverIP, serverPort);
		FontStore.configure();
		
		String url = Configurator.getDesktop().shortcut.base_url + Configurator.getDesktop().shortcut.file_url;
		Environment.setApplicationShortcut(Configurator.getDesktop().shortcut.name, Configurator.getDesktop().shortcut.icone, url);
		
		try
		{
			UIManager.setLookAndFeel(Configurator.getDesktop().laf);
		}
		catch (Exception e)
		{
		}
	}
	
	private void startDesktop()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Desktop frame = new Desktop(Integer.parseInt(Configurator.getDesktop().bgcolor));
				frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				frame.setTitle(Configurator.getDesktop().title);
				frame.setIconImage(ImageStore.getImage(Configurator.getDesktop().icon));
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				frame.setVisible(true);
				
				frame.addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent winEvt)
					{
						Desktop.getDesktop().callOptionTask(Configurator.getDesktop().exit);
					}
				});
			}
		});
	}
}