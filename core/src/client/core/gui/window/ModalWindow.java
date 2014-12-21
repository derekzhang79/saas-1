package client.core.gui.window;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import client.core.Desktop;

public class ModalWindow extends JDialog implements ComponentListener, WindowListener
{
	private static final long serialVersionUID = -9310160341977448L;

	private boolean closed = false;
	private final WindowManager manager;

	public ModalWindow(WindowManager manager)
	{
		super(Desktop.getDesktop(), ModalityType.APPLICATION_MODAL);

		this.manager = manager;

		addComponentListener(this);
		addWindowListener(this);
	}

	public void showWindow()
	{
		pack();
		this.manager.startTask();
		setVisible(true);
	}

	protected void close()
	{
		this.closed = true;
		setVisible(false);
		dispose();
	}

	@Override
	public void componentShown(ComponentEvent event)
	{
		setLocationRelativeTo(null);
	}

	@Override
	public void componentResized(ComponentEvent event)
	{
	}

	@Override
	public void componentMoved(ComponentEvent event)
	{
	}

	@Override
	public void componentHidden(ComponentEvent event)
	{
	}

	@Override
	public void windowActivated(WindowEvent event)
	{
	}

	@Override
	public void windowClosed(WindowEvent event)
	{
	}

	@Override
	public void windowClosing(WindowEvent event)
	{
		if (!this.closed)
		{
			this.manager.closing();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent event)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent event)
	{
	}

	@Override
	public void windowIconified(WindowEvent event)
	{
	}

	@Override
	public void windowOpened(WindowEvent event)
	{
	}
}