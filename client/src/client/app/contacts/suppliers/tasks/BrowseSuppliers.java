package client.app.contacts.suppliers.tasks;

import share.app.contacts.suppliers.Supplier;
import client.app.contacts.suppliers.gui.def.GUIBrowseSuppliers;
import client.app.contacts.suppliers.operations.OperationsSuppliers;
import client.core.gui.taks.Activity;

public class BrowseSuppliers extends Activity<Void>
{
	private final GUIBrowseSuppliers gui = new GUIBrowseSuppliers();
	
	public BrowseSuppliers()
	{
		super(GUIBrowseSuppliers.PATH, Type.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshSuppliers();
	}
	
	private void refreshSuppliers()
	{
		this.gui.list.setRows(OperationsSuppliers.call().getSuppliers());
	}
	
	private void addSupplier()
	{
		AddSupplier task = new AddSupplier();
		Boolean response = task.run();
		
		if (valid(response))
		{
			refreshSuppliers();
		}
		
		this.gui.list.focus();
	}
	
	private void editSupplier()
	{
		if (this.gui.list.isRowSelected())
		{
			
			Supplier current = (Supplier)this.gui.list.getCurrentRow();
			EditSupplier task = new EditSupplier(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshSuppliers();
			}
		}
		else
		{
			showWarning(GUIBrowseSuppliers.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteSupplier()
	{
		if (this.gui.list.isRowSelected())
		{
			Supplier current = (Supplier)this.gui.list.getCurrentRow();
			DeleteSupplier task = new DeleteSupplier(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshSuppliers();
			}
		}
		else
		{
			showWarning(GUIBrowseSuppliers.Literals.ROW_NOT_SELECTED);
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
				addSupplier();
				break;
			
			case EDIT:
				editSupplier();
				break;
			
			case DELETE:
				deleteSupplier();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseSuppliers.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseSuppliers.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}