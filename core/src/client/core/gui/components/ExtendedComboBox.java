package client.core.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import client.core.gui.FontStore;

@SuppressWarnings("rawtypes")
public class ExtendedComboBox extends JComboBox
{
	private static final long serialVersionUID = -5721444558358371563L;

	private static final int DEFAULT_HEIGHT = 22;

	private boolean hide_flag = false;

	private List<ExtendedComboBoxItem> items = new ArrayList<ExtendedComboBoxItem>();

	@SuppressWarnings("unchecked")
	public ExtendedComboBox(int width)
	{
		setPreferredSize(new Dimension(width, ExtendedComboBox.DEFAULT_HEIGHT));
		setFont(FontStore.getDefaultFont());
		setEditable(true);
		setRenderer(new ComboBoxRenderer());
		clearBorderColor();
	}

	public void setSuggest()
	{
		final JTextField textField = (JTextField)getEditor().getEditorComponent();

		setModelDefault();

		addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getModifiers() == 16)
				{
					ExtendedComboBoxItem item = getItem();

					if (item != null)
					{
						setModelDefault();
						set(item.getCode());

						EventQueue.invokeLater(new Runnable()
						{
							@Override
							public void run()
							{
								textField.select(textField.getText().length(), textField.getText().length());
							}
						});
					}
				}
			}
		});

		textField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{

						String text = textField.getText();

						if (text.length() == 0)
						{
							hidePopup();
							setModelDefault();
						}
						else
						{
							DefaultComboBoxModel model = getModel(text);

							if ((model.getSize() == 0) || getHideFlag())
							{
								hidePopup();
								setHideFlag(false);
							}
							else
							{
								setModel(model, text);
								showPopup();
							}
						}
					}
				});
			}

			@Override
			public void keyPressed(KeyEvent e)
			{

				switch (e.getKeyCode())
				{

					case KeyEvent.VK_ENTER:

						ExtendedComboBoxItem item = getItem();

						if (item != null)
						{
							setHideFlag(true);
							setModelDefault();
							set(item.getCode());

							EventQueue.invokeLater(new Runnable()
							{
								@Override
								public void run()
								{
									textField.select(textField.getText().length(), textField.getText().length());
								}
							});
						}
						break;

					case KeyEvent.VK_ESCAPE:
						setHideFlag(true);
						break;
				}
			}
		});
	}

	private void setHideFlag(boolean value)
	{
		this.hide_flag = value;
	}

	private boolean getHideFlag()
	{
		return this.hide_flag;
	}

	private void setModelDefault()
	{
		setModel(new DefaultComboBoxModel<ExtendedComboBoxItem>(getItems()), "");
	}
	
	@SuppressWarnings("unchecked")
	private DefaultComboBoxModel getModel(String text)
	{
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (ExtendedComboBoxItem item : getItems())
		{
			if (item.getValue().toLowerCase().startsWith(text.toLowerCase()))
			{
				model.addElement(item);
			}
		}

		return model;
	}
	
	@SuppressWarnings("unchecked")
	private void setModel(ComboBoxModel model, String text)
	{
		setModel(model);
		setSelectedIndex(-1);
		((JTextField)getEditor().getEditorComponent()).setText(text);
	}

	@SuppressWarnings("unchecked")
	public void addNewItem(ExtendedComboBoxItem element)
	{
		this.items.add(element);
		addItem(element);
	}

	public void selectNone()
	{
		((JTextField)getEditor().getEditorComponent()).setText("");
		setSelectedIndex(-1);
	}

	public ExtendedComboBoxItem getItem()
	{
		ExtendedComboBoxItem result = null;

		if (getSelectedIndex() != -1)
		{
			result = (ExtendedComboBoxItem)getSelectedItem();
		}

		return result;
	}

	private Border getDefaultBorder()
	{
		return BorderFactory.createEmptyBorder(0, 2, 0, 2);
	}

	public void setBorderColor(Color color)
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(color), getDefaultBorder()));
	}

	public void clearBorderColor()
	{
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY), getDefaultBorder()));
	}

	public String get()
	{
		String result = "";

		if (getItem() != null)
		{
			result = getItem().getCode();
		}

		return result;
	}

	public boolean isEmpty()
	{
		return (!isItemSelected());
	}

	public boolean equals(String value)
	{
		return get().equals(value);
	}

	public boolean isItemSelected()
	{
		return (getSelectedIndex() != -1);
	}

	public int getIndexByCode(String code)
	{
		int index = -1;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).getCode().equals(code))
			{
				index = i;
				break;
			}
		}

		return index;
	}

	public void set(String code)
	{
		int index = getIndexByCode(code);

		if (index != -1)
		{
			setSelectedIndex(index);
		}
	}

	public ExtendedComboBoxItem[] getItems()
	{
		ExtendedComboBoxItem[] list = new ExtendedComboBoxItem[this.items.size()];
		this.items.toArray(list);

		return list;
	}

	public void setItems(ExtendedComboBoxItem[] list)
	{
		removeAllItems();

		this.items = new ArrayList<ExtendedComboBoxItem>();

		for (ExtendedComboBoxItem item : list)
		{
			addNewItem(item);
		}
	}

	public void removeByIndex(int index)
	{
		removeItemAt(index);
		this.items.remove(index);
	}

	public void removeByCode(String code)
	{
		int index = getIndexByCode(code);

		if (index != -1)
		{
			removeByIndex(index);
		}
	}

	public void focus()
	{
		requestFocus();
	}

	private class ComboBoxRenderer extends JLabel implements ListCellRenderer
	{
		private static final long serialVersionUID = -2223149637952673189L;

		public ComboBoxRenderer()
		{
			setOpaque(true);
			setFont(FontStore.getDefaultFont());
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{

			if (isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			ExtendedComboBoxItem item = (ExtendedComboBoxItem)value;

			setText(item.getValue());

			int width = getFontMetrics(getFont()).stringWidth(item.getValue());
			setPreferredSize(new Dimension(width, 25));

			if (item.hasIcon())
			{
				setIcon(item.getIcon());
			}
			else
			{
				setIcon(null);
			}

			return this;
		}
	}
}