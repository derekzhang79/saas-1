package client.core.gui.reports;

import share.core.Constants;
import client.core.debug.Debug;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class FontReport {
	
	private BaseFont baseFont = null;
	private Font font = null;
	private int size = 0;
	
	public FontReport(String family, int size, boolean bold, boolean italic) {
		this.size = size;
		
		try {
			
			this.baseFont = BaseFont.createFont(getFontName(family, bold, italic), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			this.font = new Font(this.baseFont);
			this.font.setSize(size);
			
			if (bold) {
				this.font.setStyle(Font.BOLD);
			}
			
			if (italic) {
				this.font.setStyle(Font.ITALIC);
			}
			
		} catch (Exception e) {
			Debug.setError(e);
		}
	}
	
	private String getFontName(String family, boolean bold, boolean italic) {
		String result = Constants.FONT_PATH + family;
		
		if (italic) {
			result += "_italic";
		}
		
		if (bold) {
			result += "_bold";
		}
		
		return result + Constants.FONT_EXTENSION;
	}
	
	public BaseFont getBaseFont() {
		return this.baseFont;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public int getSize() {
		return this.size;
	}
}