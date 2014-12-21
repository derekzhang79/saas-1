package client.app.workshop.tasks;

import share.app.workshop.FixOrderDetail;
import client.app.workshop.gui.def.GUIEditFixOrderDetail;
import client.app.workshop.operations.OperationsWorkshop;

public class EditFixOrderDetail extends BaseFixOrderDetail
{
	private final FixOrderDetail original;
	
	public EditFixOrderDetail(FixOrderDetail original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditFixOrderDetail.Literals.TITLE_EDIT_FIX_ORDER_DETAIL));
		
		this.gui.detail.set(this.original.detail);
		this.gui.quantity.set(this.original.quantity);
		this.gui.amount.set(this.original.amount);
		
		this.gui.detail.focus();
	}
	
	private void editFixOrderDetail()
	{
		if (validate())
		{
			FixOrderDetail newFixOrderDetail = new FixOrderDetail(this.original.id, this.original.fixOrder, this.original.line, this.gui.quantity.getInt(), this.gui.detail.get(), this.gui.amount.getValue());
			boolean response = OperationsWorkshop.call().editFixOrderDetail(this.original, newFixOrderDetail);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditFixOrderDetail.Literals.FIX_ORDER_DETAIL_NOT_EDITED);
				this.gui.detail.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditFixOrderDetail.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.detail.equals(this.original.detail)) || (!this.gui.quantity.equals(this.original.quantity)) || (!this.gui.amount.equals(this.original.amount)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editFixOrderDetail();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}