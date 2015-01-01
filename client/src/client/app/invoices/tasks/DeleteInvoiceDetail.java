package client.app.invoices.tasks;

import share.app.invoices.InvoiceDetail;
import client.app.invoices.gui.def.GUIDeleteInvoiceDetail;
import client.app.invoices.operations.OperationsInvoices;
import client.core.gui.taks.Activity;

public class DeleteInvoiceDetail extends Activity<Boolean>
{
	private final InvoiceDetail invoiceDetail;

	public DeleteInvoiceDetail(InvoiceDetail invoiceDetail)
	{
		super(GUIDeleteInvoiceDetail.PATH, Type.SINGLE);

		this.invoiceDetail = invoiceDetail;
	}

	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteInvoiceDetail.Literals.ASK_DELETE, this.invoiceDetail.productDescription)))
		{
			Boolean response = OperationsInvoices.call().deleteInvoiceDetail(this.invoiceDetail);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}