package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUIEditSection;
import client.app.sections.operations.OperationsSections;

public class EditSection extends BaseSection<Boolean>
{
	private final Section original;
	
	public EditSection(Section original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditSection.Literals.TITLE_EDIT_SECTION));
		this.gui.code.set(this.original.id);
		this.gui.name.set(this.original.name);
		this.gui.profit.set(this.original.profit);
		this.gui.code.focus();
	}
	
	private void editSection()
	{
		if (validate())
		{
			Section newSection = new Section(this.gui.code.getInt(), this.gui.name.get(), this.gui.profit.getValue());
			boolean response = OperationsSections.call().editSection(this.original, newSection);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditSection.Literals.SECTION_NOT_EDITED);
				this.gui.code.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditSection.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.code.equals(this.original.id)) || (!this.gui.name.equals(this.original.name)) || (!this.gui.profit.equals(this.original.profit)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editSection();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}