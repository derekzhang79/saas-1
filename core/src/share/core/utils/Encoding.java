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
	private static final String MD5 = "MD5";

	public static String md5(String text)
	{
		String result = "";

		try
		{
			byte[] bytesOfMessage = text.getBytes(Encoding.UTF8);
			MessageDigest md = MessageDigest.getInstance(Encoding.MD5);
			md.reset();
			md.update(bytesOfMessage);

			byte messageDigest[] = md.digest();
			StringBuffer hexString = new StringBuffer();

			for (byte element : messageDigest)
			{
				String hex = Integer.toHexString(0xFF & element);

				if (hex.length() == 1)
				{
					hex = "0" + hex;
				}

				hexString.append(hex);
			}

			result = new String(hexString.toString());
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
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