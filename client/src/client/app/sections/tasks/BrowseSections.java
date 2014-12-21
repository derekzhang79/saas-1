package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUIBrowseSections;
import client.app.sections.operations.OperationsSections;
import client.core.gui.taks.OptionTask;

public class BrowseSections extends OptionTask<Void>
{
	private final GUIBrowseSections gui = new GUIBrowseSections();
	
	public BrowseSections()
	{
		super(GUIBrowseSections.PATH, TaskType.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshSections();
	}
	
	private void refreshSections()
	{
		this.gui.list.setRows(OperationsSections.call().getSections());
	}
	
	private void addSection()
	{
		AddSection task = new AddSection();
		Section response = task.run();
		
		if (response != null)
		{
			refreshSections();
		}
		
		this.gui.list.focus();
	}
	
	private void editSection()
	{
		if (this.gui.list.isRowSelected())
		{
			Section current = (Section)this.gui.list.getCurrentRow();
			EditSection task = new EditSection(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshSections();
			}
		}
		else
		{
			showWarning(GUIBrowseSections.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteSection()
	{
		if (this.gui.list.isRowSelected())
		{
			Section current = (Section)this.gui.list.getCurrentRow();
			DeleteSection task = new DeleteSection(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshSections();
			}
		}
		else
		{
			showWarning(GUIBrowseSections.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean()
	{
		this.gui.list.cleanSearch();
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case ADD:
				addSection();
				break;
			
			case EDIT:
				editSection();
				break;
			
			case DELETE:
				deleteSection();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseSections.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseSections.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}