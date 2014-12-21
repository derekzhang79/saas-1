package client.app.journals.tasks;

import java.util.Calendar;
import share.app.dictionary.Category.Categories;
import share.app.journals.Journal;
import share.core.constants.Constants;
import share.core.utils.Environment;
import client.app.journals.gui.def.GUIBrowseJournals;
import client.app.journals.operations.OperationsJournals;
import client.app.system.dictionary.DictionaryManager;
import client.core.gui.format.DataFormatter;
import client.core.gui.taks.OptionTask;

public class BrowseJournals extends OptionTask<Void>
{
	private final GUIBrowseJournals gui = new GUIBrowseJournals();
	
	public BrowseJournals()
	{
		super(GUIBrowseJournals.PATH, TaskType.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.month.setItems(DictionaryManager.get(Categories.MONTHS));
		this.gui.month.set(Environment.getCurrentMonth());
		this.gui.year.set(Calendar.getInstance().get(Calendar.YEAR));
		refreshJournals();
	}
	
	private void refreshJournals()
	{
		Journal[] list = OperationsJournals.call().getJournals(this.gui.year.getInt(), this.gui.month.get());
		
		double totalSale = 0;
		double totalProfit = 0;
		
		for (Journal journal : list)
		{
			totalSale += journal.sale;
			totalProfit += journal.profit;
		}
		
		this.gui.list.setRows(list);
		
		this.gui.labelTotalSale.set(DataFormatter.formatDecimal(totalSale) + " " + Constants.CURRENCY_EURO);
		this.gui.labelTotalProfit.set(DataFormatter.formatDecimal(totalProfit) + " " + Constants.CURRENCY_EURO);
	}
	
	private void journalDetail()
	{
		if (this.gui.list.isRowSelected())
		{
			Journal current = (Journal)this.gui.list.getCurrentRow();
			BrowseJournalDetail task = new BrowseJournalDetail(current);
			task.run();
			
			refreshJournals();
			this.gui.list.focus();
		}
		else
		{
			showWarning(GUIBrowseJournals.Literals.ROW_NOT_SELECTED);
			this.gui.list.focus();
		}
	}
	
	private void addJournal()
	{
		AddJournal task = new AddJournal();
		Journal response = task.run();
		
		if (response != null)
		{
			refreshJournals();
			
			BrowseJournalDetail browse = new BrowseJournalDetail(response);
			browse.run();
			
			refreshJournals();
		}
		
		this.gui.list.focus();
	}
	
	private void editJournal()
	{
		if (this.gui.list.isRowSelected())
		{
			Journal current = (Journal)this.gui.list.getCurrentRow();
			EditJournal task = new EditJournal(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshJournals();
			}
		}
		else
		{
			showWarning(GUIBrowseJournals.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteJournal()
	{
		if (this.gui.list.isRowSelected())
		{
			Journal current = (Journal)this.gui.list.getCurrentRow();
			DeleteJournal task = new DeleteJournal(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshJournals();
			}
		}
		else
		{
			showWarning(GUIBrowseJournals.Literals.ROW_NOT_SELECTED);
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
			case DETAIL:
				journalDetail();
				break;
			
			case ADD:
				addJournal();
				break;
			
			case EDIT:
				editJournal();
				break;
			
			case DELETE:
				deleteJournal();
				break;
			
			case SEARCH:
				refreshJournals();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseJournals.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseJournals.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}