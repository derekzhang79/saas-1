package share.core.debug;

public class AppError
{
	public static void setError(Exception e)
	{
		e.printStackTrace();
	}
}