package share.app.taxes;

import share.core.objects.Date;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.taxes.Operations:";
	public static final String GET_TAXES = IOperations.BASE_OPERATIONS + "getTaxes";
	public static final String GET_TAXES_FROM = IOperations.BASE_OPERATIONS + "getTaxesFrom";
	public static final String ADD_TAX = IOperations.BASE_OPERATIONS + "addTax";
	public static final String EDIT_TAX = IOperations.BASE_OPERATIONS + "editTax";
	public static final String DELETE_TAX = IOperations.BASE_OPERATIONS + "deleteTax";
	
	public Tax[] getTaxes();
	
	public Tax[] getTaxesFrom(Date from);
	
	public Boolean addTax(Tax tax);
	
	public Boolean editTax(Tax original, Tax newTax);
	
	public Boolean deleteTax(Tax tax);
}