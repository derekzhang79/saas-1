package share.app.contacts;

import share.core.Shareable;

public class Contact extends Shareable
{
	private static final long serialVersionUID = 252303121919316706L;
	
	public final int contactID;
	public final String identification;
	public final String address;
	public final String city;
	public final int postalCode;
	public final int telephone;
	public final int mobile;
	public final String email;
	public final String comments;
	public final String fullPhone;
	
	public Contact(int contactID, String identification, String address, String city, int postalCode, int telephone, int mobile, String email, String comments)
	{
		this.contactID = contactID;
		this.identification = identification;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.comments = comments;
		
		if ((telephone != 0) && (mobile != 0))
		{
			this.fullPhone = telephone + " - " + mobile;
		}
		else if ((telephone == 0) && (mobile != 0))
		{
			this.fullPhone = String.valueOf(mobile);
		}
		else if ((telephone != 0) && (mobile == 0))
		{
			this.fullPhone = String.valueOf(telephone);
		}
		else
		{
			this.fullPhone = "";
		}
	}
}