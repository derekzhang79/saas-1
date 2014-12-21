package client.app.contacts.suppliers.tasks;

import share.app.contacts.suppliers.Supplier;
import client.app.contacts.suppliers.gui.def.GUIDeleteSupplier;
import client.app.contacts.suppliers.operations.OperationsSuppliers;
import client.core.gui.taks.OptionTask;

public class DeleteSupplier extends OptionTask<Boolean>
{
	private final Supplier supplier;

	public DeleteSupplier(Supplier supplier)
	{
		super(GUIDeleteSupplier.PATH, TaskType.SINGLE);

		this.supplier = supplier;
	}

	@Override
	public void start()
	{
		if (showConfirmLiteral(getLiteral(GUIDeleteSupplier.Literals.ASK_DELETE, this.supplier.name)))
		{
			Boolean response = OperationsSuppliers.call().deleteSupplier(this.supplier);
			close(valid(response));
		}
		else
		{
			close(false);
		}
	}
}