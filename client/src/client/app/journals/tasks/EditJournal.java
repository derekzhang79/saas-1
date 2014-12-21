package client.app.journals.tasks;

import share.app.journals.Journal;
import client.app.journals.gui.def.GUIEditJournal;
import client.app.journals.operations.OperationsJournals;

public class EditJournal extends BaseJournal<Boolean> {
	
	private Journal original = null;
	
	public EditJournal(Journal original) {
		this.original = original;
	}
	
	public void start() {
		setTitle(getLiteral(GUIEditJournal.Literals.TITLE_EDIT_JOURNAL));
		this.gui.date.set(this.original.date);
		this.gui.date.focus();
	}
	
	private void editJournal() {
		if (validate()) {
			Journal newJournal = new Journal(this.gui.date.get());
			boolean response = OperationsJournals.call().editJournal(this.original, newJournal);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditJournal.Literals.JOURNAL_NOT_EDITED);
				this.gui.date.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditJournal.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.date.equals(this.original.date)));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editJournal();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}