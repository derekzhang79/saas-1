package client.app.system.tasks;

import share.core.utils.Environment;
import client.app.system.gui.def.GUILogError;
import client.core.debug.Debug;
import client.core.gui.taks.Activity;

public class LogError extends Activity<Void>
{
	private final GUILogError gui = new GUILogError();
	
	public LogError()
	{
		super(GUILogError.PATH, Type.MODAL, true);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.error.setEditable(false);
		this.gui.error.set(Debug.getErrors());
		this.gui.error.setBottom();
	}
	
	private void copyText()
	{
		Environment.copyClipboard(this.gui.error.get());
		this.gui.error.focus();
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