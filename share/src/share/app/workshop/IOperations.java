package share.app.workshop;

public interface IOperations
{
	public static final String GET_FIX_ORDERS = "server.app.workshop.Operations:getFixOrders";
	public static final String ADD_FIX_ORDER = "server.app.workshop.Operations:addFixOrder";
	public static final String EDIT_FIX_ORDER = "server.app.workshop.Operations:editFixOrder";
	public static final String DELETE_FIX_ORDER = "server.app.workshop.Operations:deleteFixOrder";

	public static final String GET_FIX_ORDERS_DETAIL = "server.app.workshop.Operations:getFixOrdersDetail";
	public static final String ADD_FIX_ORDER_DETAIL = "server.app.workshop.Operations:addFixOrderDetail";
	public static final String EDIT_FIX_ORDER_DETAIL = "server.app.workshop.Operations:editFixOrderDetail";
	public static final String DELETE_FIX_ORDER_DETAIL = "server.app.workshop.Operations:deleteFixOrderDetail";

	public FixOrder[] getFixOrders(Integer clientParam, String statusParam);

	public FixOrder addFixOrder(FixOrder fixOrder);

	public Boolean editFixOrder(FixOrder original, FixOrder newFixOrder);

	public Boolean deleteFixOrder(FixOrder fixOrder);

	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID);

	public Boolean addFixOrderDetail(FixOrderDetail fixOrderDetail);

	public Boolean editFixOrderDetail(FixOrderDetail original, FixOrderDetail newFixOrderDetail);

	public Boolean deleteFixOrderDetail(FixOrderDetail fixOrderDetail);
}