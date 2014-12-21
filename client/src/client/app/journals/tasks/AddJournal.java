package client.app.journals.tasks;

import share.app.journals.Journal;
import share.core.Date;
import client.app.journals.gui.def.GUIEditJournal;
import client.app.journals.operations.OperationsJournals;

public class AddJournal extends BaseJournal<Journal> {
	
	public void start() {
		setTitle(getLiteral(GUIEditJournal.Literals.TITLE_ADD_JOURNAL));
		this.gui.date.setToday();
		this.gui.date.focus();
	}
	
	private void addJournal() {
		if (validate()) {
			Journal newJournal = new Journal(this.gui.date.get());
			Journal response = OperationsJournals.call().addJournal(newJournal);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditJournal.Literals.JOURNAL_NOT_CREATED);
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
		return ((!this.gui.date.equals(Date.getTodayDate())));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addJournal();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}