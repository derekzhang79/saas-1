package client.app.configuration.users.tasks;

import share.core.objects.User;
import client.app.configuration.users.gui.def.GUIBrowseUsers;
import client.app.configuration.users.operations.OperationsUsers;
import client.core.gui.OptionTask;

public class BrowseUsers extends OptionTask<Void> {
	
	private GUIBrowseUsers gui = new GUIBrowseUsers();
	
	public BrowseUsers() {
		super(GUIBrowseUsers.PATH, TaskType.SINGLE);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshUsers();
	}
	
	private void refreshUsers() {
		this.gui.list.setRows(OperationsUsers.call().getUsers());
	}
	
	private void addUser() {
		AddUser task = new AddUser();
		Boolean response = task.run();
		
		if (valid(response)) {
			refreshUsers();
		}
		
		this.gui.list.focus();
	}
	
	private void editUser() {
		if (this.gui.list.isRowSelected()) {
			
			User current = (User)this.gui.list.getCurrentRow();
			EditUser task = new EditUser(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshUsers();
			}
			
		} else {
			showWarning(GUIBrowseUsers.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteUser() {
		if (this.gui.list.isRowSelected()) {
			
			User current = (User)this.gui.list.getCurrentRow();
			DeleteUser task = new DeleteUser(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshUsers();
			}
			
		} else {
			showWarning(GUIBrowseUsers.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addUser();
				break;
			
			case EDIT:
				editUser();
				break;
			
			case DELETE:
				deleteUser();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseUsers.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseUsers.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}