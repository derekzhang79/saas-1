package share.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import share.core.debug.AppError;

public class Serializer
{
	public static Object unserialize(String string)
	{
		Object result = null;

		try
		{
			byte[] data = Encoding.base64DecodeByte(string);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			result = ois.readObject();
			ois.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String serialize(Serializable object)
	{
		String result = "";

		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			result = Encoding.base64EncodeByte(baos.toByteArray());
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}
}