package share.app.invoices;

import share.core.objects.Date;
import share.core.utils.Shareable;

public class Invoice extends Shareable
{
	private static final long serialVersionUID = -2773762515592554795L;
	
	public final int id;
	public final int number;
	public final Date date;
	public final int client;
	public final double discount;
	public final String paymentMethod;
	public final String comments;
	public final String clientName;
	public final String paymentMethodDescription;
	public final double totalAmount;

	public Invoice(int id, int number, Date date, int client, double discount, String paymentMethod, String comments, String clientName, String paymentMethodDescription, double totalAmount)
	{
		this.id = id;
		this.number = number;
		this.date = date;
		this.client = client;
		this.discount = discount;
		this.paymentMethod = paymentMethod;
		this.comments = comments;
		this.clientName = clientName;
		this.paymentMethodDescription = paymentMethodDescription;
		this.totalAmount = totalAmount;
	}

	public Invoice(int id, int number, Date date, int client, double discount, String paymentMethod, String comments)
	{
		this(id, number, date, client, discount, paymentMethod, comments, "", "", 0);
	}

	public String getDateString()
	{
		return this.date.toString();
	}
}