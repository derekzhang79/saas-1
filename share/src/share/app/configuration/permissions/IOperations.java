package share.app.configuration.permissions;

import share.core.objects.Permission;
import share.core.objects.Task;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.configuration.permissions.Operations:";
	public static final String GET_TASKS = IOperations.BASE_OPERATIONS + "getTasks";
	public static final String GET_USER_GROUP_PERMISSION = IOperations.BASE_OPERATIONS + "getUserGroupPermissions";
	public static final String ADD_USER_GROUP_PERMISSION = IOperations.BASE_OPERATIONS + "addUserGroupPermission";
	public static final String DELETE_USER_GROUP_PERMISSION = IOperations.BASE_OPERATIONS + "deleteUserGroupPermission";
	
	public Task[] getTasks();
	
	public Permission[] getUserGroupPermissions(Integer groupID);
	
	public Boolean addUserGroupPermission(Permission permission);
	
	public Boolean deleteUserGroupPermission(Permission permission);
}