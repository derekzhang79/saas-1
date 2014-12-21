package client.core.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import share.core.Constants;
import share.core.Resource;
import client.core.Debug;

public class FontStore {
	
	private static final int DEFAULT_SIZE = 12;
	
	private static Font CUSTOM_FONT = null;
	
	public static void configure() {
		try {
			FontStore.CUSTOM_FONT = Font.createFont(Font.TRUETYPE_FONT, Resource.get(Constants.FONT_PATH + "verdana.ttf"));
			FontStore.CUSTOM_FONT = FontStore.CUSTOM_FONT.deriveFont(11.0F);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(FontStore.CUSTOM_FONT);
		} catch (Exception e) {
			Debug.setError(e);
		}
	}
	
	public static Font getDefaultFont(boolean bold, int size) {
		if (bold) {
			return new Font(FontStore.CUSTOM_FONT.getName(), Font.BOLD, size);
		} else {
			return new Font(FontStore.CUSTOM_FONT.getName(), Font.PLAIN, size);
		}
	}
	
	public static Font getDefaultFont(boolean bold) {
		return FontStore.getDefaultFont(bold, FontStore.DEFAULT_SIZE);
	}
	
	public static Font getDefaultFont() {
		return FontStore.getDefaultFont(false);
	}
}