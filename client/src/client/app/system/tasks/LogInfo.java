package client.app.system.tasks;

import share.core.Environment;
import client.app.system.gui.def.GUILogInfo;
import client.core.debug.Debug;
import client.core.gui.OptionTask;

public class LogInfo extends OptionTask<Void> {
	
	private GUILogInfo gui = new GUILogInfo();
	
	public LogInfo() {
		super(GUILogInfo.PATH, TaskType.MODAL, true);
	}
	
	public void start() {
		setGUI(this.gui);
		this.gui.info.setEditable(false);
		this.gui.info.set(Debug.getInfo());
		this.gui.info.setBottom();
	}
	
	private void copyText() {
		Environment.copyClipboard(this.gui.info.get());
		this.gui.info.focus();
	}
	
	public void event(Event event) {
		
		switch (event) {
		
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