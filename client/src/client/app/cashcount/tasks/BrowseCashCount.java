package client.app.cashcount.tasks;

import java.util.Calendar;
import share.app.cashcount.CashCount;
import share.app.dictionary.Category.Categories;
import share.core.utils.Environment;
import client.app.cashcount.gui.def.GUIBrowseCashCount;
import client.app.cashcount.operations.OperationsCashCount;
import client.app.system.dictionary.DictionaryManager;
import client.core.gui.taks.OptionTask;

public class BrowseCashCount extends OptionTask<Void>
{
	private final GUIBrowseCashCount gui = new GUIBrowseCashCount();
	
	public BrowseCashCount()
	{
		super(GUIBrowseCashCount.PATH, TaskType.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		this.gui.month.setItems(DictionaryManager.get(Categories.MONTHS));
		this.gui.month.set(Environment.getCurrentMonth());
		this.gui.year.set(Calendar.getInstance().get(Calendar.YEAR));
		refreshCashCount();
	}
	
	private void refreshCashCount()
	{
		this.gui.list.setRows(OperationsCashCount.call().getCashCounts(this.gui.year.getInt(), this.gui.month.get()));
	}
	
	private void addCashCount()
	{
		AddCashCount task = new AddCashCount();
		Boolean response = task.run();
		
		if (valid(response))
		{
			refreshCashCount();
		}
		
		this.gui.list.focus();
	}
	
	private void editCashCount()
	{
		if (this.gui.list.isRowSelected())
		{
			CashCount current = (CashCount)this.gui.list.getCurrentRow();
			EditCashCount task = new EditCashCount(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshCashCount();
			}
		}
		else
		{
			showWarning(GUIBrowseCashCount.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteCashCount()
	{
		if (this.gui.list.isRowSelected())
		{
			CashCount current = (CashCount)this.gui.list.getCurrentRow();
			DeleteCashCount task = new DeleteCashCount(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshCashCount();
			}
		}
		else
		{
			showWarning(GUIBrowseCashCount.Literals.ROW_NOT_SELECTED);
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
				addCashCount();
				break;
			
			case EDIT:
				editCashCount();
				break;
			
			case DELETE:
				deleteCashCount();
				break;
			
			case SEARCH:
				refreshCashCount();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseCashCount.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseCashCount.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}