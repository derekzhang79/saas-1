package client.app.products.tasks;

import java.awt.Color;

import share.app.brands.Brand;
import share.app.sections.Section;
import client.app.brands.tasks.SearchBrand;
import client.app.products.gui.def.GUIEditProduct;
import client.app.sections.tasks.SearchSection;
import client.core.gui.OptionTask;

public abstract class BaseProduct<ResultType> extends OptionTask<ResultType> {
	
	protected GUIEditProduct gui = new GUIEditProduct();
	
	protected int sectionID = 0;
	protected int brandID = 0;
	
	public BaseProduct() {
		super(GUIEditProduct.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.name.isEmpty()) {
			showWarning(GUIEditProduct.Literals.NAME_REQUIRED);
			this.gui.name.focus();
			this.gui.name.setBorderColor(Color.RED);
		} else if (this.gui.sectionName.isEmpty()) {
			showWarning(GUIEditProduct.Literals.SECTION_REQUIRED);
			this.gui.sectionName.focus();
			this.gui.sectionName.setBorderColor(Color.RED);
		} else if (this.gui.barCode.isEmpty()) {
			showWarning(GUIEditProduct.Literals.BAR_CODE_REQUIRED);
			this.gui.barCode.focus();
			this.gui.barCode.setBorderColor(Color.RED);
		} else if (this.gui.costPrice.isEmpty()) {
			showWarning(GUIEditProduct.Literals.COST_PRICE_REQUIRED);
			this.gui.costPrice.focus();
			this.gui.costPrice.setBorderColor(Color.RED);
		} else if (this.gui.salePrice.isEmpty()) {
			showWarning(GUIEditProduct.Literals.SALE_PRICE_REQUIRED);
			this.gui.salePrice.focus();
			this.gui.salePrice.setBorderColor(Color.RED);
		} else if (this.gui.tax.isEmpty()) {
			showWarning(GUIEditProduct.Literals.TAX_REQUIRED);
			this.gui.tax.focus();
			this.gui.tax.setBorderColor(Color.RED);
		} else if (this.gui.brandName.isEmpty()) {
			showWarning(GUIEditProduct.Literals.BRAND_REQUIRED);
			this.gui.brandName.focus();
			this.gui.brandName.setBorderColor(Color.RED);
		} else if (this.gui.model.isEmpty()) {
			showWarning(GUIEditProduct.Literals.MODEL_REQUIRED);
			this.gui.model.focus();
			this.gui.model.setBorderColor(Color.RED);
		} else if (this.gui.measuringUnit.isEmpty()) {
			showWarning(GUIEditProduct.Literals.MEASURING_UNIT_REQUIRED);
			this.gui.measuringUnit.focus();
			this.gui.measuringUnit.setBorderColor(Color.RED);
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	protected void searchSection() {
		SearchSection task = new SearchSection();
		Section section = task.run();
		
		if (section != null) {
			this.sectionID = section.id;
			this.gui.sectionName.set(section.name);
		}
		
		this.gui.sectionName.focus();
	}
	
	protected void searchBrand() {
		SearchBrand task = new SearchBrand();
		Brand brand = task.run();
		
		if (brand != null) {
			this.brandID = brand.id;
			this.gui.brandName.set(brand.name);
		}
		
		this.gui.sectionName.focus();
	}
}