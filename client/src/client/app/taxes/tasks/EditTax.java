package client.app.taxes.tasks;

import share.app.dictionary.Category.Categories;
import share.app.taxes.Tax;
import client.app.system.dictionary.DictionaryManager;
import client.app.taxes.gui.def.GUIEditTax;
import client.app.taxes.operations.OperationsTaxes;

public class EditTax extends BaseTax
{
	private final Tax original;
	
	public EditTax(Tax original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditTax.Literals.TITLE_EDIT_TAX));
		this.gui.type.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.type.set(this.original.type);
		this.gui.value.set(this.original.value);
		this.gui.start.set(this.original.start);
		this.gui.type.focus();
	}
	
	private void editTax()
	{
		if (validate())
		{
			Tax newTax = new Tax(0, this.gui.type.get(), "", this.gui.value.getValue(), this.gui.start.get());
			boolean response = OperationsTaxes.call().editTax(this.original, newTax);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditTax.Literals.TAX_NOT_EDITED);
				this.gui.type.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditTax.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.type.equals(this.original.type)) || (!this.gui.value.equals(this.original.value)) || (!this.gui.start.equals(this.original.start)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editTax();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}