package client.app.workshop.tasks;

import share.app.contacts.clients.Client;
import share.app.dictionary.Category;
import share.app.dictionary.Category.Categories;
import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import client.app.contacts.clients.operations.OperationsClients;
import client.app.contacts.clients.tasks.SearchClient;
import client.app.system.dictionary.DictionaryManager;
import client.app.workshop.gui.def.GUIBrowseFixOrder;
import client.app.workshop.operations.OperationsWorkshop;
import client.app.workshop.reports.PrintFixOrder;
import client.core.gui.taks.Activity;

public class BrowseFixOrder extends Activity<Void>
{
	private final GUIBrowseFixOrder gui = new GUIBrowseFixOrder();
	
	private int clientID = 0;
	
	public BrowseFixOrder()
	{
		super(GUIBrowseFixOrder.PATH, Type.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.status.setItems(DictionaryManager.get(Categories.FIX_ORDER_STATUS));
		this.gui.status.set(Category.FIX_ORDER_STATUS.RECEIVED);
		refreshFixOrders();
	}
	
	private void refreshFixOrders()
	{
		this.gui.list.setRows(OperationsWorkshop.call().getFixOrders(this.clientID, this.gui.status.get()));
	}
	
	private void addFixOrder()
	{
		AddFixOrder task = new AddFixOrder();
		FixOrder response = task.run();
		
		if (response != null)
		{
			refreshFixOrders();
			
			BrowseFixOrderDetail browse = new BrowseFixOrderDetail(response);
			browse.run();
			
			refreshFixOrders();
		}
		
		this.gui.list.focus();
	}
	
	private void editFixOrder()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrder current = (FixOrder)this.gui.list.getCurrentRow();
			EditFixOrder task = new EditFixOrder(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshFixOrders();
			}
		}
		else
		{
			showWarning(GUIBrowseFixOrder.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteFixOrder()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrder current = (FixOrder)this.gui.list.getCurrentRow();
			DeleteFixOrder task = new DeleteFixOrder(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshFixOrders();
			}
		}
		else
		{
			showWarning(GUIBrowseFixOrder.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void fixOrderDetail()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrder current = (FixOrder)this.gui.list.getCurrentRow();
			
			BrowseFixOrderDetail task = new BrowseFixOrderDetail(current);
			task.run();
			refreshFixOrders();
			
			this.gui.list.focus();
		}
		else
		{
			showWarning(GUIBrowseFixOrder.Literals.ROW_NOT_SELECTED);
			this.gui.list.focus();
		}
	}
	
	private void searchClient()
	{
		SearchClient task = new SearchClient();
		Client client = task.run();
		
		if (client != null)
		{
			this.clientID = client.id;
			this.gui.clientName.set(client.name);
		}
		
		this.gui.clientName.focus();
	}
	
	private void clearSearchClient()
	{
		this.clientID = 0;
		this.gui.clientName.clear();
	}
	
	private void clean()
	{
		this.gui.list.cleanSearch();
	}
	
	private void print()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrder current = (FixOrder)this.gui.list.getCurrentRow();
			FixOrderDetail[] details = OperationsWorkshop.call().getFixOrdersDetail(current.id);
			Client client = OperationsClients.call().getClient(current.client);
			
			PrintFixOrder print = new PrintFixOrder(current, details, client);
			print.show();
		}
		else
		{
			showWarning(GUIBrowseFixOrder.Literals.ROW_NOT_SELECTED);
			this.gui.list.focus();
		}
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case DETAIL:
				fixOrderDetail();
				break;
			
			case ADD:
				addFixOrder();
				break;
			
			case EDIT:
				editFixOrder();
				break;
			
			case DELETE:
				deleteFixOrder();
				break;
			
			case PRINT:
				print();
				break;
			
			case SEARCH_CLIENT:
				searchClient();
				break;
			
			case CLEAR_SEARCH_CLIENT:
				clearSearchClient();
				break;
			
			case SEARCH:
				refreshFixOrders();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseFixOrder.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseFixOrder.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}