package share.app.invoices;

import share.core.Date;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.invoices.Operations:";

	public static final String GET_INVOICES = IOperations.BASE_OPERATIONS + "getInvoices";
	public static final String ADD_INVOICE = IOperations.BASE_OPERATIONS + "addInvoice";
	public static final String EDIT_INVOICE = IOperations.BASE_OPERATIONS + "editInvoice";
	public static final String DELETE_INVOICE = IOperations.BASE_OPERATIONS + "deleteInvoice";
	
	public static final String GET_INVOICE_DETAIL = IOperations.BASE_OPERATIONS + "getInvoiceDetail";
	public static final String ADD_INVOICE_DETAIL = IOperations.BASE_OPERATIONS + "addInvoiceDetail";
	public static final String EDIT_INVOICE_DETAIL = IOperations.BASE_OPERATIONS + "editInvoiceDetail";
	public static final String DELETE_INVOICE_DETAIL = IOperations.BASE_OPERATIONS + "deleteInvoiceDetail";
	
	public Invoice[] getInvoices(Integer clientID, Date dateParam);
	
	public Invoice addInvoice(Invoice invoice);
	
	public Boolean editInvoice(Invoice original, Invoice newInvoice);
	
	public Boolean deleteInvoice(Invoice invoice);
	
	public InvoiceDetail[] getInvoiceDetail(Integer invoiceID);
	
	public Boolean addInvoiceDetail(InvoiceDetail invoiceDetail);
	
	public Boolean editInvoiceDetail(InvoiceDetail original, InvoiceDetail newInvoiceDetail);
	
	public Boolean deleteInvoiceDetail(InvoiceDetail invoiceDetail);
}