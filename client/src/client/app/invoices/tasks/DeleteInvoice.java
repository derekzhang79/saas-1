package client.app.invoices.tasks;

import share.app.invoices.Invoice;
import client.app.invoices.gui.def.GUIDeleteInvoice;
import client.app.invoices.operations.OperationsInvoices;
import client.core.gui.taks.OptionTask;

public class DeleteInvoice extends OptionTask<Boolean> {
	
	private Invoice invoice = null;
	
	public DeleteInvoice(Invoice invoice) {
		super(GUIDeleteInvoice.PATH, TaskType.SINGLE);
		
		this.invoice = invoice;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteInvoice.Literals.ASK_DELETE, this.invoice.getDateString()))) {
			
			Boolean response = OperationsInvoices.call().deleteInvoice(this.invoice);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}