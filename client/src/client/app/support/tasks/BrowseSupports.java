package client.app.support.tasks;

import share.app.dictionary.Category;
import share.app.dictionary.Category.Categories;
import share.app.support.Support;
import client.app.support.gui.def.GUIBrowseSupports;
import client.app.support.operations.OperationsSupports;
import client.app.system.dictionary.DictionaryManager;
import client.core.gui.OptionTask;

public class BrowseSupports extends OptionTask<Void> {
	
	private GUIBrowseSupports gui = new GUIBrowseSupports();
	
	public BrowseSupports() {
		super(GUIBrowseSupports.PATH, TaskType.SINGLE);
	}
	
	public void start() {
		setGUI(this.gui);
		this.gui.status.setItems(DictionaryManager.get(Categories.SUPPORT_STATUS));
		this.gui.status.set(Category.SUPPORT_STATUS.PENDING);
		refreshSupports();
	}
	
	private void refreshSupports() {
		if (this.gui.dateCreation.isEmpty()) {
			this.gui.dateCreation.clear();
		}
		
		this.gui.list.setRows(OperationsSupports.call().getSupports(this.gui.dateCreation.get(), this.gui.status.get()));
	}
	
	private void addSupport() {
		AddSupport task = new AddSupport();
		Boolean response = task.run();
		
		if (valid(response)) {
			refreshSupports();
		}
		
		this.gui.list.focus();
	}
	
	private void editSupport() {
		if (this.gui.list.isRowSelected()) {
			
			Support current = (Support)this.gui.list.getCurrentRow();
			EditSupport task = new EditSupport(current);
			Boolean response = task.run();
			
			if (valid(response)) {
				refreshSupports();
			}
			
		} else {
			showWarning(GUIBrowseSupports.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addSupport();
				break;
			
			case EDIT:
				editSupport();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case SEARCH:
				refreshSupports();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseSupports.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseSupports.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}