package client.app.workshop.tasks;

import share.app.dictionary.Category;
import share.app.dictionary.Category.Categories;
import share.app.workshop.FixOrder;
import share.core.objects.Date;
import client.app.system.dictionary.DictionaryManager;
import client.app.workshop.gui.def.GUIEditFixOrder;
import client.app.workshop.operations.OperationsWorkshop;

public class AddFixOrder extends BaseFixOrder<FixOrder> {
	
	public void start() {
		setTitle(getLiteral(GUIEditFixOrder.Literals.TITLE_ADD_FIX_ORDER));
		this.gui.status.setItems(DictionaryManager.get(Categories.FIX_ORDER_STATUS));
		this.gui.status.set(Category.FIX_ORDER_STATUS.RECEIVED);
		this.gui.start.setToday();
		this.gui.clientName.focus();
	}
	
	private void addFixOrder() {
		if (validate()) {
			FixOrder newFixOrder = new FixOrder(0, this.clientID, "", this.gui.status.get(), "", this.gui.start.get(), this.gui.finish.get(), this.gui.comments.get(), 0);
			FixOrder response = OperationsWorkshop.call().addFixOrder(newFixOrder);
			
			if (response != null) {
				close(response);
			} else {
				showWarning(GUIEditFixOrder.Literals.FIX_ORDER_NOT_CREATED);
				this.gui.clientName.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditFixOrder.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.clientName.isEmpty()) || (!this.gui.start.equals(Date.getTodayDate())) || (!this.gui.finish.isEmpty()) || (!this.gui.comments.isEmpty()));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				addFixOrder();
				break;
			
			case CANCEL:
				closing();
				break;
			
			case SEARCH_CLIENT:
				searchClient();
				break;
			
			default:
				break;
		}
	}
}