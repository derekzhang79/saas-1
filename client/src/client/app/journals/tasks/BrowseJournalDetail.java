package client.app.journals.tasks;

import share.app.journals.Journal;
import share.app.journals.JournalDetail;
import client.app.journals.gui.def.GUIBrowseJournalDetail;
import client.app.journals.operations.OperationsJournals;
import client.core.gui.taks.OptionTask;

public class BrowseJournalDetail extends OptionTask<Void>
{
	private final GUIBrowseJournalDetail gui = new GUIBrowseJournalDetail();
	private Journal journal = null;
	
	public BrowseJournalDetail(Journal journal)
	{
		super(GUIBrowseJournalDetail.PATH, TaskType.MODAL);
		
		this.journal = journal;
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshJournalsDetails();
	}
	
	private void refreshJournalsDetails()
	{
		this.gui.list.setRows(OperationsJournals.call().getJournalDetail(this.journal.id));
	}
	
	private void editJournalDetail()
	{
		if (this.gui.list.isRowSelected())
		{
			JournalDetail current = (JournalDetail)this.gui.list.getCurrentRow();
			EditJournalDetail task = new EditJournalDetail(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshJournalsDetails();
			}
		}
		else
		{
			showWarning(GUIBrowseJournalDetail.Literals.ROW_NOT_SELECTED);
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
			case EDIT:
				editJournalDetail();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseJournalDetail.Literals.LIST_PDF) + " " + this.journal.getDateString(), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseJournalDetail.Literals.LIST_PDF) + " " + this.journal.getDateString(), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}