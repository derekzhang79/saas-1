package client.app.contacts.suppliers.tasks;

import share.app.contacts.suppliers.Supplier;
import client.app.contacts.suppliers.gui.def.GUIEditSupplier;
import client.app.contacts.suppliers.operations.OperationsSuppliers;

public class EditSupplier extends BaseSupplier
{
	private final Supplier original;
	
	public EditSupplier(Supplier original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditSupplier.Literals.TITLE_EDIT_SUPPLIER));
		
		this.gui.name.set(this.original.name);
		this.gui.identification.set(this.original.identification);
		this.gui.address.set(this.original.address);
		this.gui.city.set(this.original.city);
		this.gui.postalCode.set(this.original.postalCode);
		this.gui.telephone.set(this.original.telephone);
		this.gui.mobile.set(this.original.mobile);
		this.gui.email.set(this.original.email);
		this.gui.contactPerson.set(this.original.contactPerson);
		this.gui.comments.set(this.original.comments);
		
		this.gui.name.focus();
	}
	
	private void editSupplier()
	{
		if (validate())
		{
			Supplier newSupplier = new Supplier(0, this.gui.name.get(), this.gui.identification.get(), this.gui.address.get(), this.gui.city.get(), this.gui.postalCode.getInt(), this.gui.telephone.getInt(), this.gui.mobile.getInt(), this.gui.email.get(), this.gui.contactPerson.get(), this.gui.comments.get(), 0);
			boolean response = OperationsSuppliers.call().editSupplier(this.original, newSupplier);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditSupplier.Literals.SUPPLIER_NOT_EDITED);
				this.gui.name.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditSupplier.Literals.ASK_CLOSE_WINDOW))
			{
				close();
			}
			else
			{
				setFocus();
			}
		}
		else
		{
			close();
		}
	}
	
	private boolean formChanged()
	{
		return ((!this.gui.name.equals(this.original.name)) || (!this.gui.identification.equals(this.original.identification)) || (!this.gui.address.equals(this.original.address)) || (!this.gui.city.equals(this.original.city)) || (!this.gui.postalCode.equals(this.original.postalCode)) || (!this.gui.telephone.equals(this.original.telephone)) || (!this.gui.mobile.equals(this.original.mobile)) || (!this.gui.contactPerson.equals(this.original.contactPerson)) || (!this.gui.email.equals(this.original.email)) || (!this.gui.comments.equals(this.original.comments)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editSupplier();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}