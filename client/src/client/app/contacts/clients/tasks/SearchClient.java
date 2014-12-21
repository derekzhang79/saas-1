package client.app.contacts.clients.tasks;

import share.app.contacts.clients.Client;
import client.app.contacts.clients.gui.def.GUISearchClient;
import client.app.contacts.clients.operations.OperationsClients;
import client.core.gui.OptionTask;

public class SearchClient extends OptionTask<Client> {
	
	private GUISearchClient gui = new GUISearchClient();
	
	public SearchClient() {
		super(GUISearchClient.PATH, TaskType.MODAL);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshClients();
	}
	
	private void refreshClients() {
		this.gui.list.setRows(OperationsClients.call().getClients());
	}
	
	private void select() {
		close((Client)this.gui.list.getCurrentRow());
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	private void addClient() {
		AddClient task = new AddClient();
		Client response = task.run();
		
		if (response != null) {
			close(response);
		}
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addClient();
				break;
			
			case SELECT:
				select();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUISearchClient.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUISearchClient.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}