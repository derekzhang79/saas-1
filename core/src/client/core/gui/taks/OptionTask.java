package client.core.gui.taks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import share.core.conf.Configurator;
import share.core.utils.MapTable;
import client.core.debug.Debug;
import client.core.desktop.Desktop;
import client.core.gui.components.ExtendedTable;
import client.core.gui.export.Excel;
import client.core.gui.export.PDF;
import client.core.gui.messages.Message;
import client.core.gui.window.WindowManager;
import client.core.profile.Profile;

public class OptionTask<T>
{
	private WindowManager manager = null;
	private T result = null;
	private final TaskType type;
	private final boolean permission;
	
	public enum Event
	{
		ADD, EDIT, DELETE, CLEAR, CLEAR_IN, CLEAR_OUT, SAVE, PRINT, ACCEPT, SEARCH, CLEAR_SEARCH_CLIENT, CLEAR_SEARCH_SECTION, CLEAR_SEARCH_PRODUCT, CLEAR_SEARCH_BRAND, CANCEL, EXIT, COPY, LOGIN, EXCEL, PDF, REFRESH, SELECT, DETAIL, SEARCH_CLIENT, SEARCH_SECTION, SEARCH_PRODUCT, SEARCH_BRAND, SEARCH_USER_GROUP
	}
	
	protected enum TaskType
	{
		SINGLE, MODAL, SPECIAL
	}
	
	public OptionTask(String guiName, TaskType type, boolean permission)
	{
		this.type = type;
		this.permission = permission || Profile.hasPermission(getClass().getCanonicalName());
		
		if (this.permission)
		{
			switch (type)
			{
				case MODAL:
				case SPECIAL:
					this.manager = new WindowManager(this, guiName, true);
					break;
				
				case SINGLE:
					this.manager = new WindowManager(this, guiName, false);
					break;
			}
		}
		else
		{
			Message.error(Configurator.getDesktop().messages.no_permission);
		}
		
		Desktop.getDesktop().restartTimer();
	}
	
	public OptionTask(String guiName, TaskType type)
	{
		this(guiName, type, false);
	}
	
	public void blurWindow(boolean value)
	{
		this.manager.blurWindow(value);
	}
	
	public boolean isSpecial()
	{
		return this.type.equals(TaskType.SPECIAL);
	}
	
	public boolean isSingle()
	{
		return this.type.equals(TaskType.SINGLE);
	}
	
	public void toFront()
	{
		this.manager.toFront();
	}
	
	public void setFocus()
	{
		this.manager.setFocus();
	}
	
	public void eventDispatcher(String code)
	{
		Desktop.getDesktop().restartTimer();
		
		if (!code.isEmpty())
		{
			try
			{
				event(Event.valueOf(code));
			}
			catch (Exception e)
			{
				Debug.setError(e);
			}
		}
	}
	
	public void event(Event event)
	{
	}
	
	public void start()
	{
	}
	
	public T run()
	{
		if (this.permission)
		{
			this.manager.show();
		}
		
		return this.result;
	}
	
	public void close()
	{
		this.manager.close(true);
	}
	
	public void closeSpecial()
	{
		this.manager.close(false);
	}
	
	public void close(T newResult)
	{
		this.result = newResult;
		close();
	}
	
	public void closing()
	{
		close();
	}
	
	public void setGUI(Object gui)
	{
		if (this.permission)
		{
			
			MapTable<String, Object> components = this.manager.getComponents();
			
			for (Field field : gui.getClass().getFields())
			{
				
				if (!Modifier.isStatic(field.getModifiers()))
				{
					try
					{
						field.set(gui, components.get(field.getName()));
					}
					catch (Exception e)
					{
						Debug.setError(e);
					}
				}
			}
		}
	}
	
	protected boolean valid(Boolean value)
	{
		return ((value != null) && (value));
	}
	
	// ============================= WINDOW INTERFACE =============================
	
	public void setStatusBar(String text)
	{
		this.manager.setStatusBar(text);
	}
	
	public void setTitle(String title)
	{
		this.manager.setTitle(title);
	}
	
	public void addTitle(String text)
	{
		this.manager.addTitle(text);
	}
	
	public void clearInputsBorders()
	{
		this.manager.clearInputsBorders();
	}
	
	public String getLiteral(Enum<?> name)
	{
		return this.manager.getLiteral(name.toString());
	}
	
	public String getLiteral(Enum<?> name, String value)
	{
		return getLiteral(name).replace("#", value);
	}
	
	// ============================== NOTIFICATIONS ===============================
	
	public boolean showConfirm(Enum<?> message)
	{
		return Message.confirm(getLiteral(message));
	}
	
	public boolean showConfirmLiteral(String message)
	{
		return Message.confirm(message);
	}
	
	public void showInformation(Enum<?> message)
	{
		Message.information(getLiteral(message));
	}
	
	public void showWarning(Enum<?> message)
	{
		Message.warning(getLiteral(message));
	}
	
	public void showError(Enum<?> message)
	{
		Message.error(getLiteral(message));
	}
	
	// ============================== EXPORT ===============================
	
	public void exportExcel(String title, ExtendedTable table)
	{
		Object[] rows = table.getRowsArray();
		Excel.export(title, table.getColumnTypes(), rows);
		table.focus();
	}
	
	public void exportPDF(String title, ExtendedTable table)
	{
		Object[] rows = table.getRowsArray();
		PDF.export(title, table.getColumnTypes(), rows);
		table.focus();
	}
}