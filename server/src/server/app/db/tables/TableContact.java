package server.app.db.tables;

import java.sql.Connection;
import server.core.db.Table;

public class TableContact extends Table
{
	public Integer id = new Integer(0);
	public String first_name = new String();
	public String last_name = new String();
	public String identification = new String();
	public String address = new String();
	public String city = new String();
	public Integer postal_code = new Integer(0);
	public Integer telephone = new Integer(0);
	public Integer mobile = new Integer(0);
	public String email = new String();
	public String contact_person = new String();
	public String comments = new String();

	public TableContact(Connection connection)
	{
		super(connection, "CONTACT");
		setTable(this);
	}

	public boolean getContact(int contactID)
	{
		this.id = contactID;

		return read();
	}

	public boolean add(String newFirstName, String newLastName, String newIdentification, String newAddress, String newCity, Integer newPostalCode, Integer newTelephone, Integer newMobile, String newEmail, String newContactPerson, String newComments)
	{
		this.first_name = newFirstName;
		this.last_name = newLastName;
		this.identification = newIdentification;
		this.address = newAddress;
		this.city = newCity;
		this.postal_code = newPostalCode;
		this.telephone = newTelephone;
		this.mobile = newMobile;
		this.email = newEmail;
		this.contact_person = newContactPerson;
		this.comments = newComments;

		boolean valid = create();

		if (valid)
		{
			this.id = getLastId();
		}

		return valid;
	}

	public boolean edit(Integer contactID, String newFirstName, String newLastName, String newIdentification, String newAddress, String newCity, Integer newPostalCode, Integer newTelephone, Integer newMobile, String newEmail, String newContactPerson, String newComments)
	{
		boolean valid = false;

		this.id = contactID;

		if (read())
		{
			this.first_name = newFirstName;
			this.last_name = newLastName;
			this.identification = newIdentification;
			this.address = newAddress;
			this.city = newCity;
			this.postal_code = newPostalCode;
			this.telephone = newTelephone;
			this.mobile = newMobile;
			this.email = newEmail;
			this.contact_person = newContactPerson;
			this.comments = newComments;

			valid = update();
		}

		return valid;
	}

	public boolean delete(Integer contactID)
	{
		boolean valid = false;

		this.id = contactID;

		if (read())
		{
			valid = delete();
		}

		return valid;
	}

	public String getName(int contact)
	{
		String result = "";

		this.id = contact;

		if (read())
		{
			result = (this.first_name + " " + this.last_name).trim();
		}

		return result;
	}
}