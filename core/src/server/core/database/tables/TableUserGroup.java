package server.core.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.core.objects.UserGroup;

public class TableUserGroup extends Table
{
	public Integer id = new Integer(0);
	public String name = new String();
	public Boolean administrator = new Boolean(false);
	public Integer client = new Integer(0);
	
	public TableUserGroup(Connection connection)
	{
		super(connection, "USER_GROUP");
		setTable(this);
	}
	
	public void getUserGroup(Integer userGroupID)
	{
		this.id = userGroupID;
		read();
	}
	
	public UserGroup[] getUserGroups()
	{
		int number = search("id");
		
		UserGroup[] result = new UserGroup[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new UserGroup(this.id, this.name, this.administrator, this.client);
		}
		
		return result;
	}
	
	public UserGroup add(UserGroup userGroup)
	{
		UserGroup result = null;
		
		this.name = userGroup.name;
		this.administrator = userGroup.administrator;
		this.client = userGroup.client;
		
		if (create())
		{
			result = new UserGroup(getLastId(), this.name, this.administrator, this.client);
		}
		
		return result;
	}
	
	public boolean edit(UserGroup original, UserGroup newUserGroup)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = original.id;
		
		if (read())
		{
			this.name = newUserGroup.name;
			this.administrator = newUserGroup.administrator;
			
			if (update())
			{
				if (this.administrator)
				{
					TablePermission table = new TablePermission(getConnection());
					
					if (table.deleteAll(this.id))
					{
						valid = commit();
					}
					else
					{
						rollback();
					}
				}
				else
				{
					valid = commit();
				}
			}
			else
			{
				rollback();
			}
		}
		
		return valid;
	}
	
	public boolean delete(UserGroup userGroup)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = userGroup.id;
		
		if (read())
		{
			TablePermission table = new TablePermission(getConnection());
			
			if (table.deleteAll(userGroup.id))
			{
				if (delete())
				{
					valid = commit();
				}
				else
				{
					rollback();
				}
			}
			else
			{
				rollback();
			}
		}
		
		return valid;
	}
}