package client.app.support.tasks;

import share.app.dictionary.Category;
import share.app.dictionary.Category.Categories;
import share.app.support.Support;
import share.core.Date;
import client.app.support.gui.def.GUIEditSupport;
import client.app.support.operations.OperationsSupports;
import client.app.system.dictionary.DictionaryManager;
import client.core.Profile;

public class AddSupport extends BaseSupport {
	
	public void start() {
		setTitle(getLiteral(GUIEditSupport.Literals.TITLE_ADD_SUPPORT));
		this.gui.module.setItems(DictionaryManager.get(Categories.SYSTEM_MODULES));
		this.gui.status.setItems(DictionaryManager.get(Categories.SUPPORT_STATUS));
		this.gui.module.selectNone();
		this.gui.status.set(Category.SUPPORT_STATUS.PENDING);
		this.gui.name.focus();
	}
	
	private void addSupport() {
		if (validate()) {
			Support newSupport = new Support(0, Profile.getUserID(), this.gui.module.get(), this.gui.status.get(), Date.getTodayDate(), Date.getTodayDate(), this.gui.name.get(), this.gui.description.get());
			boolean response = OperationsSupports.call().addSupport(newSupport);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditSupport.Literals.SUPPORT_NOT_CREATED);
				this.gui.module.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditSupport.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.name.isEmpty()) || (!this.gui.module.isEmpty()) || (!this.gui.description.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addSupport();
				break;
			
			case CANCEL:
				closing();
				break;
			
			default:
				break;
		}
	}
}