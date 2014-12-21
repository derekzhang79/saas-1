package server.app.database.tables;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import server.core.database.kernel.Table;
import server.core.database.tables.TableDictionary;
import share.app.dictionary.Category.Categories;
import share.app.taxes.Tax;
import share.core.objects.Date;

public class TableTax extends Table
{
	public Integer id = new Integer(0);
	public String type = new String();
	public Double value = new Double(0);
	public Date start = new Date();

	public TableTax(Connection connection)
	{
		super(connection, "TAX");
		setTable(this);
	}

	public Tax[] getTaxes()
	{
		int number = search("id");

		Tax[] result = new Tax[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Tax(this.id, this.type, getTaxTypeDescription(this.type), this.value, this.start);
		}

		return result;
	}

	public Tax[] getTaxesFrom(Date from)
	{
		List<Tax> list = new ArrayList<Tax>();
		int number = search("start DESC");

		for (int i = 0; i < number; i++)
		{
			select(i);

			if (from.isAfter(this.start))
			{
				list.add(new Tax(this.id, this.type, getTaxTypeDescription(this.type), this.value, this.start));
			}
		}

		Tax[] result = new Tax[list.size()];
		list.toArray(result);

		return result;
	}

	public boolean add(Tax tax)
	{
		this.type = tax.type;
		this.value = tax.value;
		this.start = tax.start;

		return create();
	}

	public boolean edit(Tax original, Tax newTax)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.type = newTax.type;
			this.value = newTax.value;
			this.start = newTax.start;

			valid = update();
		}

		return valid;
	}

	public boolean delete(Tax tax)
	{
		boolean valid = false;

		this.id = tax.id;

		if (read())
		{
			valid = delete();
		}

		return valid;
	}

	public double getTaxValue(String typeTax, Date date)
	{
		double result = 0;

		this.type = typeTax;

		int number = search("start DESC");

		for (int i = 0; i < number; i++)
		{
			select(i);

			if (date.isAfter(this.start))
			{
				result = this.value;
			}
		}

		return result;
	}

	private String getTaxTypeDescription(String typeTax)
	{
		TableDictionary table = new TableDictionary(getConnection());

		return table.getDescription(Categories.TAX.toString(), typeTax);
	}
}