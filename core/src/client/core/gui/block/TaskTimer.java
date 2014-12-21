package client.core.gui.block;

import java.util.TimerTask;
import share.core.Constants;
import share.core.Environment;
import client.core.gui.OptionTask;

public class TaskTimer extends TimerTask
{
	@Override
	public void run()
	{
		OptionTask<?> taskObject = (OptionTask<?>)Environment.instanceClass(Constants.BASE_OPTION_TASK + Constants.SYSTEM_BLOCKER);
		taskObject.run();
	}
}