package client.core.gui.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import share.core.MapTable;
import client.core.gui.fonts.FontStore;
import client.core.gui.window.WindowManager;
import client.core.images.ImageStore;

public class ExtendedMenu extends JMenu implements ActionListener
{
	private static final long serialVersionUID = 5839900638902102962L;
	
	private final WindowManager manager;
	private final MapTable<String, JMenuItem> components = new MapTable<String, JMenuItem>();
	
	public ExtendedMenu(String text, WindowManager window)
	{
		super(" " + text);
		
		this.manager = window;
		setFont(FontStore.getDefaultFont());
		setPreferredSize(new Dimension(getFontMetrics(getFont()).stringWidth(" " + text) + 15, 25));
	}
	
	public JMenuItem addMenu(String name, String text, String icon, String action)
	{
		JMenuItem menu = new JMenuItem(text);
		menu.setIcon(ImageStore.getIcon(icon));
		menu.setActionCommand(action);
		menu.addActionListener(this);
		add(menu);
		
		this.components.put(name, menu);
		
		return menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		this.manager.actionPerformed(event);
	}
}