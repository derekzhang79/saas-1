package server.app.db.tables;

import java.sql.Connection;
import server.core.db.Table;
import share.app.journals.JournalDetail;

public class TableJournalDetail extends Table
{
	public Integer id = new Integer(0);
	public Integer journal = new Integer(0);
	public Integer section = new Integer(0);
	public Double amount = new Double(0);

	public TableJournalDetail(Connection connection)
	{
		super(connection, "JOURNAL_DETAIL");
		setTable(this);
	}

	public JournalDetail[] getJournalDetail(Integer journalID)
	{
		this.journal = journalID;
		int number = this.search("id");

		JournalDetail[] result = new JournalDetail[number];

		for (int i = 0; i < number; i++)
		{
			select(i);

			result[i] = new JournalDetail(this.id, this.journal, this.section, this.section + " - " + getSectionName(this.section), this.amount);
		}

		return result;
	}

	public boolean add(int journalID, int sectionID)
	{
		this.journal = journalID;
		this.section = sectionID;

		return create();
	}

	public boolean edit(JournalDetail original, JournalDetail newJournalDetail)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.amount = newJournalDetail.amount;

			valid = update();
		}

		return valid;
	}

	public boolean delete(int journalID)
	{
		this.journal = journalID;

		int rows = search();
		int quantity = 0;

		for (int i = 0; i < rows; i++)
		{
			select(i);

			if (delete())
			{
				quantity++;
			}
		}

		return (rows == quantity);
	}

	public double getSale(int journalID)
	{
		double result = 0;

		this.journal = journalID;

		int number = search("id");

		for (int i = 0; i < number; i++)
		{
			select(i);
			result += this.amount;
		}

		return result;
	}

	public double getProfit(int journalID)
	{
		double result = 0;

		this.journal = journalID;

		int number = search("id");

		for (int i = 0; i < number; i++)
		{
			select(i);

			result += (this.amount * getSectionProfit(this.section));
		}

		return result;
	}

	private double getSectionProfit(int sectionID)
	{
		TableSection table = new TableSection(getConnection());

		return table.getProfit(sectionID);
	}

	private String getSectionName(int sectionID)
	{
		TableSection table = new TableSection(getConnection());

		return table.getName(sectionID);
	}
}