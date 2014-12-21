package share.core.connection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import share.core.AppError;
import share.core.Serializer;

public class Parameters implements Serializable
{
	private static final long serialVersionUID = -3359707841037173802L;
	
	private final String user;
	private final String pass;
	private final int clientID;
	private final String ticket;
	private final String sessionId;

	private final String clazz;
	private final String method;

	private String ip = "";
	private final String dbEnvironment;

	private final String javaVendor;
	private final String javaVersion;
	private final String javaJRE;

	private final String osArch;
	private final String osName;
	private final String osVersion;

	private byte[] key = new byte[0];
	private final List<Object> list = new ArrayList<Object>();

	public Parameters(String target, String user, String pass, int clientID, String sessionId, String ticket, String dbEnvironment, Object[] parameters)
	{
		if (!target.isEmpty())
		{
			String[] data = target.split(":");
			this.clazz = data[0];
			this.method = data[1];
		}
		else
		{
			this.clazz = "";
			this.method = "";
		}

		this.dbEnvironment = dbEnvironment;

		this.javaVendor = System.getProperty("java.vendor");
		this.javaVersion = System.getProperty("java.version");
		this.javaJRE = System.getProperty("java.runtime.version");

		this.osArch = System.getProperty("os.arch");
		this.osName = System.getProperty("os.name");
		this.osVersion = System.getProperty("os.version");

		this.user = user;
		this.pass = pass;
		this.clientID = clientID;
		this.sessionId = sessionId;
		this.ticket = ticket;

		setList(parameters);
	}

	private void setList(Object[] parameters)
	{
		for (Object parameter : parameters)
		{
			add(parameter);
		}
	}

	public boolean isLogin()
	{
		return (!this.user.isEmpty()) && (!this.pass.isEmpty());
	}

	public String getTicket()
	{
		return this.ticket;
	}

	public String getClazz()
	{
		return this.clazz;
	}

	public String getMethod()
	{
		return this.method;
	}

	public String getUser()
	{
		return this.user;
	}

	public String getPass()
	{
		return this.pass;
	}

	public int getClientID()
	{
		return this.clientID;
	}

	public String getSessionId()
	{
		return this.sessionId;
	}

	public String getDBEnvironment()
	{
		return this.dbEnvironment;
	}

	public void setKey(byte[] value)
	{
		this.key = value;
	}

	public byte[] getKey()
	{
		return this.key;
	}

	public void add(Object object)
	{
		this.list.add(object);
	}

	public String getIP()
	{
		return this.ip;
	}

	public void setIP(String value)
	{
		this.ip = value;
	}

	public String getJavaVendor()
	{
		return this.javaVendor;
	}

	public String getJavaVersion()
	{
		return this.javaVersion;
	}

	public String getJavaJRE()
	{
		return this.javaJRE;
	}

	public String getOsArch()
	{
		return this.osArch;
	}

	public String getOsName()
	{
		return this.osName;
	}

	public String getOsVersion()
	{
		return this.osVersion;
	}

	public Class<?>[] getParameterTypes()
	{
		Class<?> types[] = new Class[this.list.size()];

		for (int i = 0; i < types.length; i++)
		{
			types[i] = getClassDef(this.list.get(i));
		}

		return types;
	}

	private Class<?> getClassDef(Object object)
	{
		String clazzName = object.getClass().getCanonicalName();
		Class<?> result = null;

		if (clazzName.endsWith("[]"))
		{
			String simpleClass = clazzName.replace("[]", "");
			result = Array.newInstance(getClassDef(simpleClass), 0).getClass();
		}
		else
		{
			try
			{
				Class<?> classDef = Class.forName(clazzName);
				result = classDef;
			}
			catch (Exception e)
			{
				AppError.setError(e);
			}
		}
		return result;
	}

	public Object[] getParameters()
	{
		Object[] parameters = new Object[this.list.size()];

		for (int i = 0; i < parameters.length; i++)
		{
			parameters[i] = this.list.get(i);
		}

		return parameters;
	}

	public String getData()
	{
		return Serializer.serialize(this);
	}
}