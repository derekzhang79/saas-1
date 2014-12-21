package share.app.support;

import share.core.Date;
import share.core.Shareable;

public class Support extends Shareable
{
	private static final long serialVersionUID = 7898567676423828668L;
	
	public final int id;
	public final int user;
	public final String module;
	public final String status;
	public final Date dateCreation;
	public final Date dateModification;
	public final String name;
	public final String description;
	public final String moduleDescription;
	public final String statusDescription;

	public Support(int id, int user, String module, String status, Date dateCreation, Date dateModification, String name, String description)
	{
		this.id = id;
		this.user = user;
		this.module = module;
		this.moduleDescription = "";
		this.status = status;
		this.statusDescription = "";
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.name = name;
		this.description = description;
	}

	public Support(int id, int user, String module, String moduleDescription, String status, String statusDescription, Date dateCreation, Date dateModification, String name, String description)
	{
		this.id = id;
		this.user = user;
		this.module = module;
		this.moduleDescription = moduleDescription;
		this.status = status;
		this.statusDescription = statusDescription;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.name = name;
		this.description = description;
	}

	public String getDateCreationString()
	{
		return this.dateCreation.toString();
	}
}