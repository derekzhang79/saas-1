package share.app.workshop;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.workshop.Operations:";

	public static final String GET_FIX_ORDERS = IOperations.BASE_OPERATIONS + "getFixOrders";
	public static final String ADD_FIX_ORDER = IOperations.BASE_OPERATIONS + "addFixOrder";
	public static final String EDIT_FIX_ORDER = IOperations.BASE_OPERATIONS + "editFixOrder";
	public static final String DELETE_FIX_ORDER = IOperations.BASE_OPERATIONS + "deleteFixOrder";
	
	public static final String GET_FIX_ORDERS_DETAIL = IOperations.BASE_OPERATIONS + "getFixOrdersDetail";
	public static final String ADD_FIX_ORDER_DETAIL = IOperations.BASE_OPERATIONS + "addFixOrderDetail";
	public static final String EDIT_FIX_ORDER_DETAIL = IOperations.BASE_OPERATIONS + "editFixOrderDetail";
	public static final String DELETE_FIX_ORDER_DETAIL = IOperations.BASE_OPERATIONS + "deleteFixOrderDetail";
	
	public FixOrder[] getFixOrders(Integer clientParam, String statusParam);
	
	public FixOrder addFixOrder(FixOrder fixOrder);
	
	public Boolean editFixOrder(FixOrder original, FixOrder newFixOrder);
	
	public Boolean deleteFixOrder(FixOrder fixOrder);
	
	public FixOrderDetail[] getFixOrdersDetail(Integer fixOrderID);
	
	public Boolean addFixOrderDetail(FixOrderDetail fixOrderDetail);
	
	public Boolean editFixOrderDetail(FixOrderDetail original, FixOrderDetail newFixOrderDetail);
	
	public Boolean deleteFixOrderDetail(FixOrderDetail fixOrderDetail);
}