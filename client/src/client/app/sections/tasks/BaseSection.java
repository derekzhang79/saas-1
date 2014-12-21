package client.app.sections.tasks;

import java.awt.Color;

import client.app.sections.gui.def.GUIEditSection;
import client.core.gui.OptionTask;

public abstract class BaseSection<ResultType> extends OptionTask<ResultType> {
	
	protected GUIEditSection gui = new GUIEditSection();
	
	public BaseSection() {
		super(GUIEditSection.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.code.isEmpty()) {
			showWarning(GUIEditSection.Literals.CODE_REQUIRED);
			this.gui.code.focus();
			this.gui.code.setBorderColor(Color.RED);
		} else if (this.gui.name.isEmpty()) {
			showWarning(GUIEditSection.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		} else {
			valid = true;
		}
		
		return valid;
	}
}