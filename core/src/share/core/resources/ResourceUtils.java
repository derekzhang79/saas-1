package share.core.resources;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils
{
	public static void close(Closeable resource)
	{
		if (resource != null)
		{
			try
			{
				resource.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static byte[] readInputStream(InputStream inputStream)
	{
		byte[] result = new byte[0];

		try
		{
			byte[] bytes = new byte[1024 * 1024 * 1]; // 1 MB
			
			int read = inputStream.read(bytes);
			result = new byte[read];

			System.arraycopy(bytes, 0, result, 0, read);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}