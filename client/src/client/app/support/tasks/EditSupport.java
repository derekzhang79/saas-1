package client.app.support.tasks;

import share.app.dictionary.Category;
import share.app.dictionary.Category.Categories;
import share.app.support.Support;
import share.core.objects.Date;
import client.app.support.gui.def.GUIEditSupport;
import client.app.support.operations.OperationsSupports;
import client.app.system.dictionary.DictionaryManager;
import client.core.profile.Profile;

public class EditSupport extends BaseSupport
{
	private final Support original;
	
	public EditSupport(Support original)
	{
		this.original = original;
	}
	
	@Override
	public void start()
	{
		setTitle(getLiteral(GUIEditSupport.Literals.TITLE_EDIT_SUPPORT));
		this.gui.module.setItems(DictionaryManager.get(Categories.SYSTEM_MODULES));
		this.gui.status.setItems(DictionaryManager.get(Categories.SUPPORT_STATUS));
		
		this.gui.name.set(this.original.name);
		this.gui.module.set(this.original.module);
		this.gui.status.set(this.original.status);
		this.gui.description.set(this.original.description);
		
		if (!this.original.status.equals(Category.SUPPORT_STATUS.PENDING))
		{
			this.gui.module.setEnabled(false);
			this.gui.description.setEnabled(false);
		}
		
		this.gui.name.focus();
	}
	
	private void editSupport()
	{
		if (validate())
		{
			Support newSupport = new Support(0, Profile.getUserID(), this.gui.module.get(), this.gui.status.get(), Date.getTodayDate(), Date.getTodayDate(), this.gui.name.get(), this.gui.description.get());
			boolean response = OperationsSupports.call().editSupport(this.original, newSupport);
			
			if (response)
			{
				close(true);
			}
			else
			{
				showWarning(GUIEditSupport.Literals.SUPPORT_NOT_EDITED);
				this.gui.module.focus();
			}
		}
	}
	
	@Override
	public void closing()
	{
		if (formChanged())
		{
			if (showConfirm(GUIEditSupport.Literals.ASK_CLOSE_WINDOW))
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
		return ((!this.gui.name.equals(this.original.name)) || (!this.gui.module.equals(this.original.module)) || (!this.gui.description.equals(this.original.description)));
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case SAVE:
				editSupport();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}