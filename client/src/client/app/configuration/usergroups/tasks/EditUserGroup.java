package client.app.configuration.usergroups.tasks;

import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUIEditUserGroup;
import client.app.configuration.usergroups.operations.OperationsUserGroups;
import client.core.profile.Profile;

public class EditUserGroup extends BaseUserGroup<Boolean>
{
	private final UserGroup original;

	public EditUserGroup(UserGroup original)
	{
		this.original = original;
	}

	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditUserGroup.Literals.TITLE_EDIT_USER_GROUP));
		this.gui.name.set(this.original.name);
		this.gui.administrator.set(this.original.administrator);
		this.gui.name.focus();
	}

	private void editUserGroup()
	{
		if (validate())
		{
			UserGroup newUserGroup = new UserGroup(0, this.gui.name.get(), this.gui.administrator.get(), Profile.getClientID());
			boolean response = OperationsUserGroups.call().editUserGroup(this.original, newUserGroup);

			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditUserGroup.Literals.USER_GROUP_NOT_EDITED);
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
		return (!this.gui.name.equals(this.original.name)) || (!this.gui.administrator.equals(this.original.administrator));
	}

	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editUserGroup();
				break;

			case CANCEL:
				closing();
				break;

			default:
				break;
		}
	}
}