package server.app.db.tables;

import java.sql.Connection;
import server.core.db.Table;
import share.app.sections.Section;

public class TableSection extends Table
{
	public Integer id = new Integer(0);
	public String name = new String();
	public Double profit = new Double(0);

	public TableSection(Connection connection)
	{
		super(connection, "SECTION");
		setTable(this);
	}

	public Section[] getSections()
	{
		int number = search("id");

		Section[] result = new Section[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Section(this.id, this.name, this.profit);
		}

		return result;
	}

	public Section add(Section section)
	{
		Section result = null;

		this.id = section.id;
		this.name = section.name;
		this.profit = section.profit;

		if (create())
		{
			result = new Section(this.id, this.name, this.profit);
		}

		return result;
	}

	public boolean edit(Section original, Section newSection)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.id = newSection.id;
			this.name = newSection.name;
			this.profit = newSection.profit;

			valid = update();
		}

		return valid;
	}

	public boolean delete(Section section)
	{
		boolean valid = false;

		this.id = section.id;

		if (read())
		{
			valid = delete();
		}

		return valid;
	}

	public String getName(int section)
	{
		String result = "";

		this.id = section;

		if (read())
		{
			result = this.name;
		}

		return result;
	}

	public double getProfit(int section)
	{
		double result = 0;

		this.id = section;

		if (read())
		{
			result = this.profit;
		}

		return result;
	}
}