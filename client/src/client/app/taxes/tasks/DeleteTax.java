package client.app.taxes.tasks;

import share.app.taxes.Tax;
import client.app.taxes.gui.def.GUIDeleteTax;
import client.app.taxes.operations.OperationsTaxes;
import client.core.gui.DataFormatter;
import client.core.gui.OptionTask;

public class DeleteTax extends OptionTask<Boolean> {
	
	private Tax tax = null;
	
	public DeleteTax(Tax tax) {
		super(GUIDeleteTax.PATH, TaskType.SINGLE);
		
		this.tax = tax;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteTax.Literals.ASK_DELETE, this.tax.typeDescription + ": " + DataFormatter.formatDecimal(this.tax.value) + "%"))) {
			
			Boolean response = OperationsTaxes.call().deleteTax(this.tax);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}