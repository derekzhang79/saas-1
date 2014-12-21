package share.app.products;

import share.core.utils.Shareable;

public class Product extends Shareable
{
	private static final long serialVersionUID = -895002993476552812L;
	
	public final int id;
	public final int section;
	public final long barCode;
	public final String name;
	public final String description;
	public final double costPrice;
	public final double salePrice;
	public final String tax;
	public final int brand;
	public final String model;
	public final String color;
	public final String measuringUnit;
	public final int length;
	public final int quantity;
	public final String sectionDescription;
	public final String brandName;
	public final double taxValue;

	public Product(int id, int section, String sectionName, long barCode, String name, String description, double cost_price, double sale_price, String tax, double taxValue, int brand, String brandName, String model, String color, String measuringUnit, int length, int quantity)
	{
		this.id = id;
		this.section = section;
		this.sectionDescription = sectionName;
		this.barCode = barCode;
		this.name = name;
		this.description = description;
		this.costPrice = cost_price;
		this.salePrice = sale_price;
		this.tax = tax;
		this.taxValue = taxValue;
		this.brand = brand;
		this.brandName = brandName;
		this.model = model;
		this.color = color;
		this.measuringUnit = measuringUnit;
		this.length = length;
		this.quantity = quantity;
	}
}