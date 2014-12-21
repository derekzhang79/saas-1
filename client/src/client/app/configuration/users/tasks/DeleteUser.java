package client.app.configuration.users.tasks;

import share.core.objects.User;
import client.app.configuration.users.gui.def.GUIDeleteUser;
import client.app.configuration.users.operations.OperationsUsers;
import client.core.gui.taks.OptionTask;

public class DeleteUser extends OptionTask<Boolean> {
	
	private User user = null;
	
	public DeleteUser(User user) {
		super(GUIDeleteUser.PATH, TaskType.SINGLE);
		
		this.user = user;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteUser.Literals.ASK_DELETE, this.user.name))) {
			
			Boolean response = OperationsUsers.call().deleteUser(this.user);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}