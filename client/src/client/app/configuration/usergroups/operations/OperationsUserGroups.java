package client.app.configuration.usergroups.operations;

import share.app.configuration.usergroups.IOperations;
import share.core.objects.UserGroup;
import client.core.operations.Operation;

public class OperationsUserGroups implements IOperations
{
	private static OperationsUserGroups instance = new OperationsUserGroups();
	
	public static OperationsUserGroups call()
	{
		return OperationsUserGroups.instance;
	}
	
	@Override
	public UserGroup[] getUserGroups()
	{
		Operation<UserGroup[]> operation = new Operation<UserGroup[]>(IOperations.GET_USER_GROUPS);
		
		return operation.run();
	}
	
	@Override
	public UserGroup addUserGroup(UserGroup userGroup)
	{
		Operation<UserGroup> operation = new Operation<UserGroup>(IOperations.ADD_USER_GROUP);
		
		return operation.run(userGroup);
	}
	
	@Override
	public Boolean editUserGroup(UserGroup original, UserGroup newUserGroup)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_USER_GROUP);
		
		return operation.run(original, newUserGroup);
	}
	
	@Override
	public Boolean deleteUserGroup(UserGroup userGroup)
	{
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_USER_GROUP);
		
		return operation.run(userGroup);
	}
}