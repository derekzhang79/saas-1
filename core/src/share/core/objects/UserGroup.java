package share.core.objects;

import share.core.Shareable;

public class UserGroup extends Shareable {
	
	private static final long serialVersionUID = 1L;
	
	public int id = 0;
	public String name = "";
	public boolean administrator = false;
	public int client = 0;
	
	public UserGroup(int id, String name, boolean administrator, int client) {
		this.id = id;
		this.name = name;
		this.administrator = administrator;
		this.client = client;
	}
}