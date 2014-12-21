package client.app.journals.tasks;

import share.app.journals.JournalDetail;
import client.app.journals.gui.def.GUIEditJournalDetail;
import client.app.journals.operations.OperationsJournals;
import client.core.gui.OptionTask;

public class EditJournalDetail extends OptionTask<Boolean> {
	
	private JournalDetail original = null;
	private GUIEditJournalDetail gui = new GUIEditJournalDetail();
	
	public EditJournalDetail(JournalDetail original) {
		super(GUIEditJournalDetail.PATH, TaskType.MODAL);
		
		this.original = original;
	}
	
	public void start() {
		setGUI(this.gui);
		addTitle(this.original.sectionName);
		
		if (this.original.amount != 0) {
			this.gui.amount.set(this.original.amount);
		}
		
		this.gui.amount.focus();
	}
	
	private void editJournalDetail() {
		JournalDetail newJournal = new JournalDetail(this.original.id, this.original.journal, this.original.section, this.original.sectionName, this.gui.amount.getValue());
		boolean response = OperationsJournals.call().editJournalDetail(this.original, newJournal);
		
		if (response) {
			close(true);
		} else {
			showWarning(GUIEditJournalDetail.Literals.JOURNAL_DETAIL_NOT_EDITED);
			this.gui.amount.focus();
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditJournalDetail.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.amount.equals(this.original.amount)));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editJournalDetail();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}