package client.core.gui.block;

import java.util.Timer;

public class BlockTimer extends Timer
{
	private TaskTimer timerTask = null;
	private int delay = 0;

	public BlockTimer(int delay)
	{
		this.delay = delay * 60 * 1000;
	}

	public void stopTask()
	{
		if (this.timerTask != null)
		{
			this.timerTask.cancel();
		}
	}

	public void reset()
	{
		stopTask();
		this.timerTask = new TaskTimer();

		schedule(this.timerTask, this.delay);
	}
}