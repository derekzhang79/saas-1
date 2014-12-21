package client.app.invoices.tasks;

import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import client.app.invoices.gui.def.GUIBrowseInvoiceDetail;
import client.app.invoices.operations.OperationsInvoices;
import client.core.gui.taks.OptionTask;

public class BrowseInvoiceDetail extends OptionTask<Void> {
	
	private GUIBrowseInvoiceDetail gui = new GUIBrowseInvoiceDetail();
	
	private Invoice invoice = null;
	
	public BrowseInvoiceDetail(Invoice invoice) {
		super(GUIBrowseInvoiceDetail.PATH, TaskType.MODAL);
		
		this.invoice = invoice;
	}
	
	public void start() {
		setGUI(this.gui);
		refreshInvoiceDetail();
	}
	
	private void refreshInvoiceDetail() {
		this.gui.list.setRows(OperationsInvoices.call().getInvoiceDetail(this.invoice.id));
	}
	
	private void addInvoiceDetail() {
		AddInvoiceDetail task = new AddInvoiceDetail(this.invoice.id);
		Boolean response = task.run();
		
		if (valid(response)) {
			refreshInvoiceDetail();
		}
		
		this.gui.list.focus();
	}
	
	private void editInvoiceDetail() {
		if (this.gui.list.isRowSelected()) {
			
			InvoiceDetail current = (InvoiceDetail)this.gui.list.getCurrentRow();
			EditInvoiceDetail task = new EditInvoiceDetail(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshInvoiceDetail();
			}
			
		} else {
			showWarning(GUIBrowseInvoiceDetail.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteInvoiceDetail() {
		if (this.gui.list.isRowSelected()) {
			
			InvoiceDetail current = (InvoiceDetail)this.gui.list.getCurrentRow();
			DeleteInvoiceDetail task = new DeleteInvoiceDetail(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshInvoiceDetail();
			}
			
		} else {
			showWarning(GUIBrowseInvoiceDetail.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addInvoiceDetail();
				break;
			
			case EDIT:
				editInvoiceDetail();
				break;
			
			case DELETE:
				deleteInvoiceDetail();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseInvoiceDetail.Literals.LIST_PDF) + " " + this.invoice.id, this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseInvoiceDetail.Literals.LIST_PDF) + " " + this.invoice.id, this.gui.list);
				break;
			
			default:
				break;
		}
	}
}