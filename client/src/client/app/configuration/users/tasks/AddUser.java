package client.app.configuration.users.tasks;

import java.awt.Color;
import share.core.objects.User;
import share.core.objects.UserGroup;
import share.core.utils.Encoding;
import client.app.configuration.usergroups.tasks.SearchUserGroup;
import client.app.configuration.users.gui.def.GUIAddUser;
import client.app.configuration.users.operations.OperationsUsers;
import client.core.gui.taks.OptionTask;

public class AddUser extends OptionTask<Boolean>
{
	private final GUIAddUser gui = new GUIAddUser();
	
	private int userGroupID = 0;
	
	public AddUser()
	{
		super(GUIAddUser.PATH, TaskType.MODAL);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.name.focus();
	}
	
	private void addUser()
	{
		if (validate())
		{
			User newUser = new User(0, this.gui.name.get(), Encoding.md5(this.gui.password.get()), this.userGroupID);
			boolean response = OperationsUsers.call().addUser(newUser);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIAddUser.Literals.USER_NOT_CREATED);
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
			showWarning(GUIAddUser.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		}
		else if (this.gui.groupName.isEmpty())
		{
			showWarning(GUIAddUser.Literals.GROUP_REQUIRED);
			this.gui.groupName.focus();
			this.gui.groupName.setBorderColor(Color.RED);
		}
		else if (this.gui.password.isEmpty())
		{
			showWarning(GUIAddUser.Literals.PASSWORD_REQUIRED);
			this.gui.password.focus();
			this.gui.password.setBorderColor(Color.RED);
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
			this.gui.groupName.set(userGroup.name);
		}
		
		this.gui.groupName.focus();
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIAddUser.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.name.isEmpty()) || (!this.gui.groupName.isEmpty()) || (!this.gui.password.isEmpty()));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addUser();
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