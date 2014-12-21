package client.core;

import client.core.connection.Communication;

public class Operation<ResponseClass>
{
	private final boolean isLogin;
	private final String operation;
	
	public Operation(String operation)
	{
		this.isLogin = false;
		this.operation = operation;
	}
	
	public Operation()
	{
		this.isLogin = true;
		this.operation = "";
	}
	
	public ResponseClass run(Object... parameters)
	{
		Communication<ResponseClass> communication = new Communication<ResponseClass>(this.operation, Profile.getUserName(), Profile.getClientID());
		
		if (this.isLogin)
		{
			communication.setPass(Profile.getUserPassword());
		}
		
		return communication.send(parameters);
	}
}