package client.app.invoices.tasks;

import share.app.dictionary.Category.Categories;
import share.app.invoices.InvoiceDetail;
import client.app.invoices.gui.def.GUIEditInvoiceDetail;
import client.app.invoices.operations.OperationsInvoices;
import client.app.system.dictionary.DictionaryManager;

public class AddInvoiceDetail extends BaseInvoiceDetail
{
	private int invoiceID = 0;
	
	public AddInvoiceDetail(int invoiceID)
	{
		this.invoiceID = invoiceID;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditInvoiceDetail.Literals.TITLE_ADD_INVOICE_DETAIL));
		this.gui.tax.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.tax.selectNone();
		this.gui.productName.focus();
	}
	
	private void addInvoiceDetail()
	{
		if (validate())
		{
			InvoiceDetail newInvoiceDetail = new InvoiceDetail(0, this.invoiceID, 0, this.productID, "", this.gui.quantity.getValue(), this.gui.tax.get(), this.taxValue, this.gui.price.getValue());
			boolean response = OperationsInvoices.call().addInvoiceDetail(newInvoiceDetail);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditInvoiceDetail.Literals.INVOICE_DETAIL_NOT_CREATED);
				this.gui.productName.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditInvoiceDetail.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.productName.isEmpty()) || (!this.gui.quantity.isEmpty()) || (!this.gui.price.isEmpty()) || (!this.gui.tax.isEmpty()));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addInvoiceDetail();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_PRODUCT:
				searchProduct();
				break;
			
			case REFRESH:
				refreshTotalPrice();
				break;
			
			default:
				break;
		}
	}
}