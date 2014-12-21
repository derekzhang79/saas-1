package client.app.taxes.tasks;

import share.app.taxes.Tax;
import client.app.taxes.gui.def.GUIBrowseTaxes;
import client.app.taxes.operations.OperationsTaxes;
import client.core.gui.taks.OptionTask;

public class BrowseTaxes extends OptionTask<Void> {
	
	private GUIBrowseTaxes gui = new GUIBrowseTaxes();
	
	public BrowseTaxes() {
		super(GUIBrowseTaxes.PATH, TaskType.SINGLE);
	}
	
	public BrowseTaxes(TaskType type) {
		super(GUIBrowseTaxes.PATH, type);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshTaxes();
	}
	
	private void refreshTaxes() {
		this.gui.list.setRows(OperationsTaxes.call().getTaxes());
	}
	
	private void addTax() {
		AddTax task = new AddTax();
		Boolean response = task.run();
		
		if (valid(response)) {
			refreshTaxes();
		}
		
		this.gui.list.focus();
	}
	
	private void editTax() {
		if (this.gui.list.isRowSelected()) {
			
			Tax current = (Tax)this.gui.list.getCurrentRow();
			EditTax task = new EditTax(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshTaxes();
			}
			
		} else {
			showWarning(GUIBrowseTaxes.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteTax() {
		if (this.gui.list.isRowSelected()) {
			
			Tax current = (Tax)this.gui.list.getCurrentRow();
			DeleteTax task = new DeleteTax(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshTaxes();
			}
			
		} else {
			showWarning(GUIBrowseTaxes.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addTax();
				break;
			
			case EDIT:
				editTax();
				break;
			
			case DELETE:
				deleteTax();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseTaxes.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseTaxes.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}