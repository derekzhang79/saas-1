package client.app.workshop.tasks;

import share.app.workshop.FixOrderDetail;
import client.app.workshop.gui.def.GUIDeleteFixOrderDetail;
import client.app.workshop.operations.OperationsWorkshop;
import client.core.gui.taks.OptionTask;

public class DeleteFixOrderDetail extends OptionTask<Boolean> {
	
	private FixOrderDetail fixOrderDetail = null;
	
	public DeleteFixOrderDetail(FixOrderDetail fixOrderDetail) {
		super(GUIDeleteFixOrderDetail.PATH, TaskType.SINGLE);
		
		this.fixOrderDetail = fixOrderDetail;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteFixOrderDetail.Literals.ASK_DELETE, this.fixOrderDetail.detail))) {
			
			Boolean response = OperationsWorkshop.call().deleteFixOrderDetail(this.fixOrderDetail);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}