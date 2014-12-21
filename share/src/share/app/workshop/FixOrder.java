package share.app.workshop;

import share.core.Date;
import share.core.Shareable;

public class FixOrder extends Shareable
{
	private static final long serialVersionUID = 5016286261552495224L;

	public final int id;
	public final int client;
	public final String status;
	public final Date start;
	public final Date finish;
	public final String comments;
	public final String clientName;
	public final String statusDescription;
	public final double totalAmount;
	
	public FixOrder(int id, int client, String clientName, String status, String statusDescription, Date start, Date finish, String comment, double totalAmount)
	{
		this.id = id;
		this.client = client;
		this.clientName = clientName;
		this.status = status;
		this.statusDescription = statusDescription;
		this.start = start;
		this.finish = finish;
		this.comments = comment;
		this.totalAmount = totalAmount;
	}
}