package client.core;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import share.core.Constants.AppEnvironment;
import share.core.Constants.AppLocation;
import share.core.Environment;
import share.core.conf.Configurator;
import client.core.connection.Communication;
import client.core.gui.FontStore;
import client.core.images.ImageStore;

public class Main
{

	private static AppLocation LOCATION = null;
	private static AppEnvironment ENVIRONMENT = null;

	public static void main(String[] args)
	{
		if (args.length == 2)
		{
			Main.LOCATION = AppLocation.valueOf(args[0].toUpperCase());
			Main.ENVIRONMENT = AppEnvironment.valueOf(args[1].toUpperCase());

			Main.configure();

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
		else
		{
			System.out.println("Usage: client.jar [local|remote] [test|real]");
		}
	}

	public static AppLocation getLocation()
	{
		return Main.LOCATION;
	}

	public static AppEnvironment getEnvironment()
	{
		return Main.ENVIRONMENT;
	}

	public static void configure()
	{
		Environment.createApplicationPath();
		Communication.configure();
		FontStore.configure();

		String url = Configurator.getDesktop().shortcut.base_url + Main.ENVIRONMENT.toString().toLowerCase() + Configurator.getDesktop().shortcut.file_url;
		Environment.setApplicationShortcut(Configurator.getDesktop().shortcut.name, Configurator.getDesktop().shortcut.icone, url);

		try
		{
			UIManager.setLookAndFeel(Configurator.getDesktop().laf);
		}
		catch (Exception e)
		{
		}
	}
}