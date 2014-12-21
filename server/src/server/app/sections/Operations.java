package server.app.sections;

import java.sql.Connection;

import server.app.db.tables.TableSection;
import server.core.ServerOperation;
import share.app.sections.IOperations;
import share.app.sections.Section;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public Section[] getSections() {
		TableSection table = new TableSection(getConnection());
		
		return table.getSections();
	}
	
	public Section addSection(Section section) {
		TableSection table = new TableSection(getConnection());
		
		return table.add(section);
	}
	
	public Boolean editSection(Section original, Section newSection) {
		TableSection table = new TableSection(getConnection());
		
		return table.edit(original, newSection);
	}
	
	public Boolean deleteSection(Section section) {
		TableSection table = new TableSection(getConnection());
		
		return table.delete(section);
	}
}