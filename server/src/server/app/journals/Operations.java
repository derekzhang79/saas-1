package server.app.journals;

import java.sql.Connection;
import server.app.database.tables.TableJournal;
import server.app.database.tables.TableJournalDetail;
import server.core.connection.ServerOperation;
import share.app.journals.IOperations;
import share.app.journals.Journal;
import share.app.journals.JournalDetail;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Journal[] getJournals(Integer year, String month)
	{
		TableJournal table = new TableJournal(getConnection());

		return table.getJournals(year, month);
	}

	@Override
	public JournalDetail[] getJournalDetail(Integer journal)
	{
		TableJournalDetail table = new TableJournalDetail(getConnection());

		return table.getJournalDetail(journal);
	}

	@Override
	public Journal addJournal(Journal journal)
	{
		TableJournal table = new TableJournal(getConnection());

		return table.add(journal);
	}

	@Override
	public Boolean editJournal(Journal original, Journal newJournal)
	{
		TableJournal table = new TableJournal(getConnection());

		return table.editJournal(original, newJournal);
	}

	@Override
	public Boolean deleteJournal(Journal journal)
	{
		TableJournal table = new TableJournal(getConnection());

		return table.deleteJournal(journal);
	}

	@Override
	public Boolean editJournalDetail(JournalDetail original, JournalDetail newJournalDetail)
	{
		TableJournalDetail table = new TableJournalDetail(getConnection());

		return table.edit(original, newJournalDetail);
	}
}