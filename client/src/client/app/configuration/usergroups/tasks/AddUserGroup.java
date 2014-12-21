package client.app.configuration.usergroups.tasks;

import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUIEditUserGroup;
import client.app.configuration.usergroups.operations.OperationsUserGroups;
import client.core.profile.Profile;

public class AddUserGroup extends BaseUserGroup<UserGroup>
{
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditUserGroup.Literals.TITLE_ADD_USER_GROUP));
		this.gui.name.focus();
	}
	
	private void addUserGroup()
	{
		if (validate())
		{
			UserGroup newUserGroup = new UserGroup(0, this.gui.name.get(), this.gui.administrator.get(), Profile.getClientID());
			UserGroup response = OperationsUserGroups.call().addUserGroup(newUserGroup);
			
			if (response != null)
			{
				close(response);
			}
			else
			{
				showWarning(GUIEditUserGroup.Literals.USER_GROUP_NOT_CREATED);
				this.gui.name.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditUserGroup.Literals.ASK_CLOSE_WINDOW))
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
		return (!this.gui.name.isEmpty()) || (this.gui.administrator.get());
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addUserGroup();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}