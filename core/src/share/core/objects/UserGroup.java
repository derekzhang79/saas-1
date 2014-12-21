package share.core.objects;

import share.core.Shareable;

public class UserGroup extends Shareable
{
	private static final long serialVersionUID = -8415976039505158511L;
	
	public final int id;
	public final String name;
	public final boolean administrator;
	public final int client;
	
	public UserGroup(int id, String name, boolean administrator, int client)
	{
		this.id = id;
		this.name = name;
		this.administrator = administrator;
		this.client = client;
	}
}