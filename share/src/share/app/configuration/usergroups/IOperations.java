package share.app.configuration.usergroups;

import share.core.objects.UserGroup;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.configuration.usergroups.Operations:";
	public static final String GET_USER_GROUPS = IOperations.BASE_OPERATIONS + "getUserGroups";
	public static final String ADD_USER_GROUP = IOperations.BASE_OPERATIONS + "addUserGroup";
	public static final String EDIT_USER_GROUP = IOperations.BASE_OPERATIONS + "editUserGroup";
	public static final String DELETE_USER_GROUP = IOperations.BASE_OPERATIONS + "deleteUserGroup";
	
	public UserGroup[] getUserGroups();
	
	public UserGroup addUserGroup(UserGroup userGroup);
	
	public Boolean editUserGroup(UserGroup original, UserGroup newUserGroup);
	
	public Boolean deleteUserGroup(UserGroup userGroup);
}