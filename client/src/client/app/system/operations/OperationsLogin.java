package client.app.system.operations;

import share.core.login.LoginResponse;
import client.core.Operation;

public class OperationsLogin {
	
	public static LoginResponse doLogin() {
		Operation<LoginResponse> operation = new Operation<LoginResponse>(true);
		
		return operation.run();
	}
}