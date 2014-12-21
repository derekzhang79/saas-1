package server.app.brands;

import java.sql.Connection;

import server.app.db.tables.TableBrand;
import server.core.ServerOperation;
import share.app.brands.Brand;
import share.app.brands.IOperations;

public class Operations extends ServerOperation implements IOperations {
	
	public Operations(Connection appConnection, Connection sysConnection) {
		super(appConnection, sysConnection);
	}
	
	public Brand[] getBrands() {
		TableBrand table = new TableBrand(getConnection());
		
		return table.getBrands();
	}
	
	public Brand addBrand(Brand brand) {
		TableBrand table = new TableBrand(getConnection());
		
		return table.add(brand);
	}
	
	public Boolean editBrand(Brand original, Brand newBrand) {
		TableBrand table = new TableBrand(getConnection());
		
		return table.edit(original, newBrand);
	}
	
	public Boolean deleteBrand(Brand brand) {
		TableBrand table = new TableBrand(getConnection());
		
		return table.delete(brand);
	}
}