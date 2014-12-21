package share.core.objects;

import share.core.Shareable;

public class User extends Shareable {
	
	private static final long serialVersionUID = 1L;
	
	public int id = 0;
	public String name = "";
	public String password = "";
	public int userGroup = 0;
	
	public String groupName = "";
	public boolean administrator = false;
	public String[] permissions = new String[0];
	
	public User() {
	}
	
	public User(int id, String name, String password, int userGroup) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.userGroup = userGroup;
	}
	
	public User(int id, String name, String password, int userGroup, String groupName, boolean administrator) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.userGroup = userGroup;
		this.groupName = groupName;
		this.administrator = administrator;
	}
	
	public User(int id, String name, String password, int userGroup, boolean administrator, String[] permissions) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.userGroup = userGroup;
		this.administrator = administrator;
		this.permissions = permissions;
	}
}