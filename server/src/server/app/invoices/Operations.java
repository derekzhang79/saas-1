package server.app.invoices;

import java.sql.Connection;
import server.app.database.tables.TableInvoice;
import server.app.database.tables.TableInvoiceDetail;
import server.core.connection.ServerOperation;
import share.app.invoices.IOperations;
import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import share.core.objects.Date;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Invoice[] getInvoices(Integer clientID, Date dateParam)
	{
		TableInvoice table = new TableInvoice(getConnection());

		return table.getInvoices(clientID, dateParam);
	}

	@Override
	public Invoice addInvoice(Invoice invoice)
	{
		TableInvoice table = new TableInvoice(getConnection());

		return table.add(invoice);
	}

	@Override
	public Boolean editInvoice(Invoice original, Invoice newInvoice)
	{
		TableInvoice table = new TableInvoice(getConnection());

		return table.edit(original, newInvoice);
	}

	@Override
	public Boolean deleteInvoice(Invoice invoice)
	{
		TableInvoice table = new TableInvoice(getConnection());

		return table.delete(invoice);
	}

	@Override
	public InvoiceDetail[] getInvoiceDetail(Integer invoiceID)
	{
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());

		return table.getInvoiceDetail(invoiceID);
	}

	@Override
	public Boolean addInvoiceDetail(InvoiceDetail invoiceDetail)
	{
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());

		return table.add(invoiceDetail);
	}

	@Override
	public Boolean editInvoiceDetail(InvoiceDetail original, InvoiceDetail newInvoiceDetail)
	{
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());

		return table.edit(original, newInvoiceDetail);
	}

	@Override
	public Boolean deleteInvoiceDetail(InvoiceDetail invoiceDetail)
	{
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());

		return table.delete(invoiceDetail);
	}
}