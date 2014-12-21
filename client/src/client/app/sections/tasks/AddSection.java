package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUIEditSection;
import client.app.sections.operations.OperationsSections;

public class AddSection extends BaseSection<Section> {
	
	public void start() {
		setTitle(getLiteral(GUIEditSection.Literals.TITLE_ADD_SECTION));
		this.gui.code.focus();
	}
	
	private void addSection() {
		if (validate()) {
			Section newSection = new Section(this.gui.code.getInt(), this.gui.name.get(), this.gui.profit.getValue());
			Section response = OperationsSections.call().addSection(newSection);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditSection.Literals.SECTION_NOT_CREATED);
				this.gui.code.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditSection.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.code.isEmpty()) || (!this.gui.name.isEmpty()) || (!this.gui.profit.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addSection();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}