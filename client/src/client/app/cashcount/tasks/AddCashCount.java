package client.app.cashcount.tasks;

import share.app.cashcount.CashCount;
import share.core.objects.Date;
import client.app.cashcount.gui.def.GUIEditCashCount;
import client.app.cashcount.operations.OperationsCashCount;

public class AddCashCount extends BaseCashCount
{
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditCashCount.Literals.TITLE_ADD_CASH_COUNT));
		refreshTotals();
		this.gui.date.setToday();
		this.gui.type_50.focus();
	}
	
	private void addCashCount()
	{
		if (validate())
		{
			CashCount newCashCount = new CashCount(0, this.gui.date.get(), this.gui.type_500.getInt(), this.gui.type_200.getInt(), this.gui.type_100.getInt(), this.gui.type_50.getInt(), this.gui.type_20.getInt(), this.gui.type_10.getInt(), this.gui.type_5.getInt(), this.gui.type_2.getInt(), this.gui.type_1.getInt(), this.gui.type_0_5.getInt(), this.gui.type_0_2.getInt(), this.gui.type_0_1.getInt(), this.gui.type_0_05.getInt(), this.gui.type_0_02.getInt(), this.gui.type_0_01.getInt());
			boolean response = OperationsCashCount.call().addCashCount(newCashCount);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditCashCount.Literals.CASH_COUNT_NOT_CREATED);
				this.gui.type_50.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditCashCount.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.date.equals(Date.getTodayDate())) || (!this.gui.type_0_01.isEmpty()) || (!this.gui.type_0_02.isEmpty()) || (!this.gui.type_0_05.isEmpty()) || (!this.gui.type_0_1.isEmpty()) || (!this.gui.type_0_2.isEmpty()) || (!this.gui.type_0_5.isEmpty()) || (!this.gui.type_1.isEmpty()) || (!this.gui.type_2.isEmpty()) || (!this.gui.type_5.isEmpty()) || (!this.gui.type_10.isEmpty()) || (!this.gui.type_20.isEmpty()) || (!this.gui.type_50.isEmpty()) || (!this.gui.type_100.isEmpty()) || (!this.gui.type_200.isEmpty()) || (!this.gui.type_500.isEmpty()));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addCashCount();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case REFRESH:
				refreshTotals();
				break;
			
			default:
				break;
		}
	}
}