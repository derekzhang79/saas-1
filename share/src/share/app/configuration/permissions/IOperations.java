package share.app.configuration.permissions;

import share.core.objects.Permission;
import share.core.objects.Task;

public interface IOperations
{
	public static final String GET_TASKS = "server.app.configuration.permissions.Operations:getTasks";
	public static final String GET_USER_GROUP_PERMISSION = "server.app.configuration.permissions.Operations:getUserGroupPermissions";
	public static final String ADD_USER_GROUP_PERMISSION = "server.app.configuration.permissions.Operations:addUserGroupPermission";
	public static final String DELETE_USER_GROUP_PERMISSION = "server.app.configuration.permissions.Operations:deleteUserGroupPermission";

	public Task[] getTasks();

	public Permission[] getUserGroupPermissions(Integer groupID);

	public Boolean addUserGroupPermission(Permission permission);

	public Boolean deleteUserGroupPermission(Permission permission);
}