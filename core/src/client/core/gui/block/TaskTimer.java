package client.core.gui.block;

import java.util.TimerTask;
import share.core.constants.Constants;
import share.core.utils.Environment;
import client.core.gui.taks.Activity;

public class TaskTimer extends TimerTask
{
	@Override
	public void run()
	{
		Activity<?> taskObject = (Activity<?>)Environment.instanceClass(Constants.BASE_OPTION_TASK + Constants.SYSTEM_BLOCKER);
		taskObject.run();
	}
}