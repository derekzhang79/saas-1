package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import client.core.images.ImageStore;

public class ExtendedImage extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Image image = null;
	private Color color = Color.WHITE;
	private int width = 0;
	private int height = 0;
	
	public ExtendedImage(String source, int width, int height, boolean border) {
		super(false);
		
		this.width = width;
		this.height = height;
		this.image = ImageStore.getImage(source);
		
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		
		if (border) {
			setBorder(BorderFactory.createLoweredBevelBorder());
		}
	}
	
	public void setColor(Color background) {
		this.color = background;
	}
	
	public void setImage(String source) {
		this.image = ImageStore.getImage(source);
	}
	
	public void clear() {
		this.image = null;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(this.color);
		g.fillRect(0, 0, this.width, this.height);
		
		if (this.image != null) {
			
			int realWidth = this.image.getWidth(null);
			int realHeight = this.image.getHeight(null);
			
			if (realWidth > this.width) {
				realWidth = this.width;
			}
			
			if (realHeight > this.height) {
				realHeight = this.height;
			}
			
			g.drawImage(this.image, 0, 0, realWidth, realHeight, null);
		}
	}
}