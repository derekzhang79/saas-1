package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.products.Product;
import share.core.objects.Date;

public class TableProduct extends Table
{
	public Integer id = new Integer(0);
	public Integer section = new Integer(0);
	public Long bar_code = new Long(0);
	public String name = new String();
	public String description = new String();
	public Double cost_price = new Double(0);
	public Double sale_price = new Double(0);
	public String tax = new String();
	public Integer brand = new Integer(0);
	public String model = new String();
	public String color = new String();
	public String measuring_unit = new String();
	public Integer length = new Integer(0);
	public Integer quantity = new Integer(0);

	public TableProduct(Connection connection)
	{
		super(connection, "PRODUCT");
		setTable(this);
	}

	public Product[] getProducts(Long barCodeParam, Integer sectionIDParam, Integer brandParam)
	{
		if (barCodeParam != 0)
		{
			this.bar_code = barCodeParam;
		}

		if (sectionIDParam != 0)
		{
			this.section = sectionIDParam;
		}

		if (brandParam != 0)
		{
			this.brand = brandParam;
		}

		int number = search("id");

		Product[] result = new Product[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Product(this.id, this.section, getSectionName(this.section), this.bar_code, this.name, this.description, this.cost_price, this.sale_price, this.tax, getTaxValue(this.tax, Date.getTodayDate()), this.brand, getBrandName(this.brand), this.model, this.color, this.measuring_unit, this.length, this.quantity);
		}

		return result;
	}

	public Product add(Product product)
	{
		Product result = null;

		this.section = product.section;
		this.bar_code = product.barCode;
		this.name = product.name;
		this.description = product.description;
		this.cost_price = product.costPrice;
		this.sale_price = product.salePrice;
		this.tax = product.tax;
		this.brand = product.brand;
		this.model = product.model;
		this.color = product.color;
		this.measuring_unit = product.measuringUnit;
		this.length = product.length;
		this.quantity = product.quantity;

		if (create())
		{
			result = new Product(getLastId(), this.section, getSectionName(this.section), this.bar_code, this.name, this.description, this.cost_price, this.sale_price, this.tax, getTaxValue(this.tax, Date.getTodayDate()), this.brand, getBrandName(this.brand), this.model, this.color, this.measuring_unit, this.length, this.quantity);
		}

		return result;
	}

	public boolean edit(Product original, Product newProduct)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.section = newProduct.section;
			this.bar_code = newProduct.barCode;
			this.name = newProduct.name;
			this.description = newProduct.description;
			this.cost_price = newProduct.costPrice;
			this.sale_price = newProduct.salePrice;
			this.tax = newProduct.tax;
			this.brand = newProduct.brand;
			this.model = newProduct.model;
			this.color = newProduct.color;
			this.measuring_unit = newProduct.measuringUnit;
			this.length = newProduct.length;
			this.quantity = newProduct.quantity;

			valid = update();
		}

		return valid;
	}

	public boolean delete(Product product)
	{
		boolean valid = false;

		this.id = product.id;

		if (read())
		{
			valid = delete();
		}

		return valid;
	}

	private String getSectionName(int sectionID)
	{
		TableSection table = new TableSection(getConnection());

		return table.getName(sectionID);
	}

	private String getBrandName(int brandID)
	{
		TableBrand table = new TableBrand(getConnection());

		return table.getName(brandID);
	}

	private double getTaxValue(String type, Date date)
	{
		TableTax table = new TableTax(getConnection());

		return table.getTaxValue(type, date);
	}
}