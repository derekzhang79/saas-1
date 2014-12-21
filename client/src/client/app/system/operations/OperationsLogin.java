package client.app.system.operations;

import share.core.login.LoginResponse;
import client.core.operations.Operation;

public class OperationsLogin
{
	public static LoginResponse doLogin()
	{
		Operation<LoginResponse> operation = new Operation<LoginResponse>();
		
		return operation.run();
	}
}