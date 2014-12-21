package share.app.workshop;

import share.core.utils.Shareable;

public class FixOrderDetail extends Shareable
{
	private static final long serialVersionUID = -4424425494662426828L;

	public final int id;
	public final int fixOrder;
	public final int line;
	public final int quantity;
	public final String detail;
	public final double amount;
	
	public FixOrderDetail(int id, int fixOrder, int line, int quantity, String detail, double amount)
	{
		this.id = id;
		this.fixOrder = fixOrder;
		this.line = line;
		this.quantity = quantity;
		this.detail = detail;
		this.amount = amount;
	}
}