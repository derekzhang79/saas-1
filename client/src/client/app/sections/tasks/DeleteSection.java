package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUIDeleteSection;
import client.app.sections.operations.OperationsSections;
import client.core.gui.taks.Activity;

public class DeleteSection extends Activity<Boolean>
{
	private final Section section;
	
	public DeleteSection(Section section)
	{
		super(GUIDeleteSection.PATH, Type.SINGLE);
		
		this.section = section;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteSection.Literals.ASK_DELETE, this.section.name)))
		{
			Boolean response = OperationsSections.call().deleteSection(this.section);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}