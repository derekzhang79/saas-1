package share.app.taxes;

import share.core.Date;

public interface IOperations
{
	public static final String GET_TAXES = "server.app.taxes.Operations:getTaxes";
	public static final String GET_TAXES_FROM = "server.app.taxes.Operations:getTaxesFrom";
	public static final String ADD_TAX = "server.app.taxes.Operations:addTax";
	public static final String EDIT_TAX = "server.app.taxes.Operations:editTax";
	public static final String DELETE_TAX = "server.app.taxes.Operations:deleteTax";

	public Tax[] getTaxes();

	public Tax[] getTaxesFrom(Date from);

	public Boolean addTax(Tax tax);

	public Boolean editTax(Tax original, Tax newTax);

	public Boolean deleteTax(Tax tax);
}