package share.app.contacts.suppliers;

public interface IOperations
{
	public static final String GET_SUPPLIERS = "server.app.contacts.suppliers.Operations:getSuppliers";
	public static final String ADD_SUPPLIER = "server.app.contacts.suppliers.Operations:addSupplier";
	public static final String EDIT_SUPPLIER = "server.app.contacts.suppliers.Operations:editSupplier";
	public static final String DELETE_SUPPLIER = "server.app.contacts.suppliers.Operations:deleteSupplier";

	public Supplier[] getSuppliers();

	public Boolean addSupplier(Supplier supplier);

	public Boolean editSupplier(Supplier original, Supplier newSupplier);

	public Boolean deleteSupplier(Supplier supplier);
}