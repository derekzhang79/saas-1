package client.core.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import share.core.Constants;
import client.core.Debug;

public class ImageCreator
{

	public Image getImage(String name)
	{
		Image image = null;

		try
		{
			image = ImageIO.read(getURL(name));
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}

		return image;
	}

	public byte[] getImageBytes(String name)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try
		{
			BufferedImage buffeeredImage = ImageIO.read(getURL(name));
			ImageIO.write(buffeeredImage, "PNG", baos);
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}

		return baos.toByteArray();
	}

	public Icon getIcon(String name)
	{
		Icon icon = null;

		try
		{
			icon = new ImageIcon(getURL(name));
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}

		return icon;
	}

	public ImageIcon getImageIcon(String name)
	{
		ImageIcon icon = null;

		try
		{
			icon = new ImageIcon(getURL(name));
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}

		return icon;
	}

	private URL getURL(String name)
	{
		return getClass().getResource(Constants.IMAGE_PATH + name);
	}
}