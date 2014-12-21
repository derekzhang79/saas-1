package client.app.invoices.operations;

import share.app.invoices.IOperations;
import share.app.invoices.Invoice;
import share.app.invoices.InvoiceDetail;
import share.core.objects.Date;
import client.core.operations.Operation;

public class OperationsInvoices implements IOperations {
	
	private static OperationsInvoices instance = new OperationsInvoices();
	
	public static OperationsInvoices call() {
		return OperationsInvoices.instance;
	}
	
	public Invoice[] getInvoices(Integer clientID, Date dateParam) {
		Operation<Invoice[]> operation = new Operation<Invoice[]>(IOperations.GET_INVOICES);
		
		return operation.run(clientID, dateParam);
	}
	
	public Invoice addInvoice(Invoice invoice) {
		Operation<Invoice> operation = new Operation<Invoice>(IOperations.ADD_INVOICE);
		
		return operation.run(invoice);
	}
	
	public Boolean editInvoice(Invoice original, Invoice newInvoice) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_INVOICE);
		
		return operation.run(original, newInvoice);
	}
	
	public Boolean deleteInvoice(Invoice invoice) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_INVOICE);
		
		return operation.run(invoice);
	}
	
	public InvoiceDetail[] getInvoiceDetail(Integer invoiceID) {
		Operation<InvoiceDetail[]> operation = new Operation<InvoiceDetail[]>(IOperations.GET_INVOICE_DETAIL);
		
		return operation.run(invoiceID);
	}
	
	public Boolean addInvoiceDetail(InvoiceDetail invoiceDetail) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_INVOICE_DETAIL);
		
		return operation.run(invoiceDetail);
	}
	
	public Boolean editInvoiceDetail(InvoiceDetail original, InvoiceDetail newInvoiceDetail) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_INVOICE_DETAIL);
		
		return operation.run(original, newInvoiceDetail);
	}
	
	public Boolean deleteInvoiceDetail(InvoiceDetail invoiceDetail) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_INVOICE_DETAIL);
		
		return operation.run(invoiceDetail);
	}
}