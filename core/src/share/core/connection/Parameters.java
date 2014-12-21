package share.core.connection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import share.core.AppError;
import share.core.Serializer;

public class Parameters implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String user = "";
	private String pass = "";
	private int clientID = 0;
	private String ticket = "";
	private String sessionId = "";
	
	private String clazz = "";
	private String method = "";
	
	private String ip = "";
	private String dbEnvironment = "";
	
	private String javaVendor = "";
	private String javaVersion = "";
	private String javaJRE = "";
	
	private String osArch = "";
	private String osName = "";
	private String osVersion = "";
	
	private byte[] key = new byte[0];
	private ArrayList<Object> list = new ArrayList<Object>();
	
	public Parameters(String target, String user, String pass, int clientID, String sessionId, String ticket, String dbEnvironment, Object[] parameters) {
		
		if (!target.isEmpty()) {
			String[] data = target.split(":");
			this.clazz = data[0];
			this.method = data[1];
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
	
	private void setList(Object[] parameters) {
		for (Object parameter : parameters) {
			add(parameter);
		}
	}
	
	public boolean isLogin() {
		return (!this.user.isEmpty()) && (!this.pass.isEmpty());
	}
	
	public String getTicket() {
		return this.ticket;
	}
	
	public String getClazz() {
		return this.clazz;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPass() {
		return this.pass;
	}
	
	public int getClientID() {
		return this.clientID;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public String getDBEnvironment() {
		return this.dbEnvironment;
	}
	
	public void setKey(byte[] value) {
		this.key = value;
	}
	
	public byte[] getKey() {
		return this.key;
	}
	
	public void add(Object object) {
		this.list.add(object);
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public void setIP(String value) {
		this.ip = value;
	}
	
	public String getJavaVendor() {
		return this.javaVendor;
	}
	
	public void setJavaVendor(String javaVendor) {
		this.javaVendor = javaVendor;
	}
	
	public String getJavaVersion() {
		return this.javaVersion;
	}
	
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	
	public String getJavaJRE() {
		return this.javaJRE;
	}
	
	public void setJavaJRE(String javaJRE) {
		this.javaJRE = javaJRE;
	}
	
	public String getOsArch() {
		return this.osArch;
	}
	
	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}
	
	public String getOsName() {
		return this.osName;
	}
	
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getOsVersion() {
		return this.osVersion;
	}
	
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public Class<?>[] getParameterTypes() {
		Class<?> types[] = new Class[this.list.size()];
		
		for (int i = 0; i < types.length; i++) {
			types[i] = getClassDef(this.list.get(i));
		}
		
		return types;
	}
	
	private Class<?> getClassDef(Object object) {
		String clazzName = object.getClass().getCanonicalName();
		Class<?> result = null;
		
		if (clazzName.endsWith("[]")) {
			String simpleClass = clazzName.replace("[]", "");
			result = Array.newInstance(getClassDef(simpleClass), 0).getClass();
		} else {
			try {
				Class<?> classDef = Class.forName(clazzName);
				result = classDef;
			} catch (Exception e) {
				AppError.setError(e);
			}
		}
		return result;
	}
	
	public Object[] getParameters() {
		Object[] parameters = new Object[this.list.size()];
		
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = this.list.get(i);
		}
		
		return parameters;
	}
	
	public String getData() {
		return Serializer.serialize(this);
	}
}