package server.app.configuration.permissions;

import java.sql.Connection;
import server.core.connection.ServerOperation;
import server.core.database.tables.TablePermission;
import server.core.database.tables.TableTask;
import share.app.configuration.permissions.IOperations;
import share.core.objects.Permission;
import share.core.objects.Task;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}
	
	@Override
	public Task[] getTasks()
	{
		TableTask table = new TableTask(getSystemConnection());
		
		return table.getTasks();
	}
	
	@Override
	public Permission[] getUserGroupPermissions(Integer groupID)
	{
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.getPermissions(groupID);
	}
	
	@Override
	public Boolean addUserGroupPermission(Permission permission)
	{
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.add(permission);
	}
	
	@Override
	public Boolean deleteUserGroupPermission(Permission permission)
	{
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.delete(permission);
	}
}