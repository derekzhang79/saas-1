package share.core.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import share.core.debug.AppError;

public class Crypt
{
	private static final String RSA = "RSA";
	private static final String TYPE_CHIPER = "RSA/ECB/PKCS1Padding";

	private static final int KEY_LENGTH = 1024;
	private static final int ENCRYPTED_LENGTH = Crypt.KEY_LENGTH / 8;
	private static final int DATA_LENGTH = Crypt.ENCRYPTED_LENGTH - 11;

	private static String[] splitText(String text, int size)
	{
		List<String> list = new ArrayList<String>();

		int parts = text.length() / size;

		for (int i = 0; i < parts; i++)
		{
			list.add(text.substring(i * size, (i * size) + size));
		}

		int rest = text.length() - (parts * size);

		if (rest > 0)
		{
			list.add(text.substring(parts * size, (parts * size) + rest));
		}

		String[] result = new String[list.size()];
		list.toArray(result);

		return result;
	}

	public static byte[] encrypt(String text, byte[] keyBytes)
	{
		byte[] encrypted = new byte[0];

		try
		{
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(Crypt.RSA);
			RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance(Crypt.TYPE_CHIPER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			String[] split = Crypt.splitText(text, Crypt.DATA_LENGTH);
			encrypted = new byte[Crypt.ENCRYPTED_LENGTH * split.length];

			for (int i = 0; i < split.length; i++)
			{
				byte[] source = split[i].getBytes(Encoding.UTF8);
				byte[] target = cipher.doFinal(source);
				System.arraycopy(target, 0, encrypted, i * Crypt.ENCRYPTED_LENGTH, target.length);
			}

		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return encrypted;
	}

	public static String decrypt(byte[] data, byte[] keyBytes)
	{
		String result = "";

		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance(Crypt.RSA);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

			Cipher cipher = Cipher.getInstance(Crypt.TYPE_CHIPER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			List<Byte> byteList = new ArrayList<Byte>();
			int parts = data.length / Crypt.ENCRYPTED_LENGTH;

			for (int i = 0; i < parts; i++)
			{
				byte[] source = new byte[Crypt.ENCRYPTED_LENGTH];
				System.arraycopy(data, i * Crypt.ENCRYPTED_LENGTH, source, 0, Crypt.ENCRYPTED_LENGTH);

				byte[] target = cipher.doFinal(source);

				for (byte element : target)
				{
					byteList.add(element);
				}
			}

			byte[] decrypted = new byte[byteList.size()];

			for (int i = 0; i < byteList.size(); i++)
			{
				decrypted[i] = byteList.get(i);
			}

			result = new String(decrypted, Encoding.UTF8);

		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static KeyPair createKeys()
	{
		KeyPair result = null;

		try
		{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(Crypt.KEY_LENGTH);
			result = keyGen.genKeyPair();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}

		return result;
	}

	public static void generateServerKeys(String publicPath, String privatePath)
	{
		KeyPair keys = Crypt.createKeys();

		PrivateKey privateKey = keys.getPrivate();
		PublicKey publicKey = keys.getPublic();

		Crypt.saveKey(privateKey.getEncoded(), privatePath);
		Crypt.saveKey(publicKey.getEncoded(), publicPath);
	}

	private static void saveKey(byte[] key, String path)
	{
		try
		{
			FileWriter fstream = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(Encoding.base64EncodeByte(key));
			out.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}

	public static void main(String[] args)
	{
		Crypt.generateServerKeys("../public.key", "../private.key");
	}
}