package client.app.brands.tasks;

import java.awt.Color;
import client.app.brands.gui.def.GUIEditBrand;
import client.core.gui.taks.OptionTask;

public abstract class BaseBrand<ResultType> extends OptionTask<ResultType>
{
	protected GUIEditBrand gui = new GUIEditBrand();

	public BaseBrand()
	{
		super(GUIEditBrand.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}

	protected boolean validate()
	{
		boolean valid = false;

		clearInputsBorders();

		if (this.gui.name.isEmpty())
		{
			showWarning(GUIEditBrand.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		}
		else
		{
			valid = true;
		}

		return valid;
	}
}