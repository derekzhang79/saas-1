package share.app.brands;

import share.core.Shareable;

public class Brand extends Shareable
{
	private static final long serialVersionUID = 3316289104686365410L;

	public final int id;
	public final String name;

	public Brand(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
}
