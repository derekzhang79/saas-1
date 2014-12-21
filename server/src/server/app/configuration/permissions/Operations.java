package server.app.configuration.permissions;

import java.sql.Connection;

import server.core.ServerOperation;
import server.core.db.tables.TablePermission;
import server.core.db.tables.TableTask;
import share.app.configuration.permissions.IOperations;
import share.core.objects.Permission;
import share.core.objects.Task;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public Task[] getTasks() {
		TableTask table = new TableTask(getSystemConnection());
		
		return table.getTasks();
	}
	
	public Permission[] getUserGroupPermissions(Integer groupID) {
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.getPermissions(groupID);
	}
	
	public Boolean addUserGroupPermission(Permission permission) {
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.add(permission);
	}
	
	public Boolean deleteUserGroupPermission(Permission permission) {
		TablePermission table = new TablePermission(getSystemConnection());
		
		return table.delete(permission);
	}
	
}