package share.app.dictionary;

public class Category
{
	public enum Categories
	{
		FIX_ORDER_STATUS, SUPPORT_STATUS, MEASURING_UNIT, PAYMENT_METHOD, MONTHS, SYSTEM_MODULES, TAX
	}
	
	public static class FIX_ORDER_STATUS
	{
		public static final String RECEIVED = "R";
	}
	
	public static class SUPPORT_STATUS
	{
		public static final String PENDING = "P";
	}
	
	public static class TAX
	{
		public static final String GENERAL = "A";
		public static final String LOW = "B";
		public static final String SUPER_LOW = "C";
	}
}