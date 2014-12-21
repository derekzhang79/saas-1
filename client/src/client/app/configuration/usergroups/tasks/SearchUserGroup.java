package client.app.configuration.usergroups.tasks;

import share.core.objects.UserGroup;
import client.app.configuration.usergroups.gui.def.GUISearchUserGroups;
import client.app.configuration.usergroups.operations.OperationsUserGroups;
import client.core.gui.OptionTask;

public class SearchUserGroup extends OptionTask<UserGroup> {
	
	private GUISearchUserGroups gui = new GUISearchUserGroups();
	
	public SearchUserGroup() {
		super(GUISearchUserGroups.PATH, TaskType.MODAL);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshUserGroups();
	}
	
	private void refreshUserGroups() {
		this.gui.list.setRows(OperationsUserGroups.call().getUserGroups());
	}
	
	private void select() {
		close((UserGroup)this.gui.list.getCurrentRow());
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	private void addUserGroup() {
		AddUserGroup task = new AddUserGroup();
		UserGroup response = task.run();
		
		if (response != null) {
			close(response);
		}
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addUserGroup();
				break;
			
			case SELECT:
				select();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUISearchUserGroups.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUISearchUserGroups.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}