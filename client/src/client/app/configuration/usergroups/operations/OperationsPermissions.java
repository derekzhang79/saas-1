package client.app.configuration.usergroups.operations;

import share.app.configuration.permissions.IOperations;
import share.core.objects.Permission;
import share.core.objects.Task;
import client.core.Operation;

public class OperationsPermissions implements IOperations {
	
	private static OperationsPermissions instance = new OperationsPermissions();
	
	public static OperationsPermissions call() {
		return OperationsPermissions.instance;
	}
	
	public Task[] getTasks() {
		Operation<Task[]> operation = new Operation<Task[]>(IOperations.GET_TASKS);
		
		return operation.run();
	}
	
	public Permission[] getUserGroupPermissions(Integer groupID) {
		Operation<Permission[]> operation = new Operation<Permission[]>(IOperations.GET_USER_GROUP_PERMISSION);
		
		return operation.run(groupID);
	}
	
	public Boolean addUserGroupPermission(Permission permission) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_USER_GROUP_PERMISSION);
		
		return operation.run(permission);
	}
	
	public Boolean deleteUserGroupPermission(Permission permission) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_USER_GROUP_PERMISSION);
		
		return operation.run(permission);
	}
}