package share.core.objects;

import share.core.utils.Shareable;

public class Task extends Shareable
{
	private static final long serialVersionUID = -3644821978306587322L;

	public final int id;
	public final String name;
	public final String path;

	public Task(int id, String name, String path)
	{
		this.id = id;
		this.name = name;
		this.path = path;
	}
}