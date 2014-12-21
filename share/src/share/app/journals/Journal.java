package share.app.journals;

import share.core.objects.Date;
import share.core.utils.Shareable;

public class Journal extends Shareable
{
	private static final long serialVersionUID = -3553418364768433340L;
	
	public final int id;
	public final Date date;
	public final double sale;
	public final double profit;

	public Journal(int id, Date date, double sale, double profit)
	{
		this.id = id;
		this.date = date;
		this.sale = sale;
		this.profit = profit;
	}

	public Journal(Date date)
	{
		this(0, date, 0, 0);
	}

	public String getDateString()
	{
		return this.date.toString();
	}
}