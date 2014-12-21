package client.core.gui.window;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

class WindowLayer extends LayerUI<Container>
{
	private static final long serialVersionUID = 6570957449390315255L;
	
	private boolean on = false;
	private BufferedImage mOffscreenImage = null;
	private BufferedImageOp mOperation = null;
	
	public WindowLayer()
	{
		int matrixSize = 7;
		float[] matrix = new float[matrixSize * matrixSize];
		
		for (int i = 0; i < matrix.length; i++)
		{
			matrix[i] = 1.0f / (matrix.length);
		}
		
		this.mOperation = new ConvolveOp(new Kernel(matrixSize, matrixSize, matrix), ConvolveOp.EDGE_NO_OP, null);
	}
	
	@Override
	public void paint(Graphics g, JComponent c)
	{
		super.paint(g, c);
		
		int w = c.getWidth();
		int h = c.getHeight();
		
		if ((w == 0) || (h == 0))
		{
			return;
		}
		
		if (this.on)
		{
			if ((this.mOffscreenImage == null) || (this.mOffscreenImage.getWidth() != w) || (this.mOffscreenImage.getHeight() != h))
			{
				this.mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			}
			
			Graphics2D ig2 = this.mOffscreenImage.createGraphics();
			ig2.setClip(g.getClip());
			super.paint(ig2, c);
			ig2.dispose();
			
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(this.mOffscreenImage, this.mOperation, 0, 0);
		}
	}
	
	public void start()
	{
		this.on = true;
		firePropertyChange("tick", 0, 1);
	}
	
	public void stop()
	{
		this.on = false;
		firePropertyChange("tick", 0, 1);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void applyPropertyChange(PropertyChangeEvent pce, JLayer l)
	{
		if ("tick".equals(pce.getPropertyName()))
		{
			l.repaint();
		}
	}
}