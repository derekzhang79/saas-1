package client.app.products.tasks;

import share.app.dictionary.Category.Categories;
import share.app.dictionary.Category.TAX;
import share.app.products.Product;
import client.app.products.gui.def.GUIEditProduct;
import client.app.products.operations.OperationsProducts;
import client.app.system.dictionary.DictionaryManager;

public class AddProduct extends BaseProduct<Product> {
	
	public void start() {
		setTitle(getLiteral(GUIEditProduct.Literals.TITLE_ADD_PRODUCT));
		this.gui.tax.setItems(DictionaryManager.get(Categories.TAX));
		this.gui.tax.set(TAX.GENERAL);
		this.gui.measuringUnit.setItems(DictionaryManager.get(Categories.MEASURING_UNIT));
		this.gui.measuringUnit.selectNone();
		this.gui.name.focus();
	}
	
	private void addProduct() {
		if (validate()) {
			Product newProduct = new Product(0, this.sectionID, this.gui.sectionName.get(), this.gui.barCode.getLong(), this.gui.name.get(), this.gui.description.get(), this.gui.costPrice.getValue(), this.gui.salePrice.getValue(), this.gui.tax.get(), 0, this.brandID, "", this.gui.model.get(), this.gui.color.get(), this.gui.measuringUnit.get(), this.gui.length.getInt(), this.gui.quantity.getInt());
			Product response = OperationsProducts.call().addProduct(newProduct);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditProduct.Literals.PRODUCT_NOT_CREATED);
				this.gui.name.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditProduct.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.name.isEmpty()) || (!this.gui.sectionName.isEmpty()) || (!this.gui.barCode.isEmpty()) || (!this.gui.costPrice.isEmpty()) || (!this.gui.salePrice.isEmpty()) || (!this.gui.brandName.isEmpty()) || (!this.gui.model.isEmpty()) || (!this.gui.measuringUnit.isEmpty()) || (!this.gui.length.isEmpty()) || (!this.gui.color.isEmpty()) || (!this.gui.quantity.isEmpty()) || (!this.gui.description.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addProduct();
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