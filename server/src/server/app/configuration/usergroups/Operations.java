package server.app.configuration.usergroups;

import java.sql.Connection;
import server.core.ServerOperation;
import server.core.db.tables.TableUserGroup;
import share.app.configuration.usergroups.IOperations;
import share.core.objects.UserGroup;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public UserGroup[] getUserGroups()
	{
		TableUserGroup table = new TableUserGroup(getSystemConnection());

		return table.getUserGroups();
	}

	@Override
	public UserGroup addUserGroup(UserGroup userGroup)
	{
		TableUserGroup table = new TableUserGroup(getSystemConnection());

		return table.add(userGroup);
	}

	@Override
	public Boolean editUserGroup(UserGroup original, UserGroup newUserGroup)
	{
		TableUserGroup table = new TableUserGroup(getSystemConnection());

		return table.edit(original, newUserGroup);
	}

	@Override
	public Boolean deleteUserGroup(UserGroup userGroup)
	{
		TableUserGroup table = new TableUserGroup(getSystemConnection());

		return table.delete(userGroup);
	}
}