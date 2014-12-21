package share.app.cashcount;

import share.core.Date;
import share.core.Shareable;

public class CashCount extends Shareable
{
	private static final long serialVersionUID = 117587871507209932L;

	public final int id;
	public final Date date;
	public final int type_500;
	public final int type_200;
	public final int type_100;
	public final int type_50;
	public final int type_20;
	public final int type_10;
	public final int type_5;
	public final int type_2;
	public final int type_1;
	public final int type_0_5;
	public final int type_0_2;
	public final int type_0_1;
	public final int type_0_05;
	public final int type_0_02;
	public final int type_0_01;
	public final double total;
	
	public CashCount(int id, Date date, int type_500, int type_200, int type_100, int type_50, int type_20, int type_10, int type_5, int type_2, int type_1, int type_0_5, int type_0_2, int type_0_1, int type_0_05, int type_0_02, int type_0_01)
	{
		this.id = id;
		this.date = date;
		this.type_500 = type_500;
		this.type_200 = type_200;
		this.type_100 = type_100;
		this.type_50 = type_50;
		this.type_20 = type_20;
		this.type_10 = type_10;
		this.type_5 = type_5;
		this.type_2 = type_2;
		this.type_1 = type_1;
		this.type_0_5 = type_0_5;
		this.type_0_2 = type_0_2;
		this.type_0_1 = type_0_1;
		this.type_0_05 = type_0_05;
		this.type_0_02 = type_0_02;
		this.type_0_01 = type_0_01;
		
		this.total = (type_500 * 500) + (type_200 * 200) + (type_100 * 100) + (type_50 * 50) + (type_20 * 20) + (type_10 * 10) + (type_5 * 5) + (type_2 * 2) + (type_1 * 1) + (type_0_5 * 0.5) + (type_0_2 * 0.2) + (type_0_1 * 0.1) + (type_0_05 * 0.05) + (type_0_02 * 0.02) + (type_0_01 * 0.01);
	}
	
	public String getDateString()
	{
		return this.date.toString();
	}
}