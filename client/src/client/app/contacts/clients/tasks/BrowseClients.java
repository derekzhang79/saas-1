package client.app.contacts.clients.tasks;

import share.app.contacts.clients.Client;
import client.app.contacts.clients.gui.def.GUIBrowseClients;
import client.app.contacts.clients.operations.OperationsClients;
import client.core.gui.taks.OptionTask;

public class BrowseClients extends OptionTask<Void>
{
	private final GUIBrowseClients gui = new GUIBrowseClients();
	
	public BrowseClients()
	{
		super(GUIBrowseClients.PATH, TaskType.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshClients();
	}
	
	private void refreshClients()
	{
		this.gui.list.setRows(OperationsClients.call().getClients());
	}
	
	private void addClient()
	{
		AddClient task = new AddClient();
		Client response = task.run();
		
		if (response != null)
		{
			refreshClients();
		}
		
		this.gui.list.focus();
	}
	
	private void editClient()
	{
		if (this.gui.list.isRowSelected())
		{
			Client current = (Client)this.gui.list.getCurrentRow();
			EditClient task = new EditClient(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshClients();
			}
		}
		else
		{
			showWarning(GUIBrowseClients.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteClient()
	{
		if (this.gui.list.isRowSelected())
		{
			Client current = (Client)this.gui.list.getCurrentRow();
			DeleteClient task = new DeleteClient(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshClients();
			}
		}
		else
		{
			showWarning(GUIBrowseClients.Literals.ROW_NOT_SELECTED);
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
			case ADD:
				addClient();
				break;
			
			case EDIT:
				editClient();
				break;
			
			case DELETE:
				deleteClient();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseClients.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseClients.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}