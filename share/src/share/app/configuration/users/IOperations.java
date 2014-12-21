package share.app.configuration.users;

import share.core.objects.User;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.configuration.users.Operations:";
	public static final String GET_USERS = IOperations.BASE_OPERATIONS + "getUsers";
	public static final String ADD_USER = IOperations.BASE_OPERATIONS + "addUser";
	public static final String EDIT_USER = IOperations.BASE_OPERATIONS + "editUser";
	public static final String DELETE_USER = IOperations.BASE_OPERATIONS + "deleteUser";
	
	public User[] getUsers();
	
	public Boolean addUser(User user);
	
	public Boolean editUser(User original, User newUser);
	
	public Boolean deleteUser(User user);
}