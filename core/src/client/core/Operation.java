package client.core;

import client.core.connection.Communication;

public class Operation<ResponseClass> {
	
	private boolean isLogin = false;
	private String operation = "";
	
	public Operation(String operation) {
		this.operation = operation;
	}
	
	public Operation(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public ResponseClass run(final Object... parameters) {
		Communication<ResponseClass> communication = new Communication<ResponseClass>(this.operation, Profile.getUserName(), Profile.getClientID());
		
		if (this.isLogin) {
			communication.setPass(Profile.getUserPassword());
		}
		
		return communication.send(parameters);
	}
}