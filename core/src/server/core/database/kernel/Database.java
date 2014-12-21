package server.core.database.kernel;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import server.core.debug.ServerError;
import share.core.conf.Configurator;
import share.core.conf.database.ConfDatabase;
import share.core.constants.Constants.AppDatabase;
import share.core.utils.Environment;
import share.core.utils.MapTable;

public class Database
{
	private final MapTable<String, Connection> connections = new MapTable<String, Connection>();
	
	private String databaseEnvironment = "";
	
	private static final String JDBC_MYSQL = "jdbc:mysql://localhost/";
	private static final String ATTR_ENVIRONMENT = "environment";
	
	public Database(String databaseEnvironment)
	{
		this.databaseEnvironment = databaseEnvironment;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			createSysConnection();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
	}
	
	public static String getBackup(String database)
	{
		File file = Environment.createTempFile(".sql");
		ConfDatabase conf = Configurator.getDatabase();
		
		try
		{
			Process runtimeProcess = Runtime.getRuntime().exec("mysqldump -h localhost -u " + conf.user + " --password=" + conf.pass + " " + database + " > " + file.getAbsolutePath());
			runtimeProcess.waitFor();
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return Environment.readFile(file);
	}
	
	private Connection connect(String environment, String database, String user, String pass)
	{
		Connection connection = null;
		
		try
		{
			connection = DriverManager.getConnection(Database.JDBC_MYSQL + database, user, pass);
			connection.setClientInfo(Database.ATTR_ENVIRONMENT, environment.toLowerCase());
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return connection;
	}
	
	private void createSysConnection()
	{
		ConfDatabase conf = Configurator.getDatabase();
		
		this.connections.put(AppDatabase.SYSTEM.toString(), connect(this.databaseEnvironment, AppDatabase.SYSTEM.toString() + "_" + this.databaseEnvironment, conf.user, conf.pass));
	}
	
	public void createAppConnection(String name)
	{
		ConfDatabase conf = Configurator.getDatabase();
		
		this.connections.put(AppDatabase.APPLICATION.toString(), connect(this.databaseEnvironment, name + "_" + this.databaseEnvironment, conf.user, conf.pass));
	}
	
	public void closeConnections()
	{
		Enumeration<Connection> values = this.connections.elements();
		
		while (values.hasMoreElements())
		{
			Connection current = values.nextElement();
			
			try
			{
				current.close();
			}
			catch (Exception e)
			{
				ServerError.setError(e);
			}
		}
	}
	
	public Connection getConnection(AppDatabase name)
	{
		return this.connections.get(name.toString());
	}
}