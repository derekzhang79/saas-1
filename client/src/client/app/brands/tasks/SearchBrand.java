package client.app.brands.tasks;

import share.app.brands.Brand;
import client.app.brands.gui.def.GUISearchBrand;
import client.app.brands.operations.OperationsBrands;
import client.core.gui.taks.OptionTask;

public class SearchBrand extends OptionTask<Brand> {
	
	private GUISearchBrand gui = new GUISearchBrand();
	
	public SearchBrand() {
		super(GUISearchBrand.PATH, TaskType.MODAL);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshBrands();
	}
	
	private void refreshBrands() {
		this.gui.list.setRows(OperationsBrands.call().getBrands());
	}
	
	private void select() {
		close((Brand)this.gui.list.getCurrentRow());
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	private void addBrand() {
		AddBrand task = new AddBrand();
		Brand response = task.run();
		
		if (response != null) {
			close(response);
		}
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addBrand();
				break;
			
			case SELECT:
				select();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUISearchBrand.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUISearchBrand.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}