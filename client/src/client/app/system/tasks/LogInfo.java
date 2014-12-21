package client.app.system.tasks;

import share.core.utils.Environment;
import client.app.system.gui.def.GUILogInfo;
import client.core.debug.Debug;
import client.core.gui.taks.OptionTask;

public class LogInfo extends OptionTask<Void>
{
	private final GUILogInfo gui = new GUILogInfo();
	
	public LogInfo()
	{
		super(GUILogInfo.PATH, TaskType.MODAL, true);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.info.setEditable(false);
		this.gui.info.set(Debug.getInfo());
		this.gui.info.setBottom();
	}
	
	private void copyText()
	{
		Environment.copyClipboard(this.gui.info.get());
		this.gui.info.focus();
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case COPY:
				copyText();
				break;
			
			case ACCEPT:
				close();
				break;
			
			default:
				break;
		}
	}
}