package share.app.support;

import share.core.Date;

public interface IOperations
{
	public static final String GET_SUPPORTS = "server.app.support.Operations:getSupports";
	public static final String ADD_SUPPORT = "server.app.support.Operations:addSupport";
	public static final String EDIT_SUPPORT = "server.app.support.Operations:editSupport";

	public Support[] getSupports(Date dateCreationParam, String moduleParam);

	public Boolean addSupport(Support support);

	public Boolean editSupport(Support original, Support newSupport);
}