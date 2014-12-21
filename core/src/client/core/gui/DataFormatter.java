package client.core.gui;

import java.text.DecimalFormat;

public class DataFormatter {
	
	public static String formatDecimal(double value) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		return df.format(value);
	}
}
