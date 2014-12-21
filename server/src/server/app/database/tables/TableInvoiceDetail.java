package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.invoices.InvoiceDetail;
import share.core.Date;

public class TableInvoiceDetail extends Table
{
	public Integer id = new Integer(0);
	public Integer invoice = new Integer(0);
	public Integer line = new Integer(0);
	public Integer product = new Integer(0);
	public Double quantity = new Double(0);
	public String tax = new String();
	public Double price = new Double(0);
	
	public TableInvoiceDetail(Connection connection)
	{
		super(connection, "INVOICE_DETAIL");
		setTable(this);
	}
	
	public InvoiceDetail[] getInvoiceDetail(Integer invoiceID)
	{
		this.invoice = invoiceID;
		
		Date date = getInvoiceDate(invoiceID);
		
		int number = search("line");
		
		InvoiceDetail[] result = new InvoiceDetail[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			result[i] = new InvoiceDetail(this.id, this.invoice, this.line, this.product, getProductDescription(this.product), this.quantity, this.tax, getTaxValue(this.tax, date), this.price);
		}
		
		return result;
	}
	
	public boolean add(InvoiceDetail invoiceDetail)
	{
		this.id = invoiceDetail.id;
		this.invoice = invoiceDetail.invoice;
		this.line = nextLine(invoiceDetail.invoice);
		this.product = invoiceDetail.product;
		this.quantity = invoiceDetail.quantity;
		this.tax = invoiceDetail.tax;
		this.price = invoiceDetail.price;
		
		return create();
	}
	
	public boolean edit(InvoiceDetail original, InvoiceDetail newInvoiceDetail)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			this.product = newInvoiceDetail.product;
			this.quantity = newInvoiceDetail.quantity;
			this.tax = newInvoiceDetail.tax;
			this.price = newInvoiceDetail.price;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(InvoiceDetail invoiceDetail)
	{
		boolean valid = false;
		
		this.id = invoiceDetail.id;
		
		if (read())
		{
			valid = delete();
		}
		
		return valid;
	}
	
	public boolean deleteAll(Integer invoiceID)
	{
		this.invoice = invoiceID;
		
		Date date = getInvoiceDate(invoiceID);
		
		int number = search();
		int deleted = 0;
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
			InvoiceDetail invoiceDetail = new InvoiceDetail(this.id, this.invoice, this.line, this.product, getProductDescription(this.product), this.quantity, this.tax, getTaxValue(this.tax, date), this.price);
			
			if (table.delete(invoiceDetail))
			{
				deleted++;
			}
		}
		
		return (number == deleted);
	}
	
	public String getProductDescription(int productID)
	{
		String result = "";
		
		TableProduct table = new TableProduct(getConnection());
		table.id = productID;
		
		if (table.read())
		{
			result = table.name;
		}
		
		return result;
	}
	
	public double getTotalAmount(int invoiceID)
	{
		double result = 0;
		
		this.invoice = invoiceID;
		
		Date date = getInvoiceDate(invoiceID);
		
		int number = search();
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			double taxValue = getTaxValue(this.tax, date);
			
			result += (this.price + (this.price * (taxValue / 100))) * this.quantity;
		}
		
		return result;
	}
	
	private int nextLine(int invoiceID)
	{
		int result = 1;
		
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		table.invoice = invoiceID;
		
		int rows = table.search("line DESC");
		
		if (rows > 0)
		{
			table.select(0);
			result = table.line + 1;
		}
		
		return result;
	}
	
	private Date getInvoiceDate(int invoiceID)
	{
		TableInvoice table = new TableInvoice(getConnection());
		
		return table.getDate(invoiceID);
	}
	
	private double getTaxValue(String type, Date date)
	{
		TableTax table = new TableTax(getConnection());
		
		return table.getTaxValue(type, date);
	}
}