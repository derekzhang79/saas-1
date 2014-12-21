package client.core.gui.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import client.core.gui.TextCursorInterface;
import client.core.gui.ToolTipInterface;

public class ExtendedInputDecimal extends ExtendedInputText implements ToolTipInterface, TextCursorInterface
{
	private static final long serialVersionUID = 1993615537025247597L;

	public ExtendedInputDecimal(int width, int length, int decimals, boolean positive)
	{
		super(width);

		setHorizontalAlignment(SwingConstants.RIGHT);
		setDocument(new DecimalTextDocument(length, decimals, positive));

		addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent arg0)
			{
				if (!getText().isEmpty())
				{
					if (getText().charAt(getText().length() - 1) == ',')
					{
						setText(getText() + "0");
					}
					else if (getText().equals("-"))
					{
						setText("");
					}
				}
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
			}
		});
	}

	public double getValue()
	{
		try
		{
			return Double.parseDouble(getText().replace(",", "."));
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	public void set(double value)
	{
		setText(String.valueOf(value).replace(".", ","));
	}

	public boolean equals(double number)
	{
		return (getValue() == number);
	}

	private class DecimalTextDocument extends PlainDocument
	{
		private static final long serialVersionUID = -8435299560853331056L;

		private final int length;
		private final int decimals;
		private final Pattern pattern;

		public DecimalTextDocument(int length, int decimals, boolean positive)
		{
			this.length = length;
			this.decimals = decimals;

			if (positive)
			{
				this.pattern = Pattern.compile("\\d+(\\,\\d+)?$");
			}
			else
			{
				this.pattern = Pattern.compile("^[\\-]?\\d+(\\,\\d+)?$");
			}
		}

		private String getIntPart(String str)
		{
			String[] parts = str.split(",");

			if (parts.length > 0)
			{
				return parts[0].replace("-", "");
			}
			else
			{
				return str.replace("-", "");
			}
		}

		private String getDecimalPart(String str)
		{
			String[] parts = str.split(",");

			if (parts.length > 1)
			{
				return parts[1];
			}
			else
			{
				return "";
			}
		}

		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			String newStr = new StringBuffer(getText(0, getLength())).insert(offset, str).toString();
			String intPart = getIntPart(newStr);
			String decimalPart = getDecimalPart(newStr);

			if (!this.pattern.matcher(newStr + "0").matches())
			{
				return;
			}
			else if (intPart.length() > this.length)
			{
				return;
			}
			else if (decimalPart.length() > this.decimals)
			{
				return;
			}
			else
			{
				super.insertString(offset, str, attr);
			}
		}
	}
}