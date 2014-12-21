package server.app.sections;

import java.sql.Connection;
import server.app.db.tables.TableSection;
import server.core.ServerOperation;
import share.app.sections.IOperations;
import share.app.sections.Section;

public class Operations extends ServerOperation implements IOperations
{
	public Operations(Connection appConnection, Connection sysConnection)
	{
		super(appConnection, sysConnection);
	}

	@Override
	public Section[] getSections()
	{
		TableSection table = new TableSection(getConnection());

		return table.getSections();
	}

	@Override
	public Section addSection(Section section)
	{
		TableSection table = new TableSection(getConnection());

		return table.add(section);
	}

	@Override
	public Boolean editSection(Section original, Section newSection)
	{
		TableSection table = new TableSection(getConnection());

		return table.edit(original, newSection);
	}

	@Override
	public Boolean deleteSection(Section section)
	{
		TableSection table = new TableSection(getConnection());

		return table.delete(section);
	}
}