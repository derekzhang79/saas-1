package share.app.support;

import share.core.Date;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.support.Operations:";
	public static final String GET_SUPPORTS = IOperations.BASE_OPERATIONS + "getSupports";
	public static final String ADD_SUPPORT = IOperations.BASE_OPERATIONS + "addSupport";
	public static final String EDIT_SUPPORT = IOperations.BASE_OPERATIONS + "editSupport";
	
	public Support[] getSupports(Date dateCreationParam, String moduleParam);
	
	public Boolean addSupport(Support support);
	
	public Boolean editSupport(Support original, Support newSupport);
}