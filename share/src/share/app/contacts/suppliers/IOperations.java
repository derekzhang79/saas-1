package share.app.contacts.suppliers;

public interface IOperations
{
	public static final String BASE_OPERATIONS = "server.app.contacts.suppliers.Operations:";
	public static final String GET_SUPPLIERS = IOperations.BASE_OPERATIONS + "getSuppliers";
	public static final String ADD_SUPPLIER = IOperations.BASE_OPERATIONS + "addSupplier";
	public static final String EDIT_SUPPLIER = IOperations.BASE_OPERATIONS + "editSupplier";
	public static final String DELETE_SUPPLIER = IOperations.BASE_OPERATIONS + "deleteSupplier";
	
	public Supplier[] getSuppliers();
	
	public Boolean addSupplier(Supplier supplier);
	
	public Boolean editSupplier(Supplier original, Supplier newSupplier);
	
	public Boolean deleteSupplier(Supplier supplier);
}