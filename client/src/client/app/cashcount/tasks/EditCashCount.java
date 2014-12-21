package client.app.cashcount.tasks;

import share.app.cashcount.CashCount;
import client.app.cashcount.gui.def.GUIEditCashCount;
import client.app.cashcount.operations.OperationsCashCount;

public class EditCashCount extends BaseCashCount {
	
	private CashCount original = null;
	
	public EditCashCount(CashCount original) {
		this.original = original;
	}
	
	public void start() {
		setTitle(getLiteral(GUIEditCashCount.Literals.TITLE_EDIT_CASH_COUNT));
		
		this.gui.date.set(this.original.date);
		this.gui.type_500.set(this.original.type_500);
		this.gui.type_200.set(this.original.type_200);
		this.gui.type_100.set(this.original.type_100);
		this.gui.type_50.set(this.original.type_50);
		this.gui.type_20.set(this.original.type_20);
		this.gui.type_10.set(this.original.type_10);
		this.gui.type_5.set(this.original.type_5);
		this.gui.type_2.set(this.original.type_2);
		this.gui.type_1.set(this.original.type_1);
		this.gui.type_0_5.set(this.original.type_0_5);
		this.gui.type_0_2.set(this.original.type_0_2);
		this.gui.type_0_1.set(this.original.type_0_1);
		this.gui.type_0_05.set(this.original.type_0_05);
		this.gui.type_0_02.set(this.original.type_0_02);
		this.gui.type_0_01.set(this.original.type_0_01);
		
		refreshTotals();
		this.gui.type_50.focus();
	}
	
	private void editCashCount() {
		if (validate()) {
			CashCount newCashCount = new CashCount(0, this.gui.date.get(), this.gui.type_500.getInt(), this.gui.type_200.getInt(), this.gui.type_100.getInt(), this.gui.type_50.getInt(), this.gui.type_20.getInt(), this.gui.type_10.getInt(), this.gui.type_5.getInt(), this.gui.type_2.getInt(), this.gui.type_1.getInt(), this.gui.type_0_5.getInt(), this.gui.type_0_2.getInt(), this.gui.type_0_1.getInt(), this.gui.type_0_05.getInt(), this.gui.type_0_02.getInt(), this.gui.type_0_01.getInt());
			boolean response = OperationsCashCount.call().editCashCount(this.original, newCashCount);
			
			if (response) {
				close(true);
			} else {
				showWarning(GUIEditCashCount.Literals.CASH_COUNT_NOT_EDITED);
				this.gui.date.focus();
			}
		}
	}
	
	public void closing() {
		if (formChanged()) {
			if (showConfirm(GUIEditCashCount.Literals.ASK_CLOSE_WINDOW)) {
				close();
			} else {
				setFocus();
			}
		} else {
			close();
		}
	}
	
	private boolean formChanged() {
		return ((!this.gui.date.equals(this.original.date)) || (!this.gui.type_0_01.equals(this.original.type_0_01)) || (!this.gui.type_0_02.equals(this.original.type_0_02)) || (!this.gui.type_0_05.equals(this.original.type_0_05)) || (!this.gui.type_0_1.equals(this.original.type_0_1)) || (!this.gui.type_0_2.equals(this.original.type_0_2)) || (!this.gui.type_0_5.equals(this.original.type_0_5)) || (!this.gui.type_1.equals(this.original.type_1)) || (!this.gui.type_2.equals(this.original.type_2)) || (!this.gui.type_5.equals(this.original.type_5)) || (!this.gui.type_10.equals(this.original.type_10)) || (!this.gui.type_20.equals(this.original.type_20)) || (!this.gui.type_50.equals(this.original.type_50)) || (!this.gui.type_100.equals(this.original.type_100)) || (!this.gui.type_200.equals(this.original.type_200)) || (!this.gui.type_500.equals(this.original.type_500)));
	}
	
	public void event(Event event) {
		switch (event) {
		
			case SAVE:
				editCashCount();
				break;
			
			case CANCEL:
				close();
				break;
			
			case REFRESH:
				refreshTotals();
				break;
			
			default:
				break;
		}
	}
}