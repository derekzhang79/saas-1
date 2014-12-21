package client.app.configuration.usergroups.tasks;

import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUIDeleteUserGroup;
import client.app.configuration.usergroups.operations.OperationsUserGroups;
import client.core.gui.OptionTask;

public class DeleteUserGroup extends OptionTask<Boolean> {
	
	private UserGroup userGroup = null;
	
	public DeleteUserGroup(UserGroup userGroup) {
		super(GUIDeleteUserGroup.PATH, TaskType.SINGLE);
		
		this.userGroup = userGroup;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteUserGroup.Literals.ASK_DELETE, this.userGroup.name))) {
			
			Boolean response = OperationsUserGroups.call().deleteUserGroup(this.userGroup);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}