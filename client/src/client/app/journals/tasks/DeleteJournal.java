package client.app.journals.tasks;

import share.app.journals.Journal;
import client.app.journals.gui.def.GUIDeleteJournal;
import client.app.journals.operations.OperationsJournals;
import client.core.gui.OptionTask;

public class DeleteJournal extends OptionTask<Boolean> {
	
	private Journal journal = null;
	
	public DeleteJournal(Journal journal) {
		super(GUIDeleteJournal.PATH, TaskType.SINGLE);
		
		this.journal = journal;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteJournal.Literals.ASK_DELETE, this.journal.getDateString()))) {
			
			Boolean response = OperationsJournals.call().deleteJournal(this.journal);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}