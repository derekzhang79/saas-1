package client.app.products.tasks;

import share.app.dictionary.Category.Categories;
import share.app.products.Product;
import client.app.products.gui.def.GUIEditProduct;
import client.app.products.operations.OperationsProducts;
import client.app.system.dictionary.DictionaryManager;

public class EditProduct extends BaseProduct<Boolean>
{
	private final Product original;
	
	public EditProduct(Product original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditProduct.Literals.TITLE_EDIT_PRODUCT));
		
		this.sectionID = this.original.section;
		this.brandID = this.original.brand;
		
		this.gui.name.set(this.original.name);
		this.gui.sectionName.set(this.original.sectionDescription);
		this.gui.barCode.set(this.original.barCode);
		this.gui.costPrice.set(this.original.costPrice);
		this.gui.salePrice.set(this.original.salePrice);
		this.gui.tax.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.tax.set(this.original.tax);
		this.gui.brandName.set(this.original.brandName);
		this.gui.model.set(this.original.model);
		this.gui.measuringUnit.setItems(DictionaryManager.get(Categories.MEASURING_UNIT));
		this.gui.measuringUnit.set(this.original.measuringUnit);
		this.gui.length.set(this.original.length);
		this.gui.color.set(this.original.color);
		this.gui.quantity.set(this.original.quantity);
		this.gui.description.set(this.original.description);
		
		this.gui.name.focus();
	}
	
	private void editProduct()
	{
		if (validate())
		{
			Product newProduct = new Product(0, this.sectionID, this.gui.sectionName.get(), this.gui.barCode.getLong(), this.gui.name.get(), this.gui.description.get(), this.gui.costPrice.getValue(), this.gui.salePrice.getValue(), this.gui.tax.get(), 0, this.brandID, "", this.gui.model.get(), this.gui.color.get(), this.gui.measuringUnit.get(), this.gui.length.getInt(), this.gui.quantity.getInt());
			boolean response = OperationsProducts.call().editProduct(this.original, newProduct);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditProduct.Literals.PRODUCT_NOT_EDITED);
				this.gui.name.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditProduct.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.name.equals(this.original.name)) || (!this.gui.sectionName.equals(this.original.sectionDescription)) || (!this.gui.barCode.equals(this.original.barCode)) || (!this.gui.tax.equals(this.original.tax)) || (!this.gui.costPrice.equals(this.original.costPrice)) || (!this.gui.salePrice.equals(this.original.salePrice)) || (!this.gui.brandName.equals(this.original.brandName)) || (!this.gui.model.equals(this.original.model)) || (!this.gui.measuringUnit.equals(this.original.measuringUnit)) || (!this.gui.length.equals(this.original.length)) || (!this.gui.color.equals(this.original.color)) || (!this.gui.quantity.equals(this.original.quantity)) || (!this.gui.description.equals(this.original.description)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editProduct();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_SECTION:
				searchSection();
				break;
			
			case SEARCH_BRAND:
				searchBrand();
				break;
			
			default:
				break;
		}
	}
}