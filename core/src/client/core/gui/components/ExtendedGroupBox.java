package client.core.gui.components;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import client.core.gui.FontStore;

public class ExtendedGroupBox extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public ExtendedGroupBox(String text) {
		TitledBorder title = BorderFactory.createTitledBorder(text);
		title.setTitleFont(FontStore.getDefaultFont());
		setBorder(title);
		setLayout(new GridBagLayout());
	}
}