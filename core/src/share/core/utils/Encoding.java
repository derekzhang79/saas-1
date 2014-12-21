package share.core.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import share.core.debug.AppError;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encoding
{
	public static final String UTF8 = "UTF-8";
	private static final String SHA_256 = "SHA-256";
	
	public static String getSHA256(String text)
	{
		StringBuilder builder = new StringBuilder();
		
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance(Encoding.SHA_256);
			messageDigest.update(text.getBytes());

			byte[] bytes = messageDigest.digest();

			for (byte value : bytes)
			{
				builder.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static byte[] base64DecodeByte(byte[] array)
	{
		byte[] result = new byte[0];

		try
		{
			result = Encoding.base64DecodeByte(new String(array, Encoding.UTF8));
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String base64EncodeByte(byte[] data)
	{
		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(data);
	}

	public static byte[] base64DecodeByte(String text)
	{
		byte[] result = new byte[0];

		try
		{
			BASE64Decoder decoder = new BASE64Decoder();
			result = decoder.decodeBuffer(text);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String base64EncodeString(String text)
	{
		String result = "";

		try
		{
			BASE64Encoder encoder = new BASE64Encoder();
			result = encoder.encode(text.getBytes()).replace("\r\n", "");
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String base64DecodeString(String text)
	{
		String result = "";

		try
		{
			BASE64Decoder decoder = new BASE64Decoder();
			result = new String(decoder.decodeBuffer(text), Encoding.UTF8);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static String URLEncode(String text)
	{
		String value = "";

		try
		{
			value = URLEncoder.encode(text, Encoding.UTF8);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return value;
	}

	public static String URLDecode(String text)
	{
		String value = "";

		try
		{
			value = URLDecoder.decode(text, Encoding.UTF8);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return value;
	}
}