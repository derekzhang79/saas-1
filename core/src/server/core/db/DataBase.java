package server.core.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;

import server.core.ServerError;
import share.core.Constants.AppDatabase;
import share.core.Environment;
import share.core.MapTable;
import share.core.conf.Configurator;
import share.core.conf.database.ConfDatabase;

public class DataBase {
	
	private MapTable<String, Connection> connections = new MapTable<String, Connection>();
	
	private String dbEnvironment = "";
	
	private static final String JDBC_MYSQL = "jdbc:mysql://localhost/";
	public static final String ATTR_ENVIRONMENT = "environment";
	
	public DataBase(String dbEnvironment) {
		this.dbEnvironment = dbEnvironment;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			createSysConnection();
		} catch (Exception e) {
			ServerError.setError(e);
		}
	}
	
	public static String getBackup(String database) {
		File file = Environment.createTempFile(".sql");
		ConfDatabase conf = Configurator.getDatabase();
		
		try {
			Process runtimeProcess = Runtime.getRuntime().exec("mysqldump -h localhost -u " + conf.user + " --password=" + conf.pass + " " + database + " > " + file.getAbsolutePath());
			runtimeProcess.waitFor();
		} catch (Exception e) {
			ServerError.setError(e);
		}
		
		return Environment.readFile(file);
	}
	
	private Connection connect(String environment, String database, String user, String pass) {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(DataBase.JDBC_MYSQL + database, user, pass);
			connection.setClientInfo(DataBase.ATTR_ENVIRONMENT, environment.toLowerCase());
		} catch (Exception e) {
			ServerError.setError(e);
		}
		
		return connection;
	}
	
	private void createSysConnection() {
		ConfDatabase conf = Configurator.getDatabase();
		
		this.connections.put(AppDatabase.SYSTEM.toString(), connect(this.dbEnvironment, AppDatabase.SYSTEM.toString() + "_" + this.dbEnvironment, conf.user, conf.pass));
	}
	
	public void createAppConnection(String name) {
		ConfDatabase conf = Configurator.getDatabase();
		
		this.connections.put(AppDatabase.APPLICATION.toString(), connect(this.dbEnvironment, name + "_" + this.dbEnvironment, conf.user, conf.pass));
	}
	
	public void closeConnections() {
		Enumeration<Connection> values = this.connections.elements();
		
		while (values.hasMoreElements()) {
			Connection current = values.nextElement();
			
			try {
				current.close();
			} catch (Exception e) {
				ServerError.setError(e);
			}
		}
	}
	
	public Connection getConnection(AppDatabase name) {
		return this.connections.get(name.toString());
	}
}