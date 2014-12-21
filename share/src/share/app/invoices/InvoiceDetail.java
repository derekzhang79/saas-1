package share.app.invoices;

import share.core.utils.Shareable;

public class InvoiceDetail extends Shareable
{
	private static final long serialVersionUID = -1775630166715977477L;
	
	public final int id;
	public final int invoice;
	public final int line;
	public final int product;
	public final double quantity;
	public final String tax;
	public final double taxValue;
	public final double price;
	public final String productDescription;
	public final double totalPrice;
	public final double priceWithoutTax;

	public InvoiceDetail(int id, int invoice, int line, int product, String productDescription, double quantity, String tax, double taxValue, double price)
	{
		this.id = id;
		this.invoice = invoice;
		this.line = line;
		this.product = product;
		this.productDescription = productDescription;
		this.quantity = quantity;
		this.tax = tax;
		this.taxValue = taxValue;
		this.price = price;
		this.priceWithoutTax = quantity * price;
		this.totalPrice = ((this.price * (taxValue / 100)) + this.price) * quantity;
	}
}