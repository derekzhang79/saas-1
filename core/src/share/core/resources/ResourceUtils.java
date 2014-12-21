package share.core.resources;

import java.io.Closeable;

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
}