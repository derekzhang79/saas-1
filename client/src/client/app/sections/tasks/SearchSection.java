package client.app.sections.tasks;

import share.app.sections.Section;
import client.app.sections.gui.def.GUISearchSection;
import client.app.sections.operations.OperationsSections;
import client.core.gui.taks.Activity;

public class SearchSection extends Activity<Section>
{
	private final GUISearchSection gui = new GUISearchSection();
	
	public SearchSection()
	{
		super(GUISearchSection.PATH, Type.MODAL);
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
	
	private void select()
	{
		close((Section)this.gui.list.getCurrentRow());
	}
	
	private void clean()
	{
		this.gui.list.cleanSearch();
	}
	
	private void addSection()
	{
		AddSection task = new AddSection();
		Section response = task.run();
		
		if (response != null)
		{
			close(response);
		}
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case ADD:
				addSection();
				break;
			
			case SELECT:
				select();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUISearchSection.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUISearchSection.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}