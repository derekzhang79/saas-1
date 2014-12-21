package client.app.cashcount.tasks;

import java.awt.Color;

import share.core.Constants;
import client.app.cashcount.gui.def.GUIEditCashCount;
import client.core.gui.DataFormatter;
import client.core.gui.OptionTask;

public abstract class BaseCashCount extends OptionTask<Boolean> {
	
	protected GUIEditCashCount gui = new GUIEditCashCount();
	
	public BaseCashCount() {
		super(GUIEditCashCount.PATH, TaskType.MODAL);
		setGUI(this.gui);
	}
	
	protected boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.date.isEmpty()) {
			showWarning(GUIEditCashCount.Literals.DATE_REQUIRED);
			this.gui.date.setBorderColor(Color.RED);
			this.gui.date.focus();
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	protected void refreshTotals() {
		double type_500 = this.gui.type_500.getInt() * 500;
		this.gui.labelType_500_sub.set(DataFormatter.formatDecimal(type_500) + " " + Constants.CURRENCY_EURO);
		
		double type_200 = this.gui.type_200.getInt() * 200;
		this.gui.labelType_200_sub.set(DataFormatter.formatDecimal(type_200) + " " + Constants.CURRENCY_EURO);
		
		double type_100 = this.gui.type_100.getInt() * 100;
		this.gui.labelType_100_sub.set(DataFormatter.formatDecimal(type_100) + " " + Constants.CURRENCY_EURO);
		
		double type_50 = this.gui.type_50.getInt() * 50;
		this.gui.labelType_50_sub.set(DataFormatter.formatDecimal(type_50) + " " + Constants.CURRENCY_EURO);
		
		double type_20 = this.gui.type_20.getInt() * 20;
		this.gui.labelType_20_sub.set(DataFormatter.formatDecimal(type_20) + " " + Constants.CURRENCY_EURO);
		
		double type_10 = this.gui.type_10.getInt() * 10;
		this.gui.labelType_10_sub.set(DataFormatter.formatDecimal(type_10) + " " + Constants.CURRENCY_EURO);
		
		double type_5 = this.gui.type_5.getInt() * 5;
		this.gui.labelType_5_sub.set(DataFormatter.formatDecimal(type_5) + " " + Constants.CURRENCY_EURO);
		
		double type_2 = this.gui.type_2.getInt() * 2;
		this.gui.labelType_2_sub.set(DataFormatter.formatDecimal(type_2) + " " + Constants.CURRENCY_EURO);
		
		double type_1 = this.gui.type_1.getInt() * 1;
		this.gui.labelType_1_sub.set(DataFormatter.formatDecimal(type_1) + " " + Constants.CURRENCY_EURO);
		
		double type_0_5 = this.gui.type_0_5.getInt() * 0.5;
		this.gui.labelType_0_5_sub.set(DataFormatter.formatDecimal(type_0_5) + " " + Constants.CURRENCY_EURO);
		
		double type_0_2 = this.gui.type_0_2.getInt() * 0.2;
		this.gui.labelType_0_2_sub.set(DataFormatter.formatDecimal(type_0_2) + " " + Constants.CURRENCY_EURO);
		
		double type_0_1 = this.gui.type_0_1.getInt() * 0.1;
		this.gui.labelType_0_1_sub.set(DataFormatter.formatDecimal(type_0_1) + " " + Constants.CURRENCY_EURO);
		
		double type_0_05 = this.gui.type_0_05.getInt() * 0.05;
		this.gui.labelType_0_05_sub.set(DataFormatter.formatDecimal(type_0_05) + " " + Constants.CURRENCY_EURO);
		
		double type_0_02 = this.gui.type_0_02.getInt() * 0.02;
		this.gui.labelType_0_02_sub.set(DataFormatter.formatDecimal(type_0_02) + " " + Constants.CURRENCY_EURO);
		
		double type_0_01 = this.gui.type_0_01.getInt() * 0.01;
		this.gui.labelType_0_01_sub.set(DataFormatter.formatDecimal(type_0_01) + " " + Constants.CURRENCY_EURO);
		
		double total = type_500 + type_200 + type_100 + type_50 + type_20 + type_10 + type_5 + type_2 + type_1 + type_0_5 + type_0_2 + type_0_1 + type_0_05 + type_0_02 + type_0_01;
		this.gui.labelTotal.set(DataFormatter.formatDecimal(total) + " " + Constants.CURRENCY_EURO);
	}
}