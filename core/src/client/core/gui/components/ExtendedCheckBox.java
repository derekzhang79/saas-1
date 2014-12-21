package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import client.core.gui.ToolTipInterface;

public class ExtendedCheckBox extends JPanel implements ToolTipInterface {
	
	private static final long serialVersionUID = 1L;
	
	private JCheckBox checkBox = null;
	private String tooltip = "";
	
	public ExtendedCheckBox() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		this.checkBox = new JCheckBox();
		add(this.checkBox, c);
		
		setBorder(new LineBorder(Color.GRAY));
		setPreferredSize(new Dimension(22, 22));
		
		this.checkBox.setBorder(new LineBorder(Color.GRAY));
		this.checkBox.setOpaque(true);
		this.checkBox.setBackground(new Color(220, 220, 220));
	}
	
	public JCheckBox getCheckElement() {
		return this.checkBox;
	}
	
	public void set(boolean select) {
		this.checkBox.setSelected(select);
	}
	
	public boolean get() {
		return this.checkBox.isSelected();
	}
	
	public boolean equals(boolean value) {
		return (get() == value);
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public String getTooltip() {
		return this.tooltip;
	}
}