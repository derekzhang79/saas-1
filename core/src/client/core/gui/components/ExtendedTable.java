package client.core.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import share.core.Constants;
import share.core.Date;
import client.core.Debug;
import client.core.gui.DataFormatter;
import client.core.gui.FontStore;
import client.core.gui.Message;
import client.core.gui.window.WindowManager;

public class ExtendedTable extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;

	private JTable table = null;
	private JTextField search = null;
	private TableModel model = null;
	private TableRowSorter<TableModel> sorter = null;
	private WindowManager manager = null;
	private JPopupMenu popMenu = null;
	private ColumnType[] columnTypes = null;
	private String action = "";

	private static final int DEFAULT_ROW_HEIGHT = 22;

	public ExtendedTable(WindowManager manager, int width, int height)
	{
		super(false);

		this.manager = manager;

		setPreferredSize(new Dimension(width, height));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createRaisedBevelBorder());
		setFont(FontStore.getDefaultFont(false));
	}

	public void setAction(String newAction)
	{
		this.action = newAction;
	}

	public boolean isRowSelected()
	{
		return this.table.getSelectedRow() != -1;
	}

	public void setRows(ArrayList<Object> rows)
	{
		this.model.setRows(rows);
		refresh();
	}

	public void setRows(Object[] rows)
	{
		ArrayList<Object> list = new ArrayList<Object>();

		for (Object object : rows)
		{
			list.add(object);
		}

		setRows(list);
		focus();
	}

	public ArrayList<Object> getRows()
	{
		return this.model.getRows();
	}

	public Object[] getRowsArray()
	{
		Object[] result = new Object[this.model.getRowCount()];
		this.model.getRows().toArray(result);

		return result;
	}

	public void refresh()
	{
		this.model.refresh();
	}

	public void createToolBar(JToolBar toolBar)
	{
		toolBar.setFloatable(false);
		toolBar.setBorderPainted(true);
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());

		this.search = new JTextField();
		this.search.setFont(FontStore.getDefaultFont());
		this.search.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void changedUpdate(DocumentEvent event)
			{
				newFilter();
			}

			@Override
			public void insertUpdate(DocumentEvent event)
			{
				newFilter();
			}

			@Override
			public void removeUpdate(DocumentEvent event)
			{
				newFilter();
			}
		});

		this.search.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				refresh();
			}
		});

		toolBar.add(this.search);

		add(toolBar);
	}

	private void newFilter()
	{
		RowFilter<TableModel, Object> rf = null;

		try
		{
			rf = RowFilter.regexFilter("(?i)" + this.search.getText().replace(",", "\\."));
		}
		catch (Exception e)
		{
			return;
		}

		this.sorter.setRowFilter(rf);
	}

	public int getCurrentRowNumber()
	{
		int viewRow = ExtendedTable.this.table.getSelectedRow();

		if (viewRow >= 0)
		{
			viewRow = ExtendedTable.this.table.convertRowIndexToModel(viewRow);
		}

		return viewRow;
	}

	public Object getCurrentRow()
	{
		return this.model.getCurrentRow(getCurrentRowNumber());
	}

	public ColumnType[] getColumnTypes()
	{
		return this.columnTypes;
	}

	public void createTable(ColumnType[] columnType, JPopupMenu popmenu)
	{
		this.columnTypes = columnType;

		this.model = new TableModel(columnType);
		this.sorter = new TableRowSorter<TableModel>(this.model);

		this.table = new JTable(this.model);
		this.table.setRowHeight(ExtendedTable.DEFAULT_ROW_HEIGHT);
		this.table.setRowSorter(this.sorter);
		this.table.setRowSelectionAllowed(true);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setFont(FontStore.getDefaultFont());
		this.table.getTableHeader().setFont(FontStore.getDefaultFont());
		this.table.setFillsViewportHeight(true);

		for (int i = 0; i < columnType.length; i++)
		{
			ColumnType type = columnType[i];

			this.table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer(columnType));

			if (type.getWidth() != 0)
			{
				this.table.getColumnModel().getColumn(i).setPreferredWidth(type.getWidth());
				this.table.getColumnModel().getColumn(i).setMinWidth(type.getWidth());
			}
		}

		this.popMenu = popmenu;

		this.table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				mouseHandler(e);
			}
		});

		this.table.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent arg0)
			{
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{
			}

			@Override
			public void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == KeyEvent.VK_ENTER)
				{
					actionPerformed();
					event.consume();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(this.table);
		add(scrollPane);
	}

	private int getRowByPoint(Point point)
	{
		return this.table.rowAtPoint(point);
	}

	private boolean isCorrectPoint(Point point)
	{
		return getRowByPoint(point) != -1;
	}

	public void mouseHandler(MouseEvent e)
	{
		if (e.getClickCount() == 2)
		{
			if (isCorrectPoint(new Point(e.getX(), e.getY())))
			{
				actionPerformed();
			}
		}

		if (SwingUtilities.isRightMouseButton(e))
		{
			selectRowRightClick(e);
		}
	}

	private void selectRowRightClick(MouseEvent e)
	{
		if (isCorrectPoint(e.getPoint()))
		{
			int rowNumber = getRowByPoint(e.getPoint());
			this.table.setRowSelectionInterval(rowNumber, rowNumber);
			this.table.repaint();

			if ((this.table.getSelectedRow() != -1) && (this.popMenu != null))
			{
				this.popMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	private void actionPerformed()
	{
		if (!this.action.isEmpty())
		{
			this.manager.actionPerformed(new ActionEvent(this, 0, String.valueOf(this.action)));
		}
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		this.search.requestFocus();

		if (this.manager != null)
		{
			this.manager.actionPerformed(event);
		}
	}

	public void focus()
	{
		if (isRowSelected())
		{
			this.table.requestFocus();
		}
		else
		{
			this.search.requestFocus();
		}
	}

	public void cleanSearch()
	{
		this.search.setText("");
		this.search.requestFocus();
	}

	public class CustomTableCellRenderer extends DefaultTableCellRenderer
	{

		private static final long serialVersionUID = 1L;

		private int currentColumn = 0;

		private ColumnType[] types = null;

		public CustomTableCellRenderer(ColumnType[] types)
		{
			this.types = types;
		}

		@Override
		public void setValue(Object value)
		{
			Object result = value;
			ColumnType.Type type = this.types[this.currentColumn].getRealType();

			if (value != null)
			{
				switch (type)
				{

					case BOOLEAN:
						result = Message.getBooleanString((Boolean)value);
						setHorizontalAlignment(SwingConstants.CENTER);
						break;

					case DATE:
						result = value.toString();
						setHorizontalAlignment(SwingConstants.CENTER);
						break;

					case DECIMAL:
						result = DataFormatter.formatDecimal((Double)value) + " ";
						setHorizontalAlignment(SwingConstants.RIGHT);
						break;

					case INTEGER:
						result = value + " ";
						setHorizontalAlignment(SwingConstants.RIGHT);
						break;

					case MONEY:
						result = DataFormatter.formatDecimal((Double)value) + " " + Constants.CURRENCY_EURO + " ";
						setHorizontalAlignment(SwingConstants.RIGHT);
						break;

					case STRING:
						result = " " + value;
						setHorizontalAlignment(SwingConstants.LEFT);
						break;

					default:
						break;

				}
			}

			super.setValue(result);
		}

		@Override
		public Component getTableCellRendererComponent(JTable tableObject, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			this.currentColumn = column;

			Component c = super.getTableCellRendererComponent(tableObject, value, isSelected, hasFocus, row, column);

			if (!isSelected)
			{
				if ((row % 2) != 0)
				{
					c.setBackground(new Color(240, 240, 240));
				}
				else
				{
					c.setBackground(new Color(255, 255, 255));
				}
			}

			return c;
		}
	}

	private class TableModel extends AbstractTableModel
	{

		private static final long serialVersionUID = 1L;

		private ArrayList<Object> data = new ArrayList<Object>();
		private ColumnType[] columnType = null;

		public TableModel(ColumnType[] columnType)
		{
			this.columnType = columnType;
		}

		public Object getCurrentRow(int row)
		{
			return this.data.get(row);
		}

		public void setRows(ArrayList<Object> newData)
		{
			this.data = newData;
		}

		public ArrayList<Object> getRows()
		{
			return this.data;
		}

		public void refresh()
		{
			fireTableDataChanged();
		}

		@Override
		public int getColumnCount()
		{
			return this.columnType.length;
		}

		@Override
		public int getRowCount()
		{
			return this.data.size();
		}

		@Override
		public String getColumnName(int col)
		{
			return this.columnType[col].getName();
		}

		private Object getField(Object object, String fieldName)
		{
			Object result = "";

			try
			{
				Class<?> clazz = object.getClass();
				Field field = clazz.getField(fieldName);
				result = field.get(object);
			}
			catch (Exception e)
			{
				Debug.setError(e);
			}

			if (result instanceof Date)
			{
				result = result.toString();
			}

			return result;
		}

		private void setField(Object object, Object value, String fieldName)
		{
			try
			{
				Class<?> clazz = object.getClass();
				Field field = clazz.getField(fieldName);
				field.set(object, value);
			}
			catch (Exception e)
			{
				Debug.setError(e);
			}
		}

		@Override
		public Object getValueAt(int row, int col)
		{
			String code = this.columnType[col].getCode();
			Object element = getField(this.data.get(row), code);

			if (element instanceof Date)
			{
				element = element.toString();
			}

			return element;
		}

		@Override
		public void setValueAt(Object value, int row, int col)
		{
			String code = this.columnType[col].getCode();
			setField(this.data.get(row), value, code);
			fireTableCellUpdated(row, col);
		}

		@Override
		public Class<?> getColumnClass(int c)
		{
			return this.columnType[c].getType();
		}

		@Override
		public boolean isCellEditable(int row, int col)
		{
			return false;
		}
	}
}