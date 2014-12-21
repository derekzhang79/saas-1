package client.app.taxes.tasks;

import java.awt.Color;
import client.app.taxes.gui.def.GUIEditTax;
import client.core.gui.taks.OptionTask;

public abstract class BaseTax extends OptionTask<Boolean>
{
	protected GUIEditTax gui = new GUIEditTax();

	public BaseTax()
	{
		super(GUIEditTax.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.type.isEmpty())
		{
			showWarning(GUIEditTax.Literals.TYPE_REQUIRED);
			this.gui.type.focus();
			this.gui.type.setBorderColor(Color.RED);
		}
		else if (this.gui.value.isEmpty())
		{
			showWarning(GUIEditTax.Literals.VALUE_REQUIRED);
			this.gui.value.focus();
			this.gui.value.setBorderColor(Color.RED);
		}
		else if (this.gui.start.isEmpty())
		{
			showWarning(GUIEditTax.Literals.DATE_REQUIRED);
			this.gui.start.focus();
			this.gui.start.setBorderColor(Color.RED);
		}
		else
		{
			valid = true;
		}

		return valid;
	}
}