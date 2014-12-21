package server.app.invoices;

import java.sql.Connection;

import server.app.db.tables.TableInvoice;
import server.app.db.tables.TableInvoiceDetail;
import server.core.ServerOperation;
import share.app.invoices.IOperations;
import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import share.core.Date;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public Invoice[] getInvoices(Integer clientID, Date dateParam) {
		TableInvoice table = new TableInvoice(getConnection());
		
		return table.getInvoices(clientID, dateParam);
	}
	
	public Invoice addInvoice(Invoice invoice) {
		TableInvoice table = new TableInvoice(getConnection());
		
		return table.add(invoice);
	}
	
	public Boolean editInvoice(Invoice original, Invoice newInvoice) {
		TableInvoice table = new TableInvoice(getConnection());
		
		return table.edit(original, newInvoice);
	}
	
	public Boolean deleteInvoice(Invoice invoice) {
		TableInvoice table = new TableInvoice(getConnection());
		
		return table.delete(invoice);
	}
	
	public InvoiceDetail[] getInvoiceDetail(Integer invoiceID) {
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		
		return table.getInvoiceDetail(invoiceID);
	}
	
	public Boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		
		return table.add(invoiceDetail);
	}
	
	public Boolean editInvoiceDetail(InvoiceDetail original, InvoiceDetail newInvoiceDetail) {
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		
		return table.edit(original, newInvoiceDetail);
	}
	
	public Boolean deleteInvoiceDetail(InvoiceDetail invoiceDetail) {
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		
		return table.delete(invoiceDetail);
	}
}