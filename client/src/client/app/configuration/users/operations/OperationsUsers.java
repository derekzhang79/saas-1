package client.app.configuration.users.operations;

import share.app.configuration.users.IOperations;
import share.core.objects.User;
import client.core.operations.Operation;

public class OperationsUsers implements IOperations
{
	private static OperationsUsers instance = new OperationsUsers();

	public static OperationsUsers call()
	{
		return OperationsUsers.instance;
	}

	@Override
	public User[] getUsers()
	{
		Operation<User[]> operation = new Operation<User[]>(IOperations.GET_USERS);

		return operation.run();
	}

	@Override
	public Boolean addUser(User user)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_USER);

		return operation.run(user);
	}

	@Override
	public Boolean editUser(User original, User newUser)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_USER);

		return operation.run(original, newUser);
	}

	@Override
	public Boolean deleteUser(User user)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_USER);

		return operation.run(user);
	}
}