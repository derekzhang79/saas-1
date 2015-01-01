package share.core.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.swing.filechooser.FileSystemView;
import share.core.constants.Constants;
import share.core.debug.AppError;

public class Environment
{
	private static final String PROPERTY_FILE_SEPARATOR = "file.separator";
	private static final String PROPERTY_LINE_SEPARATOR = "line.separator";
	private static final String PROPERTY_OS_NAME = "os.name";
	private static final String PROPERTY_JAVA_VERSION = "java.runtime.version";
	
	private static final String PATH_SEPARATOR = System.getProperty(Environment.PROPERTY_FILE_SEPARATOR);
	private static final String LINE_SEPARATOR = System.getProperty(Environment.PROPERTY_LINE_SEPARATOR);
	
	private static final String APPLICATION_PATH = System.getProperty("user.home") + Constants.APPLICATION_FOLDER;
	private static final String LAST_USER_PATH = Environment.APPLICATION_PATH + Constants.LAST_USER_FILE;
	
	private static final String OS_WINDOWS = "windows";
	private static final String OS_LINUX = "linux";
	private static final String OS_MAC = "mac";
	private static final String OS_SOLARIS = "solaris";
	
	public static String getOSName()
	{
		return System.getProperty(Environment.PROPERTY_OS_NAME);
	}
	
	public static String getJavaVersion()
	{
		return System.getProperty(Environment.PROPERTY_JAVA_VERSION);
	}
	
	public static boolean isWindows()
	{
		return Environment.getOSName().toLowerCase().contains(Environment.OS_WINDOWS);
	}
	
	public static boolean isLinux()
	{
		return Environment.getOSName().toLowerCase().contains(Environment.OS_LINUX);
	}
	
	public static boolean isMac()
	{
		return Environment.getOSName().toLowerCase().contains(Environment.OS_MAC);
	}
	
	public static boolean isSolaris()
	{
		return Environment.getOSName().toLowerCase().contains(Environment.OS_SOLARIS);
	}
	
	public static String newLine()
	{
		return Environment.LINE_SEPARATOR;
	}
	
	public static String getCurrentMonth()
	{
		String result = "";
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		
		if (month < 10)
		{
			result += "0";
		}
		
		result += month;
		
		return result;
	}
	
	public static void copyClipboard(String text)
	{
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(text), null);
	}
	
	public static void createFile(String path, String content)
	{
		Environment.createFile(path, content.getBytes());
	}
	
	public static void createFile(String path, byte[] content)
	{
		try
		{
			FileWriter file = new FileWriter(path);
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			file.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
	
	public static Object instanceClass(Class<?> clazz)
	{
		return Environment.instanceClass(clazz.getCanonicalName());
	}
	
	public static Object instanceClass(String path)
	{
		Object result = null;
		
		try
		{
			Class<?> taskClass = Class.forName(path);
			result = taskClass.newInstance();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		return result;
	}
	
	public static void setLastUserLogged(String user)
	{
		File file = new File(Environment.LAST_USER_PATH);
		
		try
		{
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(user);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
	
	public static String getLastUserLogged()
	{
		String lastUser = "";
		File file = new File(Environment.LAST_USER_PATH);
		
		if (file.exists())
		{
			try
			{
				FileInputStream fstream = new FileInputStream(file);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				lastUser = br.readLine();
				
				if (lastUser == null)
				{
					lastUser = "";
				}
				
				in.close();
			}
			catch (Exception e)
			{
				AppError.setError(e);
			}
		}
		
		return lastUser;
	}
	
	public static String getDesktopPath()
	{
		FileSystemView filesys = FileSystemView.getFileSystemView();
		
		return filesys.getHomeDirectory().getAbsolutePath() + Environment.PATH_SEPARATOR;
	}
	
	public static void createApplicationPath()
	{
		File dir = new File(Environment.APPLICATION_PATH);
		
		if (!dir.exists())
		{
			dir.mkdir();
		}
	}
	
	public static byte[] loadFile(String path)
	{
		byte[] result = new byte[0];
		
		try
		{
			File file = new File(path);
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			result = new byte[(int)file.length()];
			dis.readFully(result);
			dis.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		return result;
	}
	
	public static String readFile(File file)
	{
		String result = "";
		
		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			
			while ((strLine = br.readLine()) != null)
			{
				result += strLine + Environment.newLine();
			}
			in.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		return result;
	}
	
	public static void createExcel(String data, String ext)
	{
		try
		{
			File temp = File.createTempFile("temp", ext);
			temp.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(temp));
			out.write(new String(data.getBytes(), Encoding.UTF8));
			out.close();
			Resource.open(temp);
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
	}
	
	public static File createTempFile(String ext)
	{
		File temp = null;
		
		try
		{
			temp = File.createTempFile("temp", ext);
			temp.deleteOnExit();
			BufferedWriter out = new BufferedWriter(new FileWriter(temp));
			out.close();
		}
		catch (Exception e)
		{
			AppError.setError(e);
		}
		
		return temp;
	}
}