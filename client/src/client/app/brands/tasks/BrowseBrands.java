package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUIBrowseBrands;
import client.app.brands.operations.OperationsBrands;
import client.core.gui.taks.OptionTask;

public class BrowseBrands extends OptionTask<Void> {
	
	private GUIBrowseBrands gui = new GUIBrowseBrands();
	
	public BrowseBrands() {
		super(GUIBrowseBrands.PATH, TaskType.SINGLE);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshBrands();
	}
	
	private void refreshBrands() {
		this.gui.list.setRows(OperationsBrands.call().getBrands());
	}
	
	private void addBrand() {
		AddBrand task = new AddBrand();
		Brand response = task.run();
		
		if (response != null) {
			refreshBrands();
		}
		
		this.gui.list.focus();
	}
	
	private void editBrand() {
		if (this.gui.list.isRowSelected()) {
			
			Brand current = (Brand)this.gui.list.getCurrentRow();
			EditBrand task = new EditBrand(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshBrands();
			}
			
		} else {
			showWarning(GUIBrowseBrands.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteBrand() {
		if (this.gui.list.isRowSelected()) {
			
			Brand current = (Brand)this.gui.list.getCurrentRow();
			DeleteBrand task = new DeleteBrand(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshBrands();
			}
			
		} else {
			showWarning(GUIBrowseBrands.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addBrand();
				break;
			
			case EDIT:
				editBrand();
				break;
			
			case DELETE:
				deleteBrand();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseBrands.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseBrands.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}