package client.app.journals.tasks;

import share.app.journals.Journal;
import client.app.journals.gui.def.GUIDeleteJournal;
import client.app.journals.operations.OperationsJournals;
import client.core.gui.taks.Activity;

public class DeleteJournal extends Activity<Boolean>
{
	private final Journal journal;

	public DeleteJournal(Journal journal)
	{
		super(GUIDeleteJournal.PATH, Type.SINGLE);

		this.journal = journal;
	}

	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteJournal.Literals.ASK_DELETE, this.journal.getDateString())))
		{
			Boolean response = OperationsJournals.call().deleteJournal(this.journal);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}