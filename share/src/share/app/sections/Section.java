package share.app.sections;

import share.core.Shareable;

public class Section extends Shareable
{
	private static final long serialVersionUID = 9186554906062796883L;
	
	public final int id;
	public final String name;
	public final double profit;

	public Section(int id, String name, double profit)
	{
		this.id = id;
		this.name = name;
		this.profit = profit;
	}
}