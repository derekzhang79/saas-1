package client.app.invoices.tasks;

import share.app.contacts.clients.Client;
import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import share.app.taxes.Tax;
import client.app.contacts.clients.operations.OperationsClients;
import client.app.contacts.clients.tasks.SearchClient;
import client.app.invoices.gui.def.GUIBrowseInvoices;
import client.app.invoices.operations.OperationsInvoices;
import client.app.invoices.reports.PrintInvoice;
import client.app.taxes.operations.OperationsTaxes;
import client.core.gui.taks.OptionTask;

public class BrowseInvoices extends OptionTask<Void> {
	
	private GUIBrowseInvoices gui = new GUIBrowseInvoices();
	
	private int clientID = 0;
	
	public BrowseInvoices() {
		super(GUIBrowseInvoices.PATH, TaskType.SINGLE);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshInvoices();
	}
	
	private void refreshInvoices() {
		if (this.gui.date.isEmpty()) {
			this.gui.date.clear();
		}
		
		this.gui.list.setRows(OperationsInvoices.call().getInvoices(this.clientID, this.gui.date.get()));
	}
	
	private void addInvoice() {
		AddInvoice task = new AddInvoice();
		Invoice response = task.run();
		
		if (response != null) {
			refreshInvoices();
			
			BrowseInvoiceDetail browse = new BrowseInvoiceDetail(response);
			browse.run();
			
			refreshInvoices();
		}
		
		this.gui.list.focus();
	}
	
	private void editInvoice() {
		if (this.gui.list.isRowSelected()) {
			
			Invoice current = (Invoice)this.gui.list.getCurrentRow();
			EditInvoice task = new EditInvoice(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshInvoices();
			}
			
		} else {
			showWarning(GUIBrowseInvoices.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteInvoice() {
		if (this.gui.list.isRowSelected()) {
			
			Invoice current = (Invoice)this.gui.list.getCurrentRow();
			DeleteInvoice task = new DeleteInvoice(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshInvoices();
			}
			
		} else {
			showWarning(GUIBrowseInvoices.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void invoiceDetail() {
		if (this.gui.list.isRowSelected()) {
			
			Invoice current = (Invoice)this.gui.list.getCurrentRow();
			
			BrowseInvoiceDetail task = new BrowseInvoiceDetail(current);
			task.run();
			refreshInvoices();
			
			this.gui.list.focus();
			
		} else {
			showWarning(GUIBrowseInvoices.Literals.ROW_NOT_SELECTED);
			this.gui.list.focus();
		}
	}
	
	private void searchClient() {
		SearchClient task = new SearchClient();
		Client client = task.run();
		
		if (client != null) {
			this.clientID = client.id;
			this.gui.clientName.set(client.name);
		}
		
		this.gui.clientName.focus();
	}
	
	private void clearSearchClient() {
		this.clientID = 0;
		this.gui.clientName.clear();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	private void print() {
		if (this.gui.list.isRowSelected()) {
			
			Invoice current = (Invoice)this.gui.list.getCurrentRow();
			InvoiceDetail[] details = OperationsInvoices.call().getInvoiceDetail(current.id);
			Client client = OperationsClients.call().getClient(current.client);
			Tax[] taxes = OperationsTaxes.call().getTaxesFrom(current.date);
			
			PrintInvoice print = new PrintInvoice(current, details, client, taxes);
			print.show();
			
			this.gui.list.focus();
			
		} else {
			showWarning(GUIBrowseInvoices.Literals.ROW_NOT_SELECTED);
			this.gui.list.focus();
		}
	}
	
	public void event(Event event) {
		switch (event) {
		
			case DETAIL:
				invoiceDetail();
				break;
			
			case ADD:
				addInvoice();
				break;
			
			case EDIT:
				editInvoice();
				break;
			
			case DELETE:
				deleteInvoice();
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
				refreshInvoices();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseInvoices.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseInvoices.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}