package server.core.connection;

import java.sql.Connection;
import server.core.debug.ServerError;

public class ServerOperation
{
	private Connection appConnection = null;
	private Connection sysConnection = null;
	
	public ServerOperation(Connection appConnection, Connection sysConnection)
	{
		this.appConnection = appConnection;
		this.sysConnection = sysConnection;
	}
	
	public Connection getConnection()
	{
		return this.appConnection;
	}
	
	public Connection getSystemConnection()
	{
		return this.sysConnection;
	}
	
	public boolean beginTransaction()
	{
		boolean valid = false;
		
		try
		{
			getConnection().setAutoCommit(false);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
	
	public boolean rollback()
	{
		boolean valid = false;
		
		try
		{
			getConnection().rollback();
			getConnection().setAutoCommit(true);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
	
	public boolean commit()
	{
		boolean valid = false;
		
		try
		{
			getConnection().commit();
			getConnection().setAutoCommit(true);
			valid = true;
		}
		catch (Exception e)
		{
			ServerError.setError(e);
		}
		
		return valid;
	}
}