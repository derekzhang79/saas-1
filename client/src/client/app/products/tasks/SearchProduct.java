package client.app.products.tasks;

import share.app.brands.Brand;
import share.app.products.Product;
import share.app.sections.Section;
import client.app.brands.tasks.SearchBrand;
import client.app.products.gui.def.GUISearchProduct;
import client.app.products.operations.OperationsProducts;
import client.app.sections.tasks.SearchSection;
import client.core.gui.OptionTask;

public class SearchProduct extends OptionTask<Product> {
	
	private GUISearchProduct gui = new GUISearchProduct();
	
	private int sectionID = 0;
	private int brandID = 0;
	
	public SearchProduct() {
		super(GUISearchProduct.PATH, TaskType.MODAL);
	}
	
	public void start() {
		setGUI(this.gui);
		refreshProducts();
	}
	
	private void refreshProducts() {
		this.gui.list.setRows(OperationsProducts.call().getProducts(this.gui.barCode.getLong(), this.sectionID, this.brandID));
	}
	
	private void select() {
		close((Product)this.gui.list.getCurrentRow());
	}
	
	private void searchSection() {
		SearchSection task = new SearchSection();
		Section section = task.run();
		
		if (section != null) {
			this.sectionID = section.id;
			this.gui.sectionName.set(section.name);
		}
		
		this.gui.sectionName.focus();
	}
	
	private void clearSearchSection() {
		this.sectionID = 0;
		this.gui.sectionName.clear();
	}
	
	private void searchBrand() {
		SearchBrand task = new SearchBrand();
		Brand brand = task.run();
		
		if (brand != null) {
			this.brandID = brand.id;
			this.gui.brandName.set(brand.name);
		}
		
		this.gui.brandName.focus();
	}
	
	private void clearSearchBrand() {
		this.brandID = 0;
		this.gui.brandName.clear();
	}
	
	private void clean() {
		this.gui.list.cleanSearch();
	}
	
	private void addProduct() {
		AddProduct task = new AddProduct();
		Product response = task.run();
		
		if (response != null) {
			close(response);
		}
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ADD:
				addProduct();
				break;
			
			case SEARCH:
				refreshProducts();
				break;
			
			case SELECT:
				select();
				break;
			
			case SEARCH_SECTION:
				searchSection();
				break;
			
			case CLEAR_SEARCH_SECTION:
				clearSearchSection();
				break;
			
			case SEARCH_BRAND:
				searchBrand();
				break;
			
			case CLEAR_SEARCH_BRAND:
				clearSearchBrand();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUISearchProduct.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUISearchProduct.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}