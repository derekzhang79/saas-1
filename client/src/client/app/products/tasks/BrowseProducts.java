package client.app.products.tasks;

import share.app.brands.Brand;
import share.app.products.Product;
import share.app.sections.Section;
import client.app.brands.tasks.SearchBrand;
import client.app.products.gui.def.GUIBrowseProducts;
import client.app.products.operations.OperationsProducts;
import client.app.sections.tasks.SearchSection;
import client.core.gui.taks.Activity;

public class BrowseProducts extends Activity<Void>
{
	private final GUIBrowseProducts gui = new GUIBrowseProducts();
	
	private int sectionID = 0;
	private int brandID = 0;
	
	public BrowseProducts()
	{
		super(GUIBrowseProducts.PATH, Type.SINGLE);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		refreshProducts();
	}
	
	private void refreshProducts()
	{
		this.gui.list.setRows(OperationsProducts.call().getProducts(this.gui.barCode.getLong(), this.sectionID, this.brandID));
	}
	
	private void addProduct()
	{
		AddProduct task = new AddProduct();
		Product response = task.run();
		
		if (response != null)
		{
			refreshProducts();
		}
		
		this.gui.list.focus();
	}
	
	private void editProduct()
	{
		if (this.gui.list.isRowSelected())
		{
			Product current = (Product)this.gui.list.getCurrentRow();
			EditProduct task = new EditProduct(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshProducts();
			}
		}
		else
		{
			showWarning(GUIBrowseProducts.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void deleteProduct()
	{
		if (this.gui.list.isRowSelected())
		{
			Product current = (Product)this.gui.list.getCurrentRow();
			DeleteProduct task = new DeleteProduct(current);
			Boolean response = task.run();
			
			if (valid(response))
			{
				refreshProducts();
			}
		}
		else
		{
			showWarning(GUIBrowseProducts.Literals.ROW_NOT_SELECTED);
		}
		
		this.gui.list.focus();
	}
	
	private void searchSection()
	{
		SearchSection task = new SearchSection();
		Section section = task.run();
		
		if (section != null)
		{
			this.sectionID = section.id;
			this.gui.sectionName.set(section.name);
		}
		
		this.gui.sectionName.focus();
	}
	
	private void clearSearchSection()
	{
		this.sectionID = 0;
		this.gui.sectionName.clear();
	}
	
	private void searchBrand()
	{
		SearchBrand task = new SearchBrand();
		Brand brand = task.run();
		
		if (brand != null)
		{
			this.brandID = brand.id;
			this.gui.brandName.set(brand.name);
		}
		
		this.gui.brandName.focus();
	}
	
	private void clearSearchBrand()
	{
		this.brandID = 0;
		this.gui.brandName.clear();
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
				addProduct();
				break;
			
			case EDIT:
				editProduct();
				break;
			
			case DELETE:
				deleteProduct();
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
			
			case SEARCH:
				refreshProducts();
				break;
			
			case CLEAR:
				clean();
				break;
			
			case EXCEL:
				exportExcel(getLiteral(GUIBrowseProducts.Literals.LIST_PDF), this.gui.list);
				break;
			
			case PDF:
				exportPDF(getLiteral(GUIBrowseProducts.Literals.LIST_PDF), this.gui.list);
				break;
			
			default:
				break;
		}
	}
}