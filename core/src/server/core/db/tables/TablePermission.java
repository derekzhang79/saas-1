package server.core.db.tables;

import java.sql.Connection;

import server.core.db.Table;
import share.core.objects.Permission;
import share.core.objects.Task;

public class TablePermission extends Table {
	
	public Integer id = new Integer(0);
	public Integer user_group = new Integer(0);
	public Integer task = new Integer(0);
	
	public TablePermission(Connection connection) {
		super(connection, "PERMISSION");
		setTable(this);
	}
	
	public Permission[] getPermissions(int groupID) {
		this.user_group = groupID;
		
		int number = search("id");
		
		Permission[] result = new Permission[number];
		
		for (int i = 0; i < number; i++) {
			select(i);
			
			Task taskTable = getTask(this.task);
			
			result[i] = new Permission(this.id, this.user_group, this.task, taskTable.name, taskTable.path);
		}
		
		return result;
	}
	
	public boolean add(Permission permission) {
		this.user_group = permission.userGroup;
		this.task = permission.task;
		
		return create();
	}
	
	public boolean delete(Permission permission) {
		boolean valid = false;
		
		this.id = permission.id;
		
		if (read()) {
			valid = delete();
		}
		
		return valid;
	}
	
	public boolean deleteAll(Integer userGroupID) {
		
		this.user_group = userGroupID;
		
		int number = search();
		int deleted = 0;
		
		for (int i = 0; i < number; i++) {
			select(i);
			
			if (delete()) {
				deleted++;
			}
		}
		
		return (number == deleted);
	}
	
	private Task getTask(Integer taskID) {
		Task result = null;
		
		TableTask table = new TableTask(getConnection());
		result = table.getTask(taskID);
		
		return result;
	}
}