package client.core.desktop;

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
import client.core.gui.fonts.FontStore;
import client.core.images.ImageStore;

public class MainClient
{
	public static void main(String[] args)
	{
		if (args.length == 2)
		{
			AppLocation location = AppLocation.valueOf(args[0].toUpperCase());
			AppEnvironment environment = AppEnvironment.valueOf(args[1].toUpperCase());

			MainClient mainClient = new MainClient();
			mainClient.start(location, environment);
		}
		else
		{
			System.out.println("Usage: client.jar [local|remote] [test|real]");
		}
	}

	private void start(AppLocation location, AppEnvironment environment)
	{
		configure(location, environment);
		startDesktop();
	}
	
	private void configure(AppLocation location, AppEnvironment environment)
	{
		Environment.createApplicationPath();
		Communication.configure(location, environment);
		FontStore.configure();

		String url = Configurator.getDesktop().shortcut.base_url + environment.toString().toLowerCase() + Configurator.getDesktop().shortcut.file_url;
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