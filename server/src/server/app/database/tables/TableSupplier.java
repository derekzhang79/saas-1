package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import share.app.contacts.suppliers.Supplier;

public class TableSupplier extends Table
{
	public Integer id = new Integer(0);
	public Integer contact = new Integer(0);

	public TableSupplier(Connection connection)
	{
		super(connection, "SUPPLIER");
		setTable(this);
	}

	public Supplier[] getSuppliers()
	{
		int number = search("id");

		Supplier[] result = new Supplier[number];

		for (int i = 0; i < number; i++)
		{
			select(i);

			TableContact table = new TableContact(getConnection());
			table.getContact(this.contact);

			result[i] = new Supplier(this.id, table.first_name, table.identification, table.address, table.city, table.postal_code, table.telephone, table.mobile, table.email, table.contact_person, table.comments, table.id);
		}

		return result;
	}

	public boolean add(Supplier supplier)
	{
		boolean valid = false;

		beginTransaction();

		TableContact table = new TableContact(getConnection());

		if (table.add(supplier.name, "", supplier.identification, supplier.address, supplier.city, supplier.postalCode, supplier.telephone, supplier.mobile, supplier.email, supplier.contactPerson, supplier.comments))
		{
			this.contact = table.id;

			if (create())
			{
				valid = commit();
			}
			else
			{
				rollback();
			}

		}
		else
		{
			rollback();
		}

		return valid;
	}

	public boolean edit(Supplier original, Supplier newSupplier)
	{
		TableContact table = new TableContact(getConnection());

		return table.edit(original.contactID, newSupplier.name, "", newSupplier.identification, newSupplier.address, newSupplier.city, newSupplier.postalCode, newSupplier.telephone, newSupplier.mobile, newSupplier.email, newSupplier.contactPerson, newSupplier.comments);
	}

	public boolean delete(Supplier supplier)
	{
		boolean valid = false;

		beginTransaction();

		this.id = supplier.id;

		if (read())
		{
			if (delete())
			{
				TableContact table = new TableContact(getConnection());

				if (table.delete(supplier.contactID))
				{
					valid = commit();
				}
				else
				{
					rollback();
				}
			}
			else
			{
				rollback();
			}
		}
		else
		{
			rollback();
		}

		return valid;
	}
}