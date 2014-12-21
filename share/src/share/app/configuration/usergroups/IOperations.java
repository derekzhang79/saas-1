package share.app.configuration.usergroups;

import share.core.objects.UserGroup;

public interface IOperations
{
	public static final String GET_USER_GROUPS = "server.app.configuration.usergroups.Operations:getUserGroups";
	public static final String ADD_USER_GROUP = "server.app.configuration.usergroups.Operations:addUserGroup";
	public static final String EDIT_USER_GROUP = "server.app.configuration.usergroups.Operations:editUserGroup";
	public static final String DELETE_USER_GROUP = "server.app.configuration.usergroups.Operations:deleteUserGroup";

	public UserGroup[] getUserGroups();

	public UserGroup addUserGroup(UserGroup userGroup);

	public Boolean editUserGroup(UserGroup original, UserGroup newUserGroup);

	public Boolean deleteUserGroup(UserGroup userGroup);
}