package share.app.cashcount;

public interface IOperations
{
	public static final String GET_CASH_COUNTS = "server.app.cashcount.Operations:getCashCounts";
	public static final String ADD_CASH_COUNT = "server.app.cashcount.Operations:addCashCount";
	public static final String EDIT_CASH_COUNT = "server.app.cashcount.Operations:editCashCount";
	public static final String DELETE_CASH_COUNT = "server.app.cashcount.Operations:deleteCashCount";
	
	public CashCount[] getCashCounts(Integer year, String month);
	
	public Boolean addCashCount(CashCount cashCount);
	
	public Boolean editCashCount(CashCount original, CashCount newCashCount);
	
	public Boolean deleteCashCount(CashCount cashCount);
}