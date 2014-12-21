package client.app.workshop.tasks;

import share.app.dictionary.Category.Categories;
import share.app.workshop.FixOrder;
import client.app.system.dictionary.DictionaryManager;
import client.app.workshop.gui.def.GUIEditFixOrder;
import client.app.workshop.operations.OperationsWorkshop;

public class EditFixOrder extends BaseFixOrder<Boolean>
{
	private final FixOrder original;
	
	public EditFixOrder(FixOrder original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditFixOrder.Literals.TITLE_EDIT_FIX_ORDER));
		
		this.clientID = this.original.client;
		
		this.gui.clientName.set(this.original.clientName);
		this.gui.start.set(this.original.start);
		this.gui.finish.set(this.original.finish);
		this.gui.status.setItems(DictionaryManager.get(Categories.FIX_ORDER_STATUS));
		this.gui.status.set(this.original.status);
		this.gui.comments.set(this.original.comments);
		
		this.gui.clientName.focus();
	}
	
	private void editFixOrder()
	{
		if (validate())
		{
			FixOrder newFixOrder = new FixOrder(0, this.clientID, "", this.gui.status.get(), "", this.gui.start.get(), this.gui.finish.get(), this.gui.comments.get(), 0);
			boolean response = OperationsWorkshop.call().editFixOrder(this.original, newFixOrder);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditFixOrder.Literals.FIX_ORDER_NOT_EDITED);
				this.gui.clientName.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditFixOrder.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.clientName.equals(this.original.clientName)) || (!this.gui.start.equals(this.original.start)) || (!this.gui.finish.equals(this.original.finish)) || (!this.gui.status.equals(this.original.status)) || (!this.gui.comments.equals(this.original.comments)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editFixOrder();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_CLIENT:
				searchClient();
				break;
			
			default:
				break;
		}
	}
}