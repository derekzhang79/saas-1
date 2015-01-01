package client.app.taxes.tasks;

import share.app.taxes.Tax;
import client.app.taxes.gui.def.GUIDeleteTax;
import client.app.taxes.operations.OperationsTaxes;
import client.core.gui.format.DataFormatter;
import client.core.gui.taks.Activity;

public class DeleteTax extends Activity<Boolean>
{
	private final Tax tax;
	
	public DeleteTax(Tax tax)
	{
		super(GUIDeleteTax.PATH, Type.SINGLE);
		
		this.tax = tax;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteTax.Literals.ASK_DELETE, this.tax.typeDescription + ": " + DataFormatter.formatDecimal(this.tax.value) + "%")))
		{
			Boolean response = OperationsTaxes.call().deleteTax(this.tax);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}