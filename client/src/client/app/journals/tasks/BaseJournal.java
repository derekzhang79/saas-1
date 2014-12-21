package client.app.journals.tasks;

import java.awt.Color;
import client.app.journals.gui.def.GUIEditJournal;
import client.core.gui.taks.OptionTask;

public abstract class BaseJournal<ResultType> extends OptionTask<ResultType> {
	
	protected GUIEditJournal gui = new GUIEditJournal();
	
	public BaseJournal() {
		super(GUIEditJournal.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.date.isEmpty()) {
			showWarning(GUIEditJournal.Literals.DATE_REQUIRED);
			this.gui.date.setBorderColor(Color.RED);
			this.gui.date.focus();
		} else {
			valid = true;
		}
		
		return valid;
	}
}