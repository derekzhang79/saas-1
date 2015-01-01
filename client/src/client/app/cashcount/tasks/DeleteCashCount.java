package client.app.cashcount.tasks;

import share.app.cashcount.CashCount;
import client.app.cashcount.gui.def.GUIDeleteCashCount;
import client.app.cashcount.operations.OperationsCashCount;
import client.core.gui.taks.Activity;

public class DeleteCashCount extends Activity<Boolean>
{
	private CashCount cashCount = null;
	
	public DeleteCashCount(CashCount cashCount)
	{
		super(GUIDeleteCashCount.PATH, Type.SINGLE);
		
		this.cashCount = cashCount;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteCashCount.Literals.ASK_DELETE, this.cashCount.getDateString())))
		{
			Boolean response = OperationsCashCount.call().deleteCashCount(this.cashCount);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}