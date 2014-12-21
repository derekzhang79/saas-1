package share.app.configuration.users;

import share.core.objects.User;

public interface IOperations
{
	public static final String GET_USERS = "server.app.configuration.users.Operations:getUsers";
	public static final String ADD_USER = "server.app.configuration.users.Operations:addUser";
	public static final String EDIT_USER = "server.app.configuration.users.Operations:editUser";
	public static final String DELETE_USER = "server.app.configuration.users.Operations:deleteUser";

	public User[] getUsers();

	public Boolean addUser(User user);

	public Boolean editUser(User original, User newUser);

	public Boolean deleteUser(User user);
}