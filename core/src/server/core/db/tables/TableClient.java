package server.core.db.tables;

import java.sql.Connection;

import server.core.db.Table;
import share.core.objects.Company;

public class TableClient extends Table {
	
	public Integer id = new Integer(0);
	public String owner_name = new String();
	public String company_name = new String();
	public String address = new String();
	public String city = new String();
	public Integer postal_code = new Integer(0);
	public Integer telephone = new Integer(0);
	public Integer fax = new Integer(0);
	public String identification = new String();
	public String email = new String();
	public String webpage = new String();
	public String ddbb = new String();
	
	public TableClient(Connection connection) {
		super(connection, "CLIENT");
		setTable(this);
	}
	
	public Company getClient(Integer clientID) {
		Company result = new Company();
		
		this.id = clientID;
		
		if (read()) {
			result = new Company(this.id, this.owner_name, this.company_name, this.address, this.city, this.postal_code, this.telephone, this.fax, this.identification, this.email, this.webpage, this.ddbb);
		}
		
		return result;
	}
}