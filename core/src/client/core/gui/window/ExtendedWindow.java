package client.core.gui.window;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import client.core.debug.Debug;

public class ExtendedWindow extends JInternalFrame implements InternalFrameListener
{
	private static final long serialVersionUID = -4749953270722074967L;
	
	private boolean closed = false;
	private WindowManager manager = null;

	public ExtendedWindow(WindowManager manager)
	{
		super("", false, true, false, true);

		this.manager = manager;

		addInternalFrameListener(this);
	}

	public void showWindow()
	{
		if (this.manager.isVisible())
		{
			setLocation((getDesktopPane().getWidth() / 2) - (getWidth() / 2), (getDesktopPane().getHeight() / 2) - (getHeight() / 2));
			setVisible(true);
		}
	}

	public void start()
	{
		this.manager.startTask();
	}

	public void close()
	{
		try
		{
			this.closed = true;
			setClosed(true);
		}
		catch (Exception e)
		{
			Debug.setError(e);
		}
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent arg0)
	{
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent arg0)
	{
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent arg0)
	{
		if (!this.closed)
		{
			this.manager.closing();
		}
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0)
	{
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0)
	{
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0)
	{
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0)
	{
	}
}