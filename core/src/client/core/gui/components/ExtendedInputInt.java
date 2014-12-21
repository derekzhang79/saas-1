package client.core.gui.components;

import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ExtendedInputInt extends ExtendedInputText
{
	private static final long serialVersionUID = -3236795954916770348L;

	public ExtendedInputInt(int width, int length, boolean positive)
	{
		super(width);
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setDocument(new IntTextDocument(length, positive));
	}
	
	public int getInt()
	{
		try
		{
			return Integer.parseInt(getText());
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public long getLong()
	{
		try
		{
			return Long.parseLong(getText());
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public boolean equals(int number)
	{
		return (getInt() == number);
	}
	
	public boolean equals(long number)
	{
		return (getLong() == number);
	}
	
	public void set(int value)
	{
		setText(String.valueOf(value));
	}
	
	public void set(long value)
	{
		setText(String.valueOf(value));
	}
	
	@Override
	public boolean isEmpty()
	{
		return ((getInt() == 0) && (getLong() == 0));
	}
	
	private class IntTextDocument extends PlainDocument
	{
		private static final long serialVersionUID = 2497662697352187717L;

		private final int length;
		private final Pattern pattern;
		
		public IntTextDocument(int length, boolean positive)
		{
			this.length = length;
			
			if (positive)
			{
				this.pattern = Pattern.compile("\\d+(\\d+)?$");
			}
			else
			{
				this.pattern = Pattern.compile("^[\\-]?\\d+(\\d+)?$");
			}
		}
		
		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			String newStr = new StringBuffer(getText(0, getLength())).insert(offset, str).toString();
			
			if (!this.pattern.matcher(newStr).matches())
			{
				return;
			}
			else if (newStr.replace("-", "").length() > this.length)
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