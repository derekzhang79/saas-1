package share.core.objects;

import share.core.Shareable;

public class Permission extends Shareable {
	
	private static final long serialVersionUID = 1L;
	
	public int id = 0;
	public int userGroup = 0;
	public int task = 0;
	
	public String taskName = "";
	public String taskPath = "";
	
	public Permission(int id, int userGroup, int task, String taskName, String taskPath) {
		this.id = id;
		this.userGroup = userGroup;
		this.task = task;
		this.taskName = taskName;
		this.taskPath = taskPath;
	}
}