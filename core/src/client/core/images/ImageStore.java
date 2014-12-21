package client.core.images;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageStore
{
	public static Image getImage(String name)
	{
		ImageCreator creator = new ImageCreator();
		
		return creator.getImage(name);
	}
	
	public static Icon getIcon(String name)
	{
		ImageCreator creator = new ImageCreator();
		
		return creator.getIcon(name);
	}
	
	public static ImageIcon getImageIcon(String name)
	{
		ImageCreator creator = new ImageCreator();
		
		return creator.getImageIcon(name);
	}
	
	public static byte[] getImageBytes(String name)
	{
		ImageCreator creator = new ImageCreator();
		
		return creator.getImageBytes(name);
	}
}