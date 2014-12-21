package share.app.contacts.suppliers;

import share.app.contacts.Contact;

public class Supplier extends Contact
{
	private static final long serialVersionUID = 5047423386594292924L;
	
	public final int id;
	public final String name;
	public final String contactPerson;

	public Supplier(int id, String name, String identification, String address, String city, int postalCode, int telephone, int mobile, String email, String contactPerson, String comments, int contactID)
	{
		super(contactID, identification, address, city, postalCode, telephone, mobile, email, comments);

		this.id = id;
		this.name = name;
		this.contactPerson = contactPerson;
	}
}