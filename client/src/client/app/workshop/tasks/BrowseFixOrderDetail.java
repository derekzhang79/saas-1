package client.app.workshop.tasks;

import share.app.workshop.FixOrder;
import share.app.workshop.FixOrderDetail;
import client.app.workshop.gui.def.GUIBrowseFixOrderDetail;
import client.app.workshop.operations.OperationsWorkshop;
import client.core.gui.taks.OptionTask;

public class BrowseFixOrderDetail extends OptionTask<Void>
{
	private final GUIBrowseFixOrderDetail gui = new GUIBrowseFixOrderDetail();
	
	private final FixOrder fixOrder;
	
	public BrowseFixOrderDetail(FixOrder fixOrder)
	{
		super(GUIBrowseFixOrderDetail.PATH, TaskType.MODAL);
		
		this.fixOrder = fixOrder;
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshFixOrdersDetail();
	}
	
	private void refreshFixOrdersDetail()
	{
		this.gui.list.setRows(OperationsWorkshop.call().getFixOrdersDetail(this.fixOrder.id));
	}
	
	private void addFixOrderDetail()
	{
		AddFixOrderDetail task = new AddFixOrderDetail(this.fixOrder.id);
		Boolean response = task.run();
		
		if (valid(response))
		{
			refreshFixOrdersDetail();
		}
		
		this.gui.list.focus();
	}
	
	private void editFixOrderDetail()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrderDetail current = (FixOrderDetail)this.gui.list.getCurrentRow();
			EditFixOrderDetail task = new EditFixOrderDetail(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshFixOrdersDetail();
			}
		}
		else
		{
			showWarning(GUIBrowseFixOrderDetail.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteFixOrderDetail()
	{
		if (this.gui.list.isRowSelected())
		{
			FixOrderDetail current = (FixOrderDetail)this.gui.list.getCurrentRow();
			DeleteFixOrderDetail task = new DeleteFixOrderDetail(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshFixOrdersDetail();
			}
		}
		else
		{
			showWarning(GUIBrowseFixOrderDetail.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean()
	{
		this.gui.list.cleanSearch();
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case ADD:
				addFixOrderDetail();
				break;
			
			case EDIT:
				editFixOrderDetail();
				break;
			
			case DELETE:
				deleteFixOrderDetail();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseFixOrderDetail.Literals.LIST_PDF) + " " + this.fixOrder.id, this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseFixOrderDetail.Literals.LIST_PDF) + " " + this.fixOrder.id, this.gui.list);
				break;
			
			default:
				break;
		}
	}
}