package share.app.contacts.clients;

import share.app.contacts.Contact;

public class Client extends Contact
{
	private static final long serialVersionUID = -4691600154650008051L;

	public final int id;
	public final String firstName;
	public final String lastName;
	public final String name;

	public Client(int id, String firstName, String lastName, String identification, String address, String city, int postalCode, int telephone, int mobile, String email, String comments, int contactID)
	{
		super(contactID, identification, address, city, postalCode, telephone, mobile, email, comments);

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;

		this.name = (firstName + " " + lastName).trim();
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