package share.core.utils;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import share.core.debug.AppError;
import share.core.resources.ResourceUtils;

public class Resource
{
	public InputStream getReource(String path)
	{
		return getClass().getResourceAsStream(path);
	}
	
	public static InputStream get(String path)
	{
		Resource resource = new Resource();
		
		return resource.getReource(path);
	}
	
	public static byte[] load(String path)
	{
		byte[] result = new byte[0];
		InputStream inputStream = null;
		
		try
		{
			inputStream = Resource.get(path);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			int nRead = 0;
			byte[] data = new byte[1024];
			
			while ((nRead = inputStream.read(data, 0, data.length)) != -1)
			{
				buffer.write(data, 0, nRead);
			}
			
			buffer.flush();
			
			result = buffer.toByteArray();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		finally
		{
			ResourceUtils.close(inputStream);
		}
		
		return result;
	}
	
	public static void open(File file)
	{
		if (file.exists())
		{
			try
			{
				Desktop.getDesktop().open(file);
			}
			catch (Exception e)
			{
				AppError.setError(e);
			}
		}
	}
	
	public static void open(String path)
	{
		Resource.open(new File(path));
	}
}