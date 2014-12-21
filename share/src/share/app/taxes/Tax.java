package share.app.taxes;

import share.core.objects.Date;
import share.core.utils.Shareable;

public class Tax extends Shareable
{
	private static final long serialVersionUID = -8004115041923571997L;
	
	public final int id;
	public final String type;
	public final double value;
	public final Date start;
	public final String typeDescription;

	public Tax(int id, String type, String typeDescription, double value, Date start)
	{
		this.id = id;
		this.type = type;
		this.typeDescription = typeDescription;
		this.value = value;
		this.start = start;
	}
}