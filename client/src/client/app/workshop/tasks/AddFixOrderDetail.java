package client.app.workshop.tasks;

import share.app.workshop.FixOrderDetail;
import client.app.workshop.gui.def.GUIEditFixOrderDetail;
import client.app.workshop.operations.OperationsWorkshop;

public class AddFixOrderDetail extends BaseFixOrderDetail
{
	private int fixOrderID = 0;
	
	public AddFixOrderDetail(int fixOrderID)
	{
		this.fixOrderID = fixOrderID;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditFixOrderDetail.Literals.TITLE_ADD_FIX_ORDER_DETAIL));
		this.gui.detail.focus();
	}
	
	private void addFixOrderDetail()
	{
		if (validate())
		{
			FixOrderDetail newFixOrderDetail = new FixOrderDetail(0, this.fixOrderID, 0, this.gui.quantity.getInt(), this.gui.detail.get(), this.gui.amount.getValue());
			boolean response = OperationsWorkshop.call().addFixOrderDetail(newFixOrderDetail);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditFixOrderDetail.Literals.FIX_ORDER_DETAIL_NOT_CREATED);
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
		return ((!this.gui.detail.isEmpty()) || (!this.gui.quantity.isEmpty()) || (!this.gui.amount.isEmpty()));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				addFixOrderDetail();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}