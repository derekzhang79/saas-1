package share.core.objects;

import share.core.utils.Shareable;

public class Permission extends Shareable
{
	private static final long serialVersionUID = 175181423876055737L;
	
	public final int id;
	public final int userGroup;
	public final int task;
	public final String taskName;
	public final String taskPath;
	
	public Permission(int id, int userGroup, int task, String taskName, String taskPath)
	{
		this.id = id;
		this.userGroup = userGroup;
		this.task = task;
		this.taskName = taskName;
		this.taskPath = taskPath;
	}
}