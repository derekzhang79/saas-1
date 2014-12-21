package client.core.gui.components;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartPanel;
import client.core.desktop.Desktop;

public class ChartViewer implements ComponentListener
{
	private JDialog dialog = null;

	public ChartViewer()
	{
	}

	public void run(final ChartPanel panel)
	{
		final ChartViewer viewer = this;

		panel.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent event)
			{
			}

			@Override
			public void mousePressed(MouseEvent event)
			{
				if (event.getClickCount() == 2)
				{
					ChartViewer.this.dialog = new JDialog(Desktop.getDesktop(), ModalityType.APPLICATION_MODAL);
					ChartViewer.this.dialog.setResizable(true);
					ChartViewer.this.dialog.setLocationRelativeTo(null);
					ChartViewer.this.dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					ChartViewer.this.dialog.add(new ChartPanel(panel.getChart()));

					Toolkit kit = Toolkit.getDefaultToolkit();
					Dimension dim = kit.getScreenSize();
					GraphicsConfiguration config = ChartViewer.this.dialog.getGraphicsConfiguration();
					Insets insets = kit.getScreenInsets(config);
					Dimension newDimension = new Dimension(dim.width - insets.left - insets.right, dim.height - insets.top - insets.bottom);
					ChartViewer.this.dialog.setPreferredSize(newDimension);

					ChartViewer.this.dialog.addComponentListener(viewer);

					ChartViewer.this.dialog.pack();
					ChartViewer.this.dialog.setVisible(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent event)
			{
			}

			@Override
			public void mouseEntered(MouseEvent event)
			{
			}

			@Override
			public void mouseClicked(MouseEvent event)
			{
			}
		});
	}

	@Override
	public void componentHidden(ComponentEvent event)
	{
	}

	@Override
	public void componentMoved(ComponentEvent event)
	{
	}

	@Override
	public void componentResized(ComponentEvent event)
	{
	}

	@Override
	public void componentShown(ComponentEvent event)
	{
		this.dialog.setLocationRelativeTo(null);
	}
}