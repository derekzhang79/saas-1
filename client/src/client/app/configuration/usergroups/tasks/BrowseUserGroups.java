package client.app.configuration.usergroups.tasks;

import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUIBrowseUserGroups;
import client.app.configuration.usergroups.operations.OperationsUserGroups;
import client.core.gui.taks.OptionTask;

public class BrowseUserGroups extends OptionTask<Void>
{
	private final GUIBrowseUserGroups gui = new GUIBrowseUserGroups();
	
	public BrowseUserGroups()
	{
		super(GUIBrowseUserGroups.PATH, TaskType.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshUserGroups();
	}
	
	private void refreshUserGroups()
	{
		this.gui.list.setRows(OperationsUserGroups.call().getUserGroups());
	}
	
	private void addUserGroup()
	{
		AddUserGroup task = new AddUserGroup();
		UserGroup response = task.run();
		
		if (response != null)
		{
			refreshUserGroups();
			
			if (!response.administrator)
			{
				EditUserGroupPermissions editUserGroup = new EditUserGroupPermissions(response);
				editUserGroup.run();
				
				refreshUserGroups();
			}
		}
		
		this.gui.list.focus();
	}
	
	private void editUserGroup()
	{
		if (this.gui.list.isRowSelected())
		{
			UserGroup current = (UserGroup)this.gui.list.getCurrentRow();
			EditUserGroup task = new EditUserGroup(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshUserGroups();
			}
		}
		else
		{
			showWarning(GUIBrowseUserGroups.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteUserGroup()
	{
		if (this.gui.list.isRowSelected())
		{
			UserGroup current = (UserGroup)this.gui.list.getCurrentRow();
			DeleteUserGroup task = new DeleteUserGroup(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshUserGroups();
			}
		}
		else
		{
			showWarning(GUIBrowseUserGroups.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void editUserGroupPermissions()
	{
		if (this.gui.list.isRowSelected())
		{
			UserGroup current = (UserGroup)this.gui.list.getCurrentRow();
			
			if (!current.administrator)
			{
				EditUserGroupPermissions task = new EditUserGroupPermissions(current);
				task.run();
			}
			else
			{
				showWarning(GUIBrowseUserGroups.Literals.IS_ADMINISTRATOR_GROUP);
			}
		}
		else
		{
			showWarning(GUIBrowseUserGroups.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean()
	{
		this.gui.list.cleanSearch();
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case DETAIL:
				editUserGroupPermissions();
				break;
			
			case ADD:
				addUserGroup();
				break;
			
			case EDIT:
				editUserGroup();
				break;
			
			case DELETE:
				deleteUserGroup();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseUserGroups.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseUserGroups.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}