package server.app.configuration.users;

import java.sql.Connection;
import server.core.connection.ServerOperation;
import server.core.database.tables.TableUser;
import share.app.configuration.users.IOperations;
import share.core.objects.User;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public User[] getUsers()
	{
		TableUser table = new TableUser(getSystemConnection());

		return table.getUsers();
	}

	@Override
	public Boolean addUser(User user)
	{
		TableUser table = new TableUser(getSystemConnection());

		return table.add(user);
	}

	@Override
	public Boolean editUser(User original, User newUser)
	{
		TableUser table = new TableUser(getSystemConnection());

		return table.edit(original, newUser);
	}

	@Override
	public Boolean deleteUser(User user)
	{
		TableUser table = new TableUser(getSystemConnection());

		return table.delete(user);
	}
}