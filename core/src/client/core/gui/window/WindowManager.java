package client.core.gui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import org.jdom2.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeriesCollection;
import share.core.Constants;
import share.core.MapTable;
import share.core.xml.XMLUtils;
import client.core.debug.Debug;
import client.core.desktop.Desktop;
import client.core.gui.components.ColumnType;
import client.core.gui.components.ExtendedButton;
import client.core.gui.components.ExtendedChartBar;
import client.core.gui.components.ExtendedChartLine;
import client.core.gui.components.ExtendedChartPie;
import client.core.gui.components.ExtendedCheckBox;
import client.core.gui.components.ExtendedComboBox;
import client.core.gui.components.ExtendedComboBoxItem;
import client.core.gui.components.ExtendedDateChooser;
import client.core.gui.components.ExtendedGroupBox;
import client.core.gui.components.ExtendedImage;
import client.core.gui.components.ExtendedInputDecimal;
import client.core.gui.components.ExtendedInputInt;
import client.core.gui.components.ExtendedInputPassword;
import client.core.gui.components.ExtendedInputText;
import client.core.gui.components.ExtendedLabel;
import client.core.gui.components.ExtendedMenu;
import client.core.gui.components.ExtendedProgressBar;
import client.core.gui.components.ExtendedRadioButton;
import client.core.gui.components.ExtendedTab;
import client.core.gui.components.ExtendedTable;
import client.core.gui.components.ExtendedTextArea;
import client.core.gui.components.InputLimiter;
import client.core.gui.components.TextCursorInterface;
import client.core.gui.components.ToolTipInterface;
import client.core.gui.fonts.FontStore;
import client.core.gui.taks.OptionTask;
import client.core.images.ImageStore;

@SuppressWarnings("rawtypes")
public class WindowManager implements ActionListener, MouseListener
{
	private Container container = null;
	private OptionTask task = null;

	private ExtendedWindow internal = null;
	private ModalWindow dialog = null;
	private WindowLayer layerUI = null;

	private JMenuBar menuBar = null;
	private JLabel status = null;

	private boolean isVisible = true;

	private final MapTable<String, Object> components = new MapTable<String, Object>();
	private final MapTable<String, String> literals = new MapTable<String, String>();
	private final MapTable<String, Font> fonts = new MapTable<String, Font>();
	private final MapTable<String, Color> colors = new MapTable<String, Color>();
	private final MapTable<String, ButtonGroup> radioGroups = new MapTable<String, ButtonGroup>();

	private static final String DIMENSION_2D = "2D";
	private static final String DIMENSION_3D = "3D";

	private static final String ACTION_ESCAPE = "escape";

	public enum Section
	{
		fonts, colors, menu_bar, components, literals, elements
	};

	private enum InputType
	{
		text, integer, decimal, password
	}

	private enum WindowComponent
	{
		menu, item, separator, label, input, button, text_area, check_box, radio_button, group_box, tabbed_pane, progress_bar, image, date_chooser, combo_box, table, chart_pie, chart_line, chart_bar, tree, toolbar, columns, popup_menu, layout
	};

	public enum Attribute
	{
		name, x, y, width, height, title, icon, font, editable, family, size, bold, italic, color, bg_color, code, value, text, action, keypress, type, tooltip, length, uppercase, positive, decimals, selected, group, min, max, start, label, source, today, initial, background, status_bar, dimension, x_axis, y_axis, margin_left, margin_right, margin_top, margin_bottom, alignment, param, left_padding, top_padding, right_padding, bottom_padding, extra, prefix, path, scale_width, scale_height, thick, leading, row_span, col_span, fill, border, full_screen, visible, regex
	};

	public enum Value
	{
		left, right, center
	};

	public WindowManager(OptionTask task, String guiName, boolean modal)
	{
		this.task = task;

		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action action = new AbstractAction()
		{
			private static final long serialVersionUID = 8260983508590321897L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				closing();
			}
		};

		if (!guiName.isEmpty())
		{
			if (modal)
			{
				this.dialog = new ModalWindow(this);
				this.container = this.dialog;
				this.layerUI = new WindowLayer();

				JPanel panel = new JPanel();
				JLayer<Container> jlayer = new JLayer<Container>(panel, this.layerUI);

				this.container.add(jlayer);

				build(guiName, panel);
				initDialog(this.dialog);

				this.dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, WindowManager.ACTION_ESCAPE);
				this.dialog.getRootPane().getActionMap().put(WindowManager.ACTION_ESCAPE, action);
			}
			else
			{
				this.internal = new ExtendedWindow(this);
				this.container = this.internal;
				this.layerUI = new WindowLayer();

				JPanel panel = new JPanel();
				JLayer<Container> jlayer = new JLayer<Container>(panel, this.layerUI);

				this.container.add(jlayer);

				build(guiName, panel);
				initWindow(this.internal);

				this.internal.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(escape, WindowManager.ACTION_ESCAPE);
				this.internal.getActionMap().put(WindowManager.ACTION_ESCAPE, action);
			}
		}
	}

	public void toFront()
	{
		if (this.internal != null)
		{
			this.internal.toFront();

			try
			{
				this.internal.setSelected(true);
				this.internal.getDesktopPane().getDesktopManager().deiconifyFrame(this.internal);
				this.internal.getDesktopPane().getDesktopManager().maximizeFrame(this.internal);
				this.internal.getDesktopPane().getDesktopManager().minimizeFrame(this.internal);
				this.internal.moveToFront();
			}
			catch (Exception e)
			{
				Debug.setError(e);
			}
		}
	}

	public void initWindow(ExtendedWindow window)
	{
		window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	public void initDialog(ModalWindow dialogWindow)
	{
		dialogWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialogWindow.setLocationRelativeTo(null);
		dialogWindow.addComponentListener(dialogWindow);
		dialogWindow.setResizable(false);
	}

	public void startTask()
	{
		this.task.start();
	}

	public MapTable<String, Object> getComponents()
	{
		return this.components;
	}

	public boolean isVisible()
	{
		return this.isVisible;
	}

	private void build(String guiName, Container containerWindow)
	{
		containerWindow.setLayout(new GridBagLayout());

		NodeElement root = loadGUI(guiName);

		if (root.exist(Attribute.title))
		{
			setTitle(root.getStringNoTrim(Attribute.title));
		}

		if (root.exist(Attribute.icon))
		{
			setIcon(root.getString(Attribute.icon));
		}

		if (root.exist(Attribute.visible))
		{
			this.isVisible = root.getBoolean(Attribute.visible);
		}

		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			Section type = Section.valueOf(node.getName());

			switch (type)
			{

				case colors:
					loadColors(node);
					break;

				case fonts:
					loadFonts(node);
					break;

				case menu_bar:
					loadMenuBar(node);
					break;

				case components:
					loadComponents(node, containerWindow);
					break;

				case literals:
					loadLiterals(node);
					break;

				default:
					break;

			}
		}

		if (root.exist(Attribute.status_bar) && root.getBoolean(Attribute.status_bar))
		{
			addStatusBar(containerWindow);
		}

		if (root.exist(Attribute.full_screen) && root.getBoolean(Attribute.full_screen))
		{
			BasicInternalFrameUI ui = (BasicInternalFrameUI)this.internal.getUI();
			ui.setNorthPane(null);
			this.internal.setBorder(null);
			this.internal.setSize(new Dimension(Desktop.getDesktop().getDesktopWidth(), Desktop.getDesktop().getDesktopHeight()));
		}
		else
		{
			if (this.internal != null)
			{
				this.internal.setSize(this.internal.getPreferredSize());
			}
		}
	}

	private void loadFonts(NodeElement root)
	{
		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			Attribute type = Attribute.valueOf(node.getName());

			if (type.equals(Attribute.font))
			{
				addFont(node);
			}
		}
	}

	private void addFont(NodeElement node)
	{
		int style = Font.PLAIN;

		if (node.exist(Attribute.bold) && node.getBoolean(Attribute.bold))
		{
			style = style | Font.BOLD;
		}

		if (node.exist(Attribute.italic) && node.getBoolean(Attribute.italic))
		{
			style = style | Font.ITALIC;
		}

		Font font = new Font(node.getString(Attribute.family), style, node.getInt(Attribute.size));

		this.fonts.put(node.getString(Attribute.name), font);
	}

	private void loadColors(NodeElement root)
	{
		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			Attribute type = Attribute.valueOf(node.getName());

			if (type.equals(Attribute.color))
			{
				addColor(node);
			}
		}
	}

	private void addColor(NodeElement node)
	{
		this.colors.put(node.getString(Attribute.name), getColor(node.getString(Attribute.value)));
	}

	private void loadMenuBar(NodeElement root)
	{
		this.menuBar = new JMenuBar();

		if (this.internal != null)
		{
			this.internal.setJMenuBar(this.menuBar);
		}
		else if (this.dialog != null)
		{
			this.dialog.setJMenuBar(this.menuBar);
		}

		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			WindowComponent type = WindowComponent.valueOf(node.getName());

			if (type.equals(WindowComponent.menu))
			{
				addMenu(node, this.menuBar);
			}
		}
	}

	private void addMenu(NodeElement root, JComponent mainMenu)
	{
		ExtendedMenu menu = new ExtendedMenu(root.getString(Attribute.text), this);

		if (root.exist(Attribute.font))
		{
			menu.setFont(this.fonts.get(root.getString(Attribute.font)));
		}

		mainMenu.add(menu);
		this.components.put(root.getString(Attribute.name), menu);

		processMenu(menu, root);
	}

	private void addSubMenu(ExtendedMenu parent, NodeElement root)
	{
		ExtendedMenu menu = new ExtendedMenu(root.getString(Attribute.text), this);

		if (root.exist(Attribute.icon))
		{
			menu.setIcon(ImageStore.getIcon(root.getString(Attribute.icon)));
		}

		if (root.exist(Attribute.font))
		{
			menu.setFont(this.fonts.get(root.getString(Attribute.font)));
		}

		parent.add(menu);
		this.components.put(root.getString(Attribute.name), menu);

		processMenu(menu, root);
	}

	private void processMenu(ExtendedMenu menu, NodeElement root)
	{
		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			WindowComponent type = WindowComponent.valueOf(node.getName());

			if (type.equals(WindowComponent.item))
			{
				addMenuItem(menu, node);
			}
			else if (type.equals(WindowComponent.separator))
			{
				menu.addSeparator();
			}
			else if (type.equals(WindowComponent.menu))
			{
				addSubMenu(menu, node);
			}
		}
	}

	private void addMenuItem(ExtendedMenu menu, NodeElement node)
	{
		JMenuItem item = menu.addMenu(node.getString(Attribute.name), " " + node.getString(Attribute.text), node.getString(Attribute.icon), node.getString(Attribute.action));
		item.setPreferredSize(new Dimension((int)item.getPreferredSize().getWidth() + 10, 28));

		if (node.exist(Attribute.font))
		{
			item.setFont(getFont(node.getString(Attribute.font)));
		}
		else
		{
			item.setFont(FontStore.getDefaultFont());
		}
	}

	private void loadComponents(NodeElement root, Container containerWindow)
	{
		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			processComponent(node, containerWindow);
		}
	}

	private void processComponent(NodeElement node, Container parent)
	{
		WindowComponent type = WindowComponent.valueOf(node.getName());

		if (type.equals(WindowComponent.label))
		{
			addLabel(node, parent);
		}
		else if (type.equals(WindowComponent.input))
		{
			addInput(node, parent);
		}
		else if (type.equals(WindowComponent.button))
		{
			addButton(node, parent);
		}
		else if (type.equals(WindowComponent.text_area))
		{
			addTextArea(node, parent);
		}
		else if (type.equals(WindowComponent.check_box))
		{
			addCheckBox(node, parent);
		}
		else if (type.equals(WindowComponent.radio_button))
		{
			addRadioButton(node, parent);
		}
		else if (type.equals(WindowComponent.group_box))
		{
			addGroupBox(node, parent);
		}
		else if (type.equals(WindowComponent.tabbed_pane))
		{
			addTabbedPane(node, parent);
		}
		else if (type.equals(WindowComponent.progress_bar))
		{
			addProgressBar(node, parent);
		}
		else if (type.equals(WindowComponent.image))
		{
			addImage(node, parent);
		}
		else if (type.equals(WindowComponent.date_chooser))
		{
			addDateChooser(node, parent);
		}
		else if (type.equals(WindowComponent.combo_box))
		{
			addComboBox(node, parent);
		}
		else if (type.equals(WindowComponent.table))
		{
			addTable(node, parent);
		}
		else if (type.equals(WindowComponent.chart_pie))
		{
			addChartPie(node, parent);
		}
		else if (type.equals(WindowComponent.chart_line))
		{
			addChartLine(node, parent);
		}
		else if (type.equals(WindowComponent.chart_bar))
		{
			addChartBar(node, parent);
		}
		else if (type.equals(WindowComponent.layout))
		{
			addLayout(node, parent);
		}
	}

	private void loadLiterals(NodeElement root)
	{
		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			this.literals.put(node.getString(Attribute.name), node.getString(Attribute.text));
		}
	}

	public NodeElement loadGUI(String path)
	{
		Document document = XMLUtils.readFromResource(path + Constants.GUI_EXTENSION);

		return new NodeElement(document.getRootElement());
	}

	public Font getFont(String name)
	{
		return this.fonts.get(name);
	}

	private Color getColor(String text)
	{
		int red = Integer.parseInt(text.substring(0, 2), 16);
		int green = Integer.parseInt(text.substring(2, 4), 16);
		int blue = Integer.parseInt(text.substring(4, 6), 16);

		return new Color(red, green, blue);
	}

	private void setIcon(String icon)
	{
		if (this.internal != null)
		{
			this.internal.setFrameIcon(ImageStore.getIcon(icon));
		}
		else if (this.dialog != null)
		{
			this.dialog.setIconImage(ImageStore.getImage(icon));
		}
	}

	public void setTitle(String title)
	{
		if (this.internal != null)
		{
			this.internal.setTitle(title);
		}
		else if (this.dialog != null)
		{
			this.dialog.setTitle(title);
		}
	}

	public void addTitle(String text)
	{
		if (this.internal != null)
		{
			this.internal.setTitle(this.internal.getTitle() + text);
		}
		else if (this.dialog != null)
		{
			this.dialog.setTitle(this.dialog.getTitle() + text);
		}
	}

	public void blurWindow(boolean value)
	{
		if (value)
		{
			this.layerUI.start();
		}
		else
		{
			this.layerUI.stop();
		}
	}

	public void setFocus()
	{
		if (this.internal != null)
		{
			this.internal.requestFocus();
		}
		else if (this.dialog != null)
		{
			this.dialog.requestFocus();
		}
	}

	public void show()
	{
		if (this.task.isSpecial())
		{
			Desktop.getDesktop().appBlocked(true);
			Desktop.getDesktop().blurAllWindows(true);
		}
		else
		{
			Desktop.getDesktop().addTask(this.task);
		}

		if (this.internal != null)
		{
			Desktop.getDesktop().addWindowToDesktop(this.internal);
		}
		else if (this.dialog != null)
		{
			this.dialog.showWindow();
		}
	}

	public void close(boolean propage)
	{
		if (this.internal != null)
		{
			this.internal.close();
		}
		else if (this.dialog != null)
		{
			this.dialog.close();
		}

		if (propage)
		{
			Desktop.getDesktop().closeTask(this.task);
		}

		if (this.task.isSpecial())
		{
			Desktop.getDesktop().appBlocked(false);
			Desktop.getDesktop().blurAllWindows(false);
		}
		else
		{
			Desktop.getDesktop().removeTask(this.task);
		}
	}

	public void closing()
	{
		this.task.closing();
	}

	public String getLiteral(String name)
	{
		return this.literals.get(name);
	}

	private void addStatusBar(Container containerWindow)
	{
		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.insets = new Insets(0, 0, 0, 0);
		layout.ipady = 10;
		layout.gridx = 0;
		layout.gridwidth = 9999;
		layout.gridy = 9999;

		JPanel statusBar = new JPanel();
		statusBar.setLayout(null);
		this.status = new JLabel();
		this.status.setFont(FontStore.getDefaultFont());
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.setPreferredSize(new Dimension(0, 12));
		this.status.setBounds(5, 1, 2000, 20);
		statusBar.add(this.status);
		containerWindow.add(statusBar, layout);
	}

	public void setStatusBar(String text)
	{
		if (this.status != null)
		{
			this.status.setText(text);
		}
	}

	private void addLabel(NodeElement node, Container parent)
	{
		ExtendedLabel label = new ExtendedLabel(node.getString(Attribute.text));

		if (node.exist(Attribute.font))
		{
			label.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.color))
		{
			label.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		if (node.exist(Attribute.bold))
		{
			label.setBold(node.getBoolean(Attribute.bold));
		}

		if (node.exist(Attribute.width) && node.exist(Attribute.height))
		{
			label.setLabelSize(node.getInt(Attribute.width), node.getInt(Attribute.height));
		}

		if (node.exist(Attribute.alignment))
		{
			label.setHorizontalAlignment(getLabelAlignment(node.getString(Attribute.alignment)));
		}

		if (node.exist(Attribute.border) && node.getBoolean(Attribute.border))
		{
			int topPadding = (node.exist(Attribute.top_padding)) ? node.getIntValue(Attribute.top_padding) : 2;
			int leftPadding = (node.exist(Attribute.left_padding)) ? node.getIntValue(Attribute.left_padding) : 4;
			int bottomPadding = (node.exist(Attribute.bottom_padding)) ? node.getIntValue(Attribute.bottom_padding) : 2;
			int rightPadding = (node.exist(Attribute.right_padding)) ? node.getIntValue(Attribute.right_padding) : 10;

			if (node.exist(Attribute.bg_color))
			{
				label.setBorder(topPadding, leftPadding, bottomPadding, rightPadding, this.colors.get(node.getString(Attribute.bg_color)));
			}
			else
			{
				label.setBorder(topPadding, leftPadding, bottomPadding, rightPadding);
			}
		}

		GridBagConstraints layout = new GridBagConstraints();

		if (node.exist(Attribute.fill))
		{
			if (node.getBoolean(Attribute.fill))
			{
				layout.fill = GridBagConstraints.HORIZONTAL;
			}
		}
		else
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		layout.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(label, layout);

		this.components.put(node.getString(Attribute.name), label);
	}

	private int getLabelAlignment(String type)
	{
		int result = SwingConstants.LEFT;

		if (type.equals(Value.center.toString()))
		{
			result = SwingConstants.CENTER;
		}
		else if (type.equals(Value.left.toString()))
		{
			result = SwingConstants.LEFT;
		}
		else if (type.equals(Value.right.toString()))
		{
			result = SwingConstants.RIGHT;
		}

		return result;
	}

	private void addInput(NodeElement node, Container parent)
	{
		InputType type = InputType.valueOf(node.getString(Attribute.type));

		switch (type)
		{

			case decimal:
				addInputDecimal(node, parent);
				break;

			case integer:
				addInputInt(node, parent);
				break;

			case password:
				addInputPassoword(node, parent);
				break;

			case text:
				addInputText(node, parent);
				break;

			default:
				break;

		}
	}

	private void addInputText(final NodeElement node, Container parent)
	{
		final ExtendedInputText input = new ExtendedInputText(node.getInt(Attribute.width));

		if (node.exist(Attribute.tooltip))
		{
			input.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			input.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.editable))
		{
			input.setEditable(node.getBoolean(Attribute.editable));
			input.setBackground(Color.WHITE);
		}

		if (node.exist(Attribute.uppercase))
		{
			input.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getBoolean(Attribute.uppercase), node.getStringValue(Attribute.regex)));
		}
		else
		{
			input.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getStringValue(Attribute.regex)));
		}

		if (node.exist(Attribute.color))
		{
			input.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		if (node.exist(Attribute.text))
		{
			input.set(node.getString(Attribute.text));
			input.setCaretPosition(node.getString(Attribute.text).length());
		}

		input.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent arg0)
			{
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
				input.setCaretPosition(input.getDocument().getLength());
			}
		});

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.fill) && node.getBoolean(Attribute.fill))
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(input, layout);

		if (node.exist(Attribute.action))
		{
			input.setActionCommand(node.getString(Attribute.action));
			input.addActionListener(this);
		}

		if (node.exist(Attribute.keypress))
		{
			input.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent arg0)
				{
				}

				@Override
				public void keyReleased(KeyEvent arg0)
				{
					createEvent(node.getString(Attribute.keypress));
				}

				@Override
				public void keyPressed(KeyEvent arg0)
				{
				}
			});
		}

		input.addMouseListener(this);

		this.components.put(node.getString(Attribute.name), input);
	}

	private void addInputInt(final NodeElement node, Container parent)
	{
		boolean positive = false;

		if (node.exist(Attribute.positive))
		{
			positive = node.getBoolean(Attribute.positive);
		}

		final ExtendedInputInt input = new ExtendedInputInt(node.getInt(Attribute.width), node.getInt(Attribute.length), positive);

		if (node.exist(Attribute.text))
		{
			input.set(node.getString(Attribute.text));
		}

		if (node.exist(Attribute.tooltip))
		{
			input.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			input.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.color))
		{
			input.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.fill) && node.getBoolean(Attribute.fill))
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(input, layout);

		if (node.exist(Attribute.action))
		{
			input.setActionCommand(node.getString(Attribute.action));
			input.addActionListener(this);
		}

		if (node.exist(Attribute.keypress))
		{
			input.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent arg0)
				{
				}

				@Override
				public void keyReleased(KeyEvent arg0)
				{
					createEvent(node.getString(Attribute.keypress));
				}

				@Override
				public void keyPressed(KeyEvent arg0)
				{
				}
			});
		}

		input.addMouseListener(this);

		this.components.put(node.getString(Attribute.name), input);
	}

	private void addInputDecimal(final NodeElement node, Container parent)
	{
		boolean positive = false;
		int decimals = 2;

		if (node.exist(Attribute.positive))
		{
			positive = node.getBoolean(Attribute.positive);
		}

		if (node.exist(Attribute.decimals))
		{
			decimals = node.getInt(Attribute.decimals);
		}

		ExtendedInputDecimal input = new ExtendedInputDecimal(node.getInt(Attribute.width), node.getInt(Attribute.length), decimals, positive);

		if (node.exist(Attribute.text))
		{
			input.set(node.getString(Attribute.text));
		}

		if (node.exist(Attribute.tooltip))
		{
			input.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			input.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.color))
		{
			input.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.fill) && node.getBoolean(Attribute.fill))
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(input, layout);

		if (node.exist(Attribute.action))
		{
			input.setActionCommand(node.getString(Attribute.action));
			input.addActionListener(this);
		}

		if (node.exist(Attribute.keypress))
		{
			input.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent arg0)
				{
				}

				@Override
				public void keyReleased(KeyEvent arg0)
				{
					createEvent(node.getString(Attribute.keypress));
				}

				@Override
				public void keyPressed(KeyEvent arg0)
				{
				}
			});
		}

		input.addMouseListener(this);

		this.components.put(node.getString(Attribute.name), input);
	}

	private void addInputPassoword(final NodeElement node, Container parent)
	{
		final ExtendedInputPassword input = new ExtendedInputPassword(node.getInt(Attribute.width));

		if (node.exist(Attribute.tooltip))
		{
			input.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			input.setFont(this.fonts.get(node.getString(Attribute.name)));
		}

		if (node.exist(Attribute.uppercase))
		{
			input.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getBoolean(Attribute.uppercase), node.getStringValue(Attribute.regex)));
		}
		else
		{
			input.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getStringValue(Attribute.regex)));
		}

		if (node.exist(Attribute.text))
		{
			input.set(node.getString(Attribute.text));
			input.setCaretPosition(node.getString(Attribute.text).length());
		}

		if (node.exist(Attribute.color))
		{
			input.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		input.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent arg0)
			{
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
				input.setCaretPosition(input.getDocument().getLength());
			}
		});

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.fill) && node.getBoolean(Attribute.fill))
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(input, layout);

		if (node.exist(Attribute.action))
		{
			input.setActionCommand(node.getString(Attribute.action));
			input.addActionListener(this);
		}

		if (node.exist(Attribute.keypress))
		{
			input.addKeyListener(new KeyListener()
			{

				@Override
				public void keyTyped(KeyEvent arg0)
				{
				}

				@Override
				public void keyReleased(KeyEvent arg0)
				{
					createEvent(node.getString(Attribute.keypress));
				}

				@Override
				public void keyPressed(KeyEvent arg0)
				{
				}
			});
		}

		input.addMouseListener(this);

		this.components.put(node.getString(Attribute.name), input);
	}

	private void addTextArea(NodeElement node, Container parent)
	{
		ExtendedTextArea area = new ExtendedTextArea();

		if (node.exist(Attribute.tooltip))
		{
			area.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			area.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.editable))
		{
			area.setEditable(node.getBoolean(Attribute.editable));
		}

		if (node.exist(Attribute.length))
		{
			if (node.exist(Attribute.uppercase))
			{
				area.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getBoolean(Attribute.uppercase)));
			}
			else
			{
				area.setDocument(new InputLimiter(node.getInt(Attribute.length), node.getStringValue(Attribute.regex)));
			}
		}

		if (node.exist(Attribute.text))
		{
			area.set(node.getString(Attribute.text));
			area.setCaretPosition(node.getString(Attribute.text).length());
		}

		if (node.exist(Attribute.color))
		{
			area.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(node.getInt(Attribute.width), node.getInt(Attribute.height)));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(node.getInt(Attribute.width), node.getInt(Attribute.height)));
		panel.add(scroll, BorderLayout.PAGE_START);

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(panel, layout);

		area.addMouseListener(this);
		this.components.put(node.getString(Attribute.name), area);
	}

	private void addButton(NodeElement node, Container parent)
	{
		ExtendedButton button = new ExtendedButton(node.getString(Attribute.text), node.getInt(Attribute.width), node.getIntValue(Attribute.height), node.getString(Attribute.icon));

		if (node.exist(Attribute.tooltip))
		{
			button.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.font))
		{
			button.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.color))
		{
			button.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		GridBagConstraints layout = new GridBagConstraints();

		if (node.exist(Attribute.fill) && (!node.getBoolean(Attribute.fill)))
		{
			layout.fill = GridBagConstraints.NONE;
		}
		else
		{
			layout.fill = GridBagConstraints.HORIZONTAL;
		}

		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(button, layout);

		if (node.exist(Attribute.action))
		{
			button.setActionCommand(node.getString(Attribute.action));
			button.addActionListener(this);
		}

		button.addMouseListener(this);
		this.components.put(node.getString(Attribute.name), button);
	}

	private void addCheckBox(NodeElement node, Container parent)
	{
		ExtendedCheckBox check = new ExtendedCheckBox();

		if (node.exist(Attribute.tooltip))
		{
			check.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.selected))
		{
			check.set(node.getBoolean(Attribute.selected));
		}

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(check, layout);

		if (node.exist(Attribute.action))
		{
			check.getCheckElement().setActionCommand(node.getString(Attribute.action));
			check.getCheckElement().addActionListener(this);
		}

		check.addMouseListener(this);
		this.components.put(node.getString(Attribute.name), check);
	}

	private void addRadioButton(NodeElement node, Container parent)
	{
		final ExtendedRadioButton radio = new ExtendedRadioButton();

		String groupName = node.getString(Attribute.group);

		if (this.radioGroups.containsKey(groupName))
		{
			ButtonGroup group = this.radioGroups.get(groupName);
			group.add(radio);
		}
		else
		{
			ButtonGroup group = new ButtonGroup();
			group.add(radio);
			this.radioGroups.put(groupName, group);
		}

		if (node.exist(Attribute.tooltip))
		{
			radio.setTooltip(node.getString(Attribute.tooltip));
		}

		if (node.exist(Attribute.selected))
		{
			radio.select(node.getBoolean(Attribute.selected));
		}

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;
		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(radio, layout);

		if (node.exist(Attribute.action))
		{
			radio.setActionCommand(node.getString(Attribute.action));
			radio.addActionListener(this);
		}

		radio.addMouseListener(this);
		this.components.put(node.getString(Attribute.name), radio);
	}

	private void addGroupBox(NodeElement node, Container parent)
	{
		ExtendedGroupBox group = new ExtendedGroupBox(node.getString(Attribute.text));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(group, layout);

		if (node.exist(Attribute.font))
		{
			group.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.color))
		{
			group.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		NodeElement[] children = node.getNodes();

		for (NodeElement current : children)
		{
			processComponent(current, group);
		}

		this.components.put(node.getString(Attribute.name), group);
	}

	private void addLayout(NodeElement node, Container parent)
	{
		JPanel panel = new JPanel();
		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(panel, layout);

		NodeElement[] children = node.getNodes();

		for (NodeElement current : children)
		{
			processComponent(current, panel);
		}
	}

	private void addTabbedPane(NodeElement node, Container parent)
	{
		final JTabbedPane pane = new JTabbedPane();
		pane.setPreferredSize(new Dimension(node.getInt(Attribute.width), node.getInt(Attribute.height)));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(pane, layout);

		final OptionTask taskOption = this.task;

		pane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent event)
			{
				ExtendedTab tab = (ExtendedTab)pane.getSelectedComponent();
				taskOption.eventDispatcher(tab.getAction());
			}
		});

		if (node.exist(Attribute.font))
		{
			pane.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		NodeElement[] children = node.getNodes();

		for (NodeElement tab : children)
		{
			ExtendedTab panel = new ExtendedTab(node.getInt(Attribute.width), node.getInt(Attribute.height));

			if (node.exist(Attribute.color))
			{
				panel.setForeground(this.colors.get(node.getString(Attribute.color)));
			}

			if (tab.exist(Attribute.action))
			{
				panel.setAction(tab.getString(Attribute.action));
			}

			NodeElement[] tab_content = tab.getNodes();

			for (NodeElement current : tab_content)
			{
				processComponent(current, panel);
			}

			pane.addTab(tab.getString(Attribute.text) + "  ", ImageStore.getIcon(tab.getString(Attribute.icon)), panel);
		}

		this.components.put(node.getString(Attribute.name), pane);
	}

	private void addProgressBar(NodeElement node, Container parent)
	{
		ExtendedProgressBar progress = new ExtendedProgressBar(node.getInt(Attribute.width));

		if (node.exist(Attribute.font))
		{
			progress.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.min))
		{
			progress.setMinimum(node.getInt(Attribute.min));
		}

		if (node.exist(Attribute.max))
		{
			progress.setMaximum(node.getInt(Attribute.max));
		}

		if (node.exist(Attribute.start))
		{
			progress.setValue(node.getInt(Attribute.start));
		}

		if (node.exist(Attribute.label))
		{
			progress.setStringPainted(node.getBoolean(Attribute.label));
		}

		if (node.exist(Attribute.color))
		{
			progress.setForeground(this.colors.get(node.getString(Attribute.color)));
		}

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(progress, layout);

		this.components.put(node.getString(Attribute.name), progress);
	}

	private void addImage(NodeElement node, Container parent)
	{
		ExtendedImage image = new ExtendedImage(node.getString(Attribute.source), node.getInt(Attribute.width), node.getInt(Attribute.height), node.exist(Attribute.border) && node.getBoolean(Attribute.border));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(image, layout);

		if (node.exist(Attribute.background))
		{
			image.setColor(this.colors.get(node.getString(Attribute.background)));
		}

		this.components.put(node.getString(Attribute.name), image);
	}

	private void addDateChooser(NodeElement node, Container parent)
	{
		ExtendedDateChooser date = new ExtendedDateChooser();

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(date, layout);

		if (node.exist(Attribute.font))
		{
			date.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.today) && node.getBoolean(Attribute.today))
		{
			date.setToday();
		}

		date.addMouseListener(this);
		this.components.put(node.getString(Attribute.name), date);
	}

	private void addComboBox(NodeElement node, Container parent)
	{
		ExtendedComboBox combo = new ExtendedComboBox(node.getInt(Attribute.width));

		GridBagConstraints layout = new GridBagConstraints();
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(combo, layout);

		NodeElement[] items = node.getNodes();

		for (NodeElement item : items)
		{
			ExtendedComboBoxItem current = new ExtendedComboBoxItem(item.getString(Attribute.code), item.getString(Attribute.value));

			if (item.exist(Attribute.icon))
			{
				current.setIcon(item.getString(Attribute.icon));
			}

			combo.addNewItem(current);
		}

		if (node.exist(Attribute.editable))
		{
			combo.setEnabled(node.getBoolean(Attribute.editable));
		}

		if (node.exist(Attribute.max))
		{
			combo.setMaximumRowCount(node.getInt(Attribute.max));
		}

		if (node.exist(Attribute.font))
		{
			combo.setFont(this.fonts.get(node.getString(Attribute.font)));
		}

		if (node.exist(Attribute.initial))
		{
			combo.set(node.getString(Attribute.initial));
		}

		combo.setSuggest();

		this.components.put(node.getString(Attribute.name), combo);
	}

	private void addTable(NodeElement node, Container parent)
	{
		ExtendedTable table = new ExtendedTable(this, node.getInt(Attribute.width), node.getInt(Attribute.height));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(table, layout);

		ColumnType[] columnList = null;
		JPopupMenu popMenu = null;
		JToolBar toolBar = new JToolBar();
		NodeElement[] content = node.getNodes();

		for (NodeElement current : content)
		{

			WindowComponent type = WindowComponent.valueOf(current.getName());

			if (type.equals(WindowComponent.toolbar))
			{
				fillTableToolBar(toolBar, current);
			}
			else if (type.equals(WindowComponent.columns))
			{
				columnList = getTableColumns(current);
			}
			else if (type.equals(WindowComponent.popup_menu))
			{
				popMenu = getPopMenu(current);
			}
		}

		table.createToolBar(toolBar);
		table.createTable(columnList, popMenu);

		if (node.exist(Attribute.action))
		{
			table.setAction(node.getString(Attribute.action));
		}

		this.components.put(node.getString(Attribute.name), table);
	}

	private void fillTableToolBar(JToolBar toolBar, NodeElement root)
	{
		NodeElement[] content = root.getNodes();

		for (NodeElement node : content)
		{
			WindowComponent type = WindowComponent.valueOf(node.getName());

			if (type.equals(WindowComponent.item))
			{
				JButton button = getToolBarButtonItem(node.getString(Attribute.icon), node.getString(Attribute.action));
				toolBar.add(button);

				this.components.put(node.getString(Attribute.name), button);
			}
		}
	}

	private JButton getToolBarButtonItem(String icon, String code)
	{
		JButton button = new JButton();
		button.setActionCommand(code);
		button.addActionListener(this);
		button.setIcon(ImageStore.getIcon(Constants.TABLE_TOOLBAR_PATH + icon));

		return button;
	}

	private ColumnType[] getTableColumns(NodeElement node)
	{
		NodeElement[] columns = node.getNodes();
		ColumnType[] columnList = new ColumnType[columns.length];

		for (int i = 0; i < columns.length; i++)
		{
			NodeElement column = columns[i];

			if (column.exist(Attribute.width))
			{
				columnList[i] = new ColumnType(column.getString(Attribute.name), column.getString(Attribute.code), column.getInt(Attribute.width), ColumnType.Type.valueOf(column.getString(Attribute.type).toUpperCase()));
			}
			else
			{
				columnList[i] = new ColumnType(column.getString(Attribute.name), column.getString(Attribute.code), 0, ColumnType.Type.valueOf(column.getString(Attribute.type).toUpperCase()));
			}
		}

		return columnList;
	}

	private JPopupMenu getPopMenu(NodeElement root)
	{
		JPopupMenu popMenu = new JPopupMenu();

		NodeElement[] children = root.getNodes();

		for (NodeElement node : children)
		{
			WindowComponent type = WindowComponent.valueOf(node.getName());

			if (type.equals(WindowComponent.item))
			{
				popMenu.add(getPopupMenuItem(node));
			}
			else if (type.equals(WindowComponent.separator))
			{
				popMenu.addSeparator();
			}
		}

		return popMenu;
	}

	private JMenuItem getPopupMenuItem(NodeElement node)
	{
		JMenuItem menu = new JMenuItem(" " + node.getString(Attribute.text));

		if (node.exist(Attribute.icon))
		{
			menu.setIcon(ImageStore.getIcon(node.getString(Attribute.icon)));
		}

		menu.setFont(FontStore.getDefaultFont());
		menu.setPreferredSize(new Dimension((int)menu.getPreferredSize().getWidth() + 5, 30));
		menu.setActionCommand(node.getString(Attribute.action));
		menu.addActionListener(this);
		menu.setEnabled(true);
		menu.setVisible(true);

		this.components.put(node.getString(Attribute.name), menu);

		return menu;
	}

	private void addChartPie(NodeElement node, Container parent)
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		JFreeChart pie = null;

		if (node.getString(Attribute.dimension).equals(WindowManager.DIMENSION_2D))
		{
			pie = ChartFactory.createPieChart(node.getString(Attribute.title), dataset, true, true, false);
		}
		else if (node.getString(Attribute.dimension).equals(WindowManager.DIMENSION_3D))
		{
			pie = ChartFactory.createPieChart3D(node.getString(Attribute.title), dataset, true, true, false);
		}

		ExtendedChartPie chart = new ExtendedChartPie(pie, dataset, node.getInt(Attribute.width), node.getInt(Attribute.height));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(chart, layout);

		this.components.put(node.getString(Attribute.name), chart);
	}

	private void addChartLine(NodeElement node, Container parent)
	{
		XYSeriesCollection dataset = new XYSeriesCollection();
		JFreeChart line = ChartFactory.createXYLineChart(node.getString(Attribute.title), node.getString(Attribute.x_axis), node.getString(Attribute.y_axis), dataset, PlotOrientation.VERTICAL, true, true, false);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesShapesVisible(0, true);
		line.getXYPlot().setRenderer(renderer);

		ExtendedChartLine chart = new ExtendedChartLine(line, dataset, node.getInt(Attribute.width), node.getInt(Attribute.height));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(chart, layout);

		this.components.put(node.getString(Attribute.name), chart);
	}

	private void addChartBar(NodeElement node, Container parent)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		JFreeChart bar = null;

		if (node.getString(Attribute.dimension).equals(WindowManager.DIMENSION_2D))
		{
			bar = ChartFactory.createBarChart3D(node.getString(Attribute.title), node.getString(Attribute.x_axis), node.getString(Attribute.y_axis), dataset, PlotOrientation.VERTICAL, true, true, false);
		}
		else if (node.getString(Attribute.dimension).equals(WindowManager.DIMENSION_3D))
		{
			bar = ChartFactory.createBarChart3D(node.getString(Attribute.title), node.getString(Attribute.x_axis), node.getString(Attribute.y_axis), dataset, PlotOrientation.VERTICAL, true, true, false);
		}

		ExtendedChartBar chart = new ExtendedChartBar(bar, dataset, node.getInt(Attribute.width), node.getInt(Attribute.height));

		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
		layout.anchor = GridBagConstraints.FIRST_LINE_START;

		if (node.exist(Attribute.col_span))
		{
			layout.gridwidth = node.getInt(Attribute.col_span);
		}

		if (node.exist(Attribute.row_span))
		{
			layout.gridheight = node.getInt(Attribute.row_span);
		}

		layout.gridx = node.getInt(Attribute.x);
		layout.gridy = node.getInt(Attribute.y);
		layout.insets = new Insets(node.getIntValue(Attribute.margin_top), node.getIntValue(Attribute.margin_left), node.getIntValue(Attribute.margin_bottom), node.getIntValue(Attribute.margin_right));
		parent.add(chart, layout);

		this.components.put(node.getString(Attribute.name), chart);
	}

	public void clearInputsBorders()
	{
		Enumeration<Object> list = this.components.elements();

		while (list.hasMoreElements())
		{
			Object current = list.nextElement();

			if (current instanceof ExtendedInputText)
			{
				ExtendedInputText input = (ExtendedInputText)current;
				input.clearBorderColor();
			}
			else if (current instanceof ExtendedInputInt)
			{
				ExtendedInputInt input = (ExtendedInputInt)current;
				input.clearBorderColor();
			}
			else if (current instanceof ExtendedInputDecimal)
			{
				ExtendedInputDecimal input = (ExtendedInputDecimal)current;
				input.clearBorderColor();
			}
			else if (current instanceof ExtendedInputPassword)
			{
				ExtendedInputPassword input = (ExtendedInputPassword)current;
				input.clearBorderColor();
			}
			else if (current instanceof ExtendedComboBox)
			{
				ExtendedComboBox combo = (ExtendedComboBox)current;
				combo.clearBorderColor();
			}
			else if (current instanceof ExtendedDateChooser)
			{
				ExtendedDateChooser date = (ExtendedDateChooser)current;
				date.clearBorderColor();
			}
			else if (current instanceof ExtendedTextArea)
			{
				ExtendedTextArea textArea = (ExtendedTextArea)current;
				textArea.clearBorderColor();
			}
		}
	}

	// ================================ LISTENERS ================================

	@Override
	public void mouseEntered(MouseEvent event)
	{
		Object source = event.getSource();

		if (source instanceof ToolTipInterface)
		{
			ToolTipInterface component = (ToolTipInterface)source;
			setStatusBar(component.getTooltip());
		}

		if (source instanceof TextCursorInterface)
		{
			this.container.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		}
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
	}

	@Override
	public void mouseExited(MouseEvent event)
	{
		setStatusBar("");
		Object source = event.getSource();

		if (source instanceof TextCursorInterface)
		{
			this.container.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mousePressed(MouseEvent event)
	{
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
	}

	public void createEvent(String code)
	{
		this.task.eventDispatcher(code);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		this.task.eventDispatcher(event.getActionCommand());
	}
}