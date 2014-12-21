package share.core.objects;

import share.core.Shareable;

public class Task extends Shareable {
	
	private static final long serialVersionUID = 1L;
	
	public int id = 0;
	public String name = "";
	public String path = "";
	
	public Task(int id, String name, String path) {
		this.id = id;
		this.name = name;
		this.path = path;
	}
}