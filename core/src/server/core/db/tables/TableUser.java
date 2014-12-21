package server.core.db.tables;

import java.sql.Connection;

import server.core.db.Table;
import share.core.objects.User;

public class TableUser extends Table {
	
	public Integer id = new Integer(0);
	public String name = new String();
	public String password = new String();
	public String ticket = new String();
	public String session_id = new String();
	public Integer user_group = new Integer(0);
	
	public TableUser(Connection connection) {
		super(connection, "USER");
		setTable(this);
	}
	
	public User[] getUsers() {
		int number = search("id");
		
		User[] result = new User[number];
		
		for (int i = 0; i < number; i++) {
			select(i);
			
			TableUserGroup table = getUserGroup(this.user_group);
			
			result[i] = new User(this.id, this.name, this.password, this.user_group, table.name, table.administrator);
		}
		
		return result;
	}
	
	public boolean add(User user) {
		this.name = user.name;
		this.password = user.password;
		this.user_group = user.userGroup;
		
		return create();
	}
	
	public boolean edit(User original, User newUser) {
		boolean valid = false;
		
		this.id = original.id;
		
		if (read()) {
			this.name = newUser.name;
			this.user_group = newUser.userGroup;
			
			if (!newUser.password.isEmpty()) {
				this.password = newUser.password;
			}
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(User user) {
		boolean valid = false;
		
		this.id = user.id;
		
		if (read()) {
			valid = delete();
		}
		
		return valid;
	}
	
	private TableUserGroup getUserGroup(int userGroupID) {
		TableUserGroup table = new TableUserGroup(getConnection());
		table.getUserGroup(userGroupID);
		
		return table;
	}
}