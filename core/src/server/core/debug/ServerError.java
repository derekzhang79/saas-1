package server.core.debug;

public class ServerError
{
	public static void setError(Exception e)
	{
		e.printStackTrace();
	}
}