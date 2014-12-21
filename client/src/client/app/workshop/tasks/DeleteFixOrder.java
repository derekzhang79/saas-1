package client.app.workshop.tasks;

import share.app.workshop.FixOrder;
import client.app.workshop.gui.def.GUIDeleteFixOrder;
import client.app.workshop.operations.OperationsWorkshop;
import client.core.gui.taks.OptionTask;

public class DeleteFixOrder extends OptionTask<Boolean> {
	
	private FixOrder fixOrder = null;
	
	public DeleteFixOrder(FixOrder fixOrder) {
		super(GUIDeleteFixOrder.PATH, TaskType.SINGLE);
		
		this.fixOrder = fixOrder;
	}
	
	public void start() {
		if (showConfirmLiteral(getLiteral(GUIDeleteFixOrder.Literals.ASK_DELETE, this.fixOrder.clientName))) {
			
			Boolean response = OperationsWorkshop.call().deleteFixOrder(this.fixOrder);
			close(valid(response));
			
		} else {
			close(false);
		}
	}
}