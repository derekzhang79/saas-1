package server.app.database.tables;

import java.sql.Connection;
import server.core.database.kernel.Table;
import server.core.database.tables.TableDictionary;
import share.app.dictionary.Category.Categories;
import share.app.support.Support;
import share.core.objects.Date;

public class TableSupport extends Table
{
	public Integer id = new Integer(0);
	public Integer user = new Integer(0);
	public String module = new String();
	public String status = new String();
	public Date date_creation = new Date();
	public Date date_modification = new Date();
	public String name = new String();
	public String description = new String();

	public TableSupport(Connection connection)
	{
		super(connection, "SUPPORT");
		setTable(this);
	}

	public Support[] getSupports(Date dateCreationParam, String statusParam, Connection appConnection)
	{

		if (!statusParam.isEmpty())
		{
			this.status = statusParam;
		}

		if (!dateCreationParam.isEmpty())
		{
			this.date_creation = dateCreationParam;
		}

		int number = search("id");

		Support[] result = new Support[number];

		for (int i = 0; i < number; i++)
		{
			select(i);
			result[i] = new Support(this.id, this.user, this.module, getModuleDescription(this.module, appConnection), this.status, getStatusDescription(this.status, appConnection), this.date_creation, this.date_modification, this.name, this.description);
		}

		return result;
	}

	public boolean add(Support support)
	{
		this.user = support.user;
		this.module = support.module;
		this.status = support.status;
		this.date_creation = support.dateCreation;
		this.date_modification = support.dateModification;
		this.name = support.name;
		this.description = support.description;

		return create();
	}

	public boolean edit(Support original, Support newSupport)
	{
		boolean valid = false;

		this.id = original.id;

		if (read())
		{
			this.module = newSupport.module;
			this.date_modification = newSupport.dateModification;
			this.name = newSupport.name;
			this.description = newSupport.description;

			valid = update();
		}

		return valid;
	}

	private String getModuleDescription(String code, Connection appConnection)
	{
		TableDictionary table = new TableDictionary(appConnection);

		return table.getDescription(Categories.SYSTEM_MODULES.toString(), code);
	}

	private String getStatusDescription(String code, Connection appConnection)
	{
		TableDictionary table = new TableDictionary(appConnection);

		return table.getDescription(Categories.SUPPORT_STATUS.toString(), code);
	}
}