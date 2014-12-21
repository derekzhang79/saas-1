package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUIDeleteSection;
import client.app.sections.operations.OperationsSections;
import client.core.gui.taks.OptionTask;

public class DeleteSection extends OptionTask<Boolean> {
	
	private Section section = null;
	
	public DeleteSection(Section section) {
		super(GUIDeleteSection.PATH, TaskType.SINGLE);
		
		this.section = section;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteSection.Literals.ASK_DELETE, this.section.name))) {
			
			Boolean response = OperationsSections.call().deleteSection(this.section);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}