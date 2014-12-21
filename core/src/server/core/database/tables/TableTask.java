package server.core.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.core.objects.Task;

public class TableTask extends Table
{
	public Integer id = new Integer(0);
	public String name = new String();
	public String path = new String();

	public TableTask(Connection connection)
	{
		super(connection, "TASK");
		setTable(this);
	}

	public Task[] getTasks()
	{
		int number = search("name");

		Task[] result = new Task[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Task(this.id, this.name, this.path);
		}

		return result;
	}

	public Task getTask(Integer taskID)
	{
		Task result = null;

		this.id = taskID;

		if (read())
		{
			result = new Task(this.id, this.name, this.path);
		}

		return result;
	}
}