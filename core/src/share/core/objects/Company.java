package share.core.objects;

import share.core.Shareable;

public class Company extends Shareable
{
	private static final long serialVersionUID = -5042941455297810120L;

	private int clientID = 0;
	private String ownerName = "";
	private String companyName = "";
	private String address = "";
	private String city = "";
	private int postalCode = 0;
	private int telephone = 0;
	private int fax = 0;
	private String identification = "";
	private String email = "";
	private String webpage = "";
	private String ddbb = "";

	public Company()
	{
	}

	public Company(Integer clientID, String ownerName, String companyName, String address, String city, int postalCode, int telephone, int fax, String identification, String email, String webpage, String ddbb)
	{
		this.clientID = clientID;
		this.ownerName = ownerName;
		this.companyName = companyName;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.telephone = telephone;
		this.fax = fax;
		this.identification = identification;
		this.email = email;
		this.webpage = webpage;
		this.ddbb = ddbb;
	}

	public int getClientID()
	{
		return this.clientID;
	}

	public String getOwnerName()
	{
		return this.ownerName;
	}

	public String getCompanyName()
	{
		return this.companyName;
	}

	public String getAddress()
	{
		return this.address;
	}

	public String getCity()
	{
		return this.city;
	}

	public int getPostalCode()
	{
		return this.postalCode;
	}

	public int getTelephone()
	{
		return this.telephone;
	}

	public int getFax()
	{
		return this.fax;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getWebPage()
	{
		return this.webpage;
	}

	public String getIdentification()
	{
		return this.identification;
	}

	public String getDatabase()
	{
		return this.ddbb;
	}

	public String getPostCodeCity()
	{
		String result = this.city;

		if (this.postalCode != 0)
		{
			if (!result.isEmpty())
			{
				result += " (" + this.postalCode + ")";
			}
			else
			{
				result += this.postalCode;
			}
		}

		return result.trim();
	}

}