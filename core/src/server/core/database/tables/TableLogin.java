package server.core.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.core.connection.Parameters;

public class TableLogin extends Table
{
	public Integer id = new Integer(0);
	public String ip = new String();
	public String java_vendor = new String();
	public String java_version = new String();
	public String java_jre = new String();
	public String os_arch = new String();
	public String os_name = new String();
	public String os_version = new String();
	public Integer user = new Integer(0);

	public TableLogin(Connection connection)
	{
		super(connection, "LOGIN");
		setTable(this);
	}

	public boolean createRequest(Parameters parameters, int userID)
	{
		this.ip = parameters.getIP();
		this.java_vendor = parameters.getJavaVendor();
		this.java_version = parameters.getJavaVersion();
		this.java_jre = parameters.getJavaJRE();
		this.os_arch = parameters.getOsArch();
		this.os_name = parameters.getOsName();
		this.os_version = parameters.getOsVersion();
		this.user = userID;

		create();

		return true;
	}
}