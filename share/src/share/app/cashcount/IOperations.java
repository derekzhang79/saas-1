package share.app.cashcount;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.cashcount.Operations:";
	public static final String GET_CASH_COUNTS = IOperations.BASE_OPERATIONS + "getCashCounts";
	public static final String ADD_CASH_COUNT = IOperations.BASE_OPERATIONS + "addCashCount";
	public static final String EDIT_CASH_COUNT = IOperations.BASE_OPERATIONS + "editCashCount";
	public static final String DELETE_CASH_COUNT = IOperations.BASE_OPERATIONS + "deleteCashCount";

	public CashCount[] getCashCounts(Integer year, String month);

	public Boolean addCashCount(CashCount cashCount);

	public Boolean editCashCount(CashCount original, CashCount newCashCount);

	public Boolean deleteCashCount(CashCount cashCount);
}