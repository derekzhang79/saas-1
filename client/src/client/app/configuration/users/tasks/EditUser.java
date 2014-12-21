package client.app.configuration.users.tasks;

import java.awt.Color;
import share.core.objects.User;
import share.core.objects.UserGroup;
import share.core.utils.Encoding;
import client.app.configuration.usergroups.tasks.SearchUserGroup;
import client.app.configuration.users.gui.def.GUIEditUser;
import client.app.configuration.users.operations.OperationsUsers;
import client.core.gui.taks.OptionTask;
import client.core.profile.Profile;

public class EditUser extends OptionTask<Boolean>
{
	private final GUIEditUser gui = new GUIEditUser();
	
	private final User original;
	private int userGroupID = 0;
	private boolean isGroupAdministrator = false;
	
	public EditUser(User original)
	{
		super(GUIEditUser.PATH, TaskType.MODAL);
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.userGroupID = this.original.userGroup;
		this.gui.name.set(this.original.name);
		this.gui.groupName.set(this.original.groupName);
		this.gui.name.focus();
	}
	
	private void editUser()
	{
		if (validate())
		{
			String newPassword = this.gui.password.get();
			
			if (!newPassword.isEmpty())
			{
				newPassword = Encoding.md5(newPassword);
			}
			
			User newUser = new User(0, this.gui.name.get(), newPassword, this.userGroupID);
			boolean response = OperationsUsers.call().editUser(this.original, newUser);
			
			if (response)
			{
				if (this.original.id == Profile.getUserID())
				{
					Profile.setUserName(this.gui.name.get());
					Profile.setUserPassword(Encoding.md5(this.gui.password.get()));
					Profile.setAdministrator(this.isGroupAdministrator);
				}
				
				close(true);
			}
			else
			{
				showWarning(GUIEditUser.Literals.USER_NOT_EDITED);
				this.gui.name.focus();
			}
		}
	}
	
	private boolean validate()
	{
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.name.isEmpty())
		{
			showWarning(GUIEditUser.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		}
		else if (this.gui.groupName.isEmpty())
		{
			showWarning(GUIEditUser.Literals.GROUP_REQUIRED);
			this.gui.groupName.focus();
			this.gui.groupName.setBorderColor(Color.RED);
		}
		else
		{
			valid = true;
		}
		
		return valid;
	}
	
	private void searchUserGroup()
	{
		SearchUserGroup task = new SearchUserGroup();
		UserGroup userGroup = task.run();
		
		if (userGroup != null)
		{
			this.userGroupID = userGroup.id;
			this.isGroupAdministrator = userGroup.administrator;
			this.gui.groupName.set(userGroup.name);
		}
		
		this.gui.groupName.focus();
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditUser.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.name.equals(this.original.name)) || (!this.gui.groupName.equals(this.original.groupName)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editUser();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_USER_GROUP:
				searchUserGroup();
				break;
			
			default:
				break;
		}
	}
}