package client.core.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultDesktopManager;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.jdom2.Element;
import share.core.conf.Configurator;
import share.core.constants.Constants;
import share.core.utils.Environment;
import share.core.utils.MapTable;
import share.core.xml.XMLUtils;
import client.core.debug.Debug;
import client.core.gui.block.BlockTimer;
import client.core.gui.fonts.FontStore;
import client.core.gui.taks.OptionTask;
import client.core.gui.window.ExtendedWindow;
import client.core.images.ImageStore;
import client.core.profile.Profile;

public class Desktop extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 3024264473305582800L;
	
	private static Desktop self = null;
	
	private final MapTable<String, OptionTask<?>> singleTasks = new MapTable<String, OptionTask<?>>();
	private final List<OptionTask<?>> tasksList = new ArrayList<OptionTask<?>>();
	
	private BlockTimer blockTimer = null;
	private Boolean appBlocked = false;
	
	private static final String SEPARATOR = "separator";
	private static final String ITEM = "item";
	
	private static final String ATTRIBUTE_TITLE = "title";
	private static final String ATTRIBUTE_ICON = "icon";
	private static final String ATTRIBUTE_TASK = "task";
	private static final String ATTRIBUTE_DESCRIPTION = "description";
	private static final String ATTRIBUTE_PERMISSION = "permission";
	
	private static final String VALUE_TRUE = "true";
	
	public static final int TOOL_BAR_HEIGHT = 60;
	
	private JDesktopPane desktop = null;
	private final JMenuBar menuBar = new JMenuBar();
	
	public Desktop(int color)
	{
		Desktop.self = this;
		
		this.desktop = new JDesktopPane();
		this.desktop.setBackground(new Color(color, color, color));
		setContentPane(this.desktop);
		this.desktop.setDesktopManager(new DesktopManager());
		this.desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		
		this.blockTimer = new BlockTimer(Integer.parseInt(Configurator.getDesktop().blocker_time));
		callOptionTask(Configurator.getDesktop().login);
	}
	
	public void restartTimer()
	{
		if (Desktop.isDesktopSet())
		{
			synchronized (this.appBlocked)
			{
				if (!this.appBlocked)
				{
					Desktop.getDesktop().blockTimer.reset();
				}
			}
		}
	}
	
	public void appBlocked(boolean value)
	{
		synchronized (this.appBlocked)
		{
			this.appBlocked = value;
			
			if (value)
			{
				Desktop.getDesktop().blockTimer.stopTask();
			}
			else
			{
				restartTimer();
			}
		}
	}
	
	public int getDesktopHeight()
	{
		return this.desktop.getHeight();
	}
	
	public int getDesktopWidth()
	{
		return this.desktop.getWidth();
	}
	
	public void enableMenu(boolean enable)
	{
		for (int i = 0; i < this.menuBar.getMenuCount(); i++)
		{
			this.menuBar.getMenu(i).setEnabled(enable);
		}
	}
	
	public JInternalFrame[] getAllWindows()
	{
		return this.desktop.getAllFrames();
	}
	
	public void startApplication()
	{
		createMenuBar();
		createToolBar();
		restartTimer();
	}
	
	private boolean isMenuPermission(Element element)
	{
		return ((element.getAttributeValue(Desktop.ATTRIBUTE_PERMISSION) != null) && element.getAttributeValue(Desktop.ATTRIBUTE_PERMISSION).equals(Desktop.VALUE_TRUE));
	}
	
	private boolean hasSubItemPermission(Element menu)
	{
		boolean valid = false;
		
		List<Element> submenu = menu.getChildren();
		
		for (Element item : submenu)
		{
			if (Profile.hasPermission(Constants.BASE_OPTION_TASK + item.getAttributeValue(Desktop.ATTRIBUTE_TASK)))
			{
				valid = true;
				break;
			}
		}
		
		return valid;
	}
	
	private void createMenuBar()
	{
		Element root = XMLUtils.readFromResource(Constants.CONF_MENUBAR + Constants.XML_EXTENSION).getRootElement();
		List<Element> list = root.getChildren();
		
		for (Element menu : list)
		{
			if (isMenuPermission(menu) || hasSubItemPermission(menu))
			{
				String title = " " + menu.getAttributeValue(Desktop.ATTRIBUTE_TITLE);
				JMenu newMenu = new JMenu(title);
				newMenu.setFont(FontStore.getDefaultFont());
				newMenu.setPreferredSize(new Dimension(newMenu.getFontMetrics(newMenu.getFont()).stringWidth(title) + 15, 25));
				
				List<Element> submenu = menu.getChildren();
				
				boolean lastSeparator = false;
				
				for (Element item : submenu)
				{
					if (isMenuPermission(menu) || Profile.hasPermission(Constants.BASE_OPTION_TASK + item.getAttributeValue(Desktop.ATTRIBUTE_TASK)))
					{
						String type = item.getName();
						
						if (type.equals(Desktop.ITEM))
						{
							newMenu.add(menuItem(item.getAttributeValue(Desktop.ATTRIBUTE_TITLE), item.getAttributeValue(Desktop.ATTRIBUTE_ICON), item.getAttributeValue(Desktop.ATTRIBUTE_TASK)));
							lastSeparator = false;
						}
						else if (type.equals(Desktop.SEPARATOR) && (!lastSeparator))
						{
							newMenu.addSeparator();
							lastSeparator = true;
						}
					}
				}
				
				this.menuBar.add(newMenu);
			}
		}
		
		setJMenuBar(this.menuBar);
	}
	
	private JMenuItem menuItem(String label, String icon, String task)
	{
		JMenuItem menuItem = new JMenuItem(" " + label);
		Icon image = ImageStore.getIcon(icon);
		menuItem.setIcon(image);
		menuItem.setActionCommand(task);
		menuItem.addActionListener(this);
		menuItem.setFont(FontStore.getDefaultFont());
		menuItem.setPreferredSize(new Dimension((int)menuItem.getPreferredSize().getWidth() + 5, 25));
		
		return menuItem;
	}
	
	private void createToolBar()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBorderPainted(true);
		toolBar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolBar.setBounds(-1, -1, screenSize.width + 1, Desktop.TOOL_BAR_HEIGHT);
		
		Element root = XMLUtils.readFromResource(Constants.CONF_TOOLBAR + Constants.XML_EXTENSION).getRootElement();
		List<Element> list = root.getChildren();
		
		boolean lastSeparator = true;
		
		for (Element element : list)
		{
			String type = element.getName();
			
			if (type.equals(Desktop.ITEM))
			{
				String taskPath = Constants.BASE_OPTION_TASK + element.getAttributeValue(Desktop.ATTRIBUTE_TASK);
				
				if (Profile.hasPermission(taskPath))
				{
					JButton button = buttonItem(element.getAttributeValue(Desktop.ATTRIBUTE_TITLE), element.getAttributeValue(Desktop.ATTRIBUTE_DESCRIPTION), Constants.DESKTOP_TOOLBAR_PATH + element.getAttributeValue(Desktop.ATTRIBUTE_ICON), element.getAttributeValue(Desktop.ATTRIBUTE_TASK));
					button.setFont(FontStore.getDefaultFont());
					toolBar.add(button);
					lastSeparator = false;
				}
				
			}
			else if (type.equals(Desktop.SEPARATOR) && (!lastSeparator))
			{
				toolBar.addSeparator();
				lastSeparator = true;
			}
		}
		
		add(toolBar);
	}
	
	private JButton buttonItem(String text, String description, String icon, String task)
	{
		JButton button = new JButton();
		button.setText(" " + text + " ");
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setActionCommand(task);
		button.addActionListener(this);
		button.setIcon(ImageStore.getIcon(icon));
		button.setToolTipText("<html><b>" + text + "</b><br>" + description + "</html>");
		
		button.setPreferredSize(new Dimension(100, 65));
		button.setMaximumSize(new Dimension(100, 65));
		button.setMinimumSize(new Dimension(100, 65));
		
		return button;
	}
	
	public void callOptionTask(String path)
	{
		OptionTask<?> task = (OptionTask<?>)Environment.instanceClass(Constants.BASE_OPTION_TASK + path);
		
		if (task.isSingle())
		{
			if (isSingleTaskActive(task))
			{
				task.closeSpecial();
				OptionTask<?> oldTask = this.singleTasks.get(task.getClass().getCanonicalName());
				oldTask.toFront();
			}
			else
			{
				this.singleTasks.put(task.getClass().getCanonicalName(), task);
				task.run();
			}
		}
		else
		{
			task.run();
		}
	}

	public void closeTask(OptionTask<?> task)
	{
		if (task.isSingle())
		{
			if (isSingleTaskActive(task))
			{
				this.singleTasks.remove(task.getClass().getCanonicalName());
			}
		}
	}

	private boolean isSingleTaskActive(OptionTask<?> task)
	{
		return this.singleTasks.containsKey(task.getClass().getCanonicalName());
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		callOptionTask(event.getActionCommand());
	}
	
	public void addTask(OptionTask<?> task)
	{
		this.tasksList.add(task);
	}
	
	public void removeTask(OptionTask<?> task)
	{
		this.tasksList.remove(task);
	}
	
	public void blurAllWindows(boolean blur)
	{
		synchronized (this.tasksList)
		{
			for (int i = 0; i < this.tasksList.size(); i++)
			{
				this.tasksList.get(i).blurWindow(blur);
			}
		}
	}
	
	public void addWindowToDesktop(ExtendedWindow window)
	{
		getContentPane().add(window);
		window.showWindow();
		
		try
		{
			window.setSelected(true);
			window.start();
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
	}
	
	public static Desktop getDesktop()
	{
		return Desktop.self;
	}
	
	public static boolean isDesktopSet()
	{
		return Desktop.self != null;
	}
	
	public JDesktopPane getDesktopPane()
	{
		return this.desktop;
	}
	
	private class DesktopManager extends DefaultDesktopManager
	{
		private static final long serialVersionUID = 2579392641919643665L;

		@Override
		public void dragFrame(JComponent f, int xPanel, int yPanel)
		{
			int x = xPanel;
			int y = yPanel;
			
			if (f instanceof JInternalFrame)
			{
				JInternalFrame frame = (JInternalFrame)f;
				JDesktopPane desk = frame.getDesktopPane();
				Dimension d = desk.getSize();
				
				if (x < 0)
				{
					x = 0;
				}
				else if ((x + frame.getWidth()) > d.width)
				{
					x = d.width - frame.getWidth();
				}
				
				if (y < (Desktop.TOOL_BAR_HEIGHT - 1))
				{
					y = Desktop.TOOL_BAR_HEIGHT - 1;
				}
				else if ((y + frame.getHeight()) > d.height)
				{
					y = d.height - frame.getHeight();
				}
			}
			
			super.dragFrame(f, x, y);
		}
	}
}