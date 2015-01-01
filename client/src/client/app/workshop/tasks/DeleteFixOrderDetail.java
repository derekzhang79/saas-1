package client.app.workshop.tasks;

import share.app.workshop.FixOrderDetail;
import client.app.workshop.gui.def.GUIDeleteFixOrderDetail;
import client.app.workshop.operations.OperationsWorkshop;
import client.core.gui.taks.Activity;

public class DeleteFixOrderDetail extends Activity<Boolean>
{
	private final FixOrderDetail fixOrderDetail;
	
	public DeleteFixOrderDetail(FixOrderDetail fixOrderDetail)
	{
		super(GUIDeleteFixOrderDetail.PATH, Type.SINGLE);
		
		this.fixOrderDetail = fixOrderDetail;
	}
	
	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteFixOrderDetail.Literals.ASK_DELETE, this.fixOrderDetail.detail)))
		{
			Boolean response = OperationsWorkshop.call().deleteFixOrderDetail(this.fixOrderDetail);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}