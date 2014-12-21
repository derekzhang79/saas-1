package server.app.db.tables;

import java.sql.Connection;
import server.core.db.Table;
import server.core.db.tables.TableDictionary;
import share.app.dictionary.Category.Categories;
import share.app.invoices.Invoice;
import share.core.Date;

public class TableInvoice extends Table
{
	public Integer id = new Integer(0);
	public Integer number = new Integer(0);
	public Date date = new Date();
	public Integer client = new Integer(0);
	public Double discount = new Double(0);
	public String payment_method = new String();
	public String comments = new String();
	
	public TableInvoice(Connection connection)
	{
		super(connection, "INVOICE");
		setTable(this);
	}
	
	public Invoice[] getInvoices(int clientID, Date dateParam)
	{
		if (clientID != 0)
		{
			this.client = clientID;
		}
		
		if (!dateParam.isEmpty())
		{
			this.date = dateParam;
		}
		
		int numberOfRows = search("id");
		
		Invoice[] result = new Invoice[numberOfRows];
		
		for (int i = 0; i < numberOfRows; i++)
		{
			select(i);
			
			result[i] = new Invoice(this.id, this.number, this.date, this.client, this.discount, this.payment_method, this.comments, getClientName(this.client), getPaymentDescription(this.payment_method), getTotalAmount(this.id));
		}
		
		return result;
	}
	
	public Invoice add(Invoice invoice)
	{
		Invoice result = null;
		
		if (invoice.number == 0)
		{
			this.number = nextNumber();
		}
		else
		{
			this.number = invoice.number;
		}
		
		this.date = invoice.date;
		this.client = invoice.client;
		this.discount = invoice.discount;
		this.payment_method = invoice.paymentMethod;
		this.comments = invoice.comments;
		
		if (create())
		{
			int newID = getLastId();
			result = new Invoice(newID, this.number, this.date, this.client, this.discount, this.payment_method, this.comments, getClientName(this.client), getPaymentDescription(this.payment_method), getTotalAmount(newID));
		}
		
		return result;
	}
	
	public boolean edit(Invoice original, Invoice newInvoice)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			
			if (newInvoice.number == 0)
			{
				this.number = nextNumber();
			}
			else
			{
				this.number = newInvoice.number;
			}
			
			this.date = newInvoice.date;
			this.client = newInvoice.client;
			this.discount = newInvoice.discount;
			this.payment_method = newInvoice.paymentMethod;
			this.comments = newInvoice.comments;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean delete(Invoice invoice)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = invoice.id;
		
		if (read())
		{
			
			TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
			
			if (table.deleteAll(invoice.id))
			{
				if (delete())
				{
					valid = commit();
				}
				else
				{
					rollback();
				}
			}
			else
			{
				rollback();
			}
		}
		
		return valid;
	}
	
	public Date getDate(int invoiceID)
	{
		Date result = null;
		
		this.id = invoiceID;
		
		if (read())
		{
			result = this.date;
		}
		
		return result;
	}
	
	private String getClientName(int clientID)
	{
		TableClient table = new TableClient(getConnection());
		
		return table.getName(clientID);
	}
	
	private String getPaymentDescription(String code)
	{
		TableDictionary table = new TableDictionary(getConnection());
		
		return table.getDescription(Categories.PAYMENT_METHOD.toString(), code);
	}
	
	private double getTotalAmount(int invoiceID)
	{
		TableInvoiceDetail table = new TableInvoiceDetail(getConnection());
		
		return table.getTotalAmount(invoiceID);
	}
	
	private int nextNumber()
	{
		int result = 1;
		
		TableInvoice table = new TableInvoice(getConnection());
		
		int rows = table.search("number DESC");
		
		if (rows > 0)
		{
			table.select(0);
			result = table.number + 1;
		}
		
		return result;
	}
}