package client.app.system.tasks;

import client.app.system.gui.def.GUIExit;
import client.core.gui.taks.Activity;

public class Exit extends Activity<Void>
{
	public Exit()
	{
		super(GUIExit.PATH, Type.SINGLE, true);
	}

	@Override
	public void start()
	{
		if (showConfirm(GUIExit.Literals.ASK_EXIT))
		{
			System.exit(0);
		}
		else
		{
			close();
		}
	}
}