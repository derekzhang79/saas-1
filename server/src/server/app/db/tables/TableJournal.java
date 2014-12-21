package server.app.db.tables;

import java.sql.Connection;
import server.core.db.Table;
import share.app.journals.Journal;
import share.app.sections.Section;
import share.core.Date;

public class TableJournal extends Table
{
	public Integer id = new Integer(0);
	public Date date = new Date();
	
	public TableJournal(Connection connection)
	{
		super(connection, "JOURNAL");
		setTable(this);
	}
	
	public Journal[] getJournals(Integer year, String month)
	{
		
		if ((year != 0) && (!month.isEmpty()))
		{
			addCondition("date LIKE '" + year + "-" + month + "-%'");
		}
		else if ((year != 0) && (month.isEmpty()))
		{
			addCondition("date LIKE '" + year + "-%'");
		}
		else if ((year == 0) && (!month.isEmpty()))
		{
			addCondition("date LIKE '%-" + month + "-%'");
		}
		
		int number = search("DATE DESC");
		
		Journal[] result = new Journal[number];
		
		for (int i = 0; i < number; i++)
		{
			select(i);
			
			result[i] = new Journal(this.id, this.date, getJournalSale(this.id), getJournalProfit(this.id));
		}
		
		return result;
	}
	
	public Journal add(Journal journal)
	{
		Journal result = null;
		
		beginTransaction();
		
		this.date = journal.date;
		
		if (create())
		{
			Section[] sections = getSections();
			int quantity = 0;
			
			for (Section section : sections)
			{
				TableJournalDetail table = new TableJournalDetail(getConnection());
				
				if (table.add(getLastId(), section.id))
				{
					quantity++;
				}
			}
			
			if (sections.length == quantity)
			{
				if (commit())
				{
					int newID = getLastId();
					result = new Journal(newID, journal.date, getJournalSale(newID), getJournalProfit(newID));
				}
			}
			else
			{
				rollback();
			}
		}
		else
		{
			rollback();
		}
		
		return result;
	}
	
	private Section[] getSections()
	{
		TableSection table = new TableSection(getConnection());
		
		return table.getSections();
	}
	
	private double getJournalSale(int journalID)
	{
		TableJournalDetail table = new TableJournalDetail(getConnection());
		
		return table.getSale(journalID);
	}
	
	private double getJournalProfit(int journalID)
	{
		TableJournalDetail table = new TableJournalDetail(getConnection());
		
		return table.getProfit(journalID);
	}
	
	public boolean editJournal(Journal original, Journal newJournal)
	{
		boolean valid = false;
		
		this.id = original.id;
		
		if (read())
		{
			this.date = newJournal.date;
			
			valid = update();
		}
		
		return valid;
	}
	
	public boolean deleteJournal(Journal journal)
	{
		boolean valid = false;
		
		beginTransaction();
		
		this.id = journal.id;
		
		if (read())
		{
			TableJournalDetail table = new TableJournalDetail(getConnection());
			
			if (table.delete(journal.id))
			{
				if (delete())
				{
					valid = commit();
				}
				else
				{
					rollback();
				}
			}
			else
			{
				rollback();
			}
		}
		
		return valid;
	}
}