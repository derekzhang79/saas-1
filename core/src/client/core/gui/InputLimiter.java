package client.core.gui;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class InputLimiter extends PlainDocument {
	
	private static final long serialVersionUID = 1L;
	
	private Pattern pattern = null;
	private int limit = 0;
	private boolean toUppercase = false;
	
	public InputLimiter(int limit, String regex) {
		this.limit = limit;
		
		if (!regex.isEmpty()) {
			this.pattern = Pattern.compile(regex);
		}
	}
	
	public InputLimiter(int limit, boolean upper) {
		this.limit = limit;
		this.toUppercase = upper;
	}
	
	public InputLimiter(int limit, boolean upper, String regex) {
		this.limit = limit;
		this.toUppercase = upper;
		
		if (!regex.isEmpty()) {
			this.pattern = Pattern.compile(regex);
		}
	}
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}
		
		if (this.pattern != null) {
			String newStr = new StringBuffer(getText(0, getLength())).insert(offset, str).toString();
			
			if (!this.pattern.matcher(newStr).matches()) {
				return;
			}
		}
		
		if ((getLength() + str.length()) <= this.limit) {
			if (this.toUppercase) {
				super.insertString(offset, str.toUpperCase(), attr);
			} else {
				super.insertString(offset, str, attr);
			}
		}
	}
}