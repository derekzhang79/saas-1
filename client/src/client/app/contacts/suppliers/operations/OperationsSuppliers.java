package client.app.contacts.suppliers.operations;

import share.app.contacts.suppliers.IOperations;
import share.app.contacts.suppliers.Supplier;
import client.core.Operation;

public class OperationsSuppliers implements IOperations {
	
	private static OperationsSuppliers instance = new OperationsSuppliers();
	
	public static OperationsSuppliers call() {
		return OperationsSuppliers.instance;
	}
	
	public Supplier[] getSuppliers() {
		Operation<Supplier[]> operation = new Operation<Supplier[]>(IOperations.GET_SUPPLIERS);
		
		return operation.run();
	}
	
	public Boolean addSupplier(Supplier supplier) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.ADD_SUPPLIER);
		
		return operation.run(supplier);
	}
	
	public Boolean editSupplier(Supplier original, Supplier newSupplier) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.EDIT_SUPPLIER);
		
		return operation.run(original, newSupplier);
	}
	
	public Boolean deleteSupplier(Supplier supplier) {
		Operation<Boolean> operation = new Operation<Boolean>(IOperations.DELETE_SUPPLIER);
		
		return operation.run(supplier);
	}
}