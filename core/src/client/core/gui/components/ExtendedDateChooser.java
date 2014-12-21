package client.core.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import share.core.Date;
import client.core.gui.fonts.FontStore;
import com.toedter.calendar.JDateChooser;

public class ExtendedDateChooser extends JDateChooser
{
	private static final long serialVersionUID = 7284662336505282868L;

	private static final int DEFAULT_WIDTH = 110;
	private static final int DEFAULT_HEIGHT = 22;

	public ExtendedDateChooser()
	{
		setFont(FontStore.getDefaultFont(false));
		setPreferredSize(new Dimension(ExtendedDateChooser.DEFAULT_WIDTH, ExtendedDateChooser.DEFAULT_HEIGHT));
		getDateEditor().setDateFormatString("dd/MM/yyyy");
	}

	public void setToday()
	{
		setCalendar(Date.getToday());
	}

	public Date get()
	{
		if (getDate() == null)
		{
			return new Date();
		}
		else
		{
			return new Date(getCalendar());
		}
	}

	public boolean isBefore(ExtendedDateChooser date)
	{
		return get().isBefore(date.get());
	}

	public boolean equals(Date date)
	{
		return get().equals(date);
	}

	public void set(Date date)
	{
		setCalendar(date.getCalendar());
	}

	public void clear()
	{
		setDate(null);
	}

	public boolean isEmpty()
	{
		return get().isEmpty();
	}

	@Override
	public String toString()
	{
		return get().toString();
	}

	public void setBorderColor(Color color)
	{
		setBorder(new LineBorder(color, 0));
	}

	public void clearBorderColor()
	{
		setBorder(new LineBorder(Color.GRAY, 0));
	}

	public void focus()
	{
		requestFocus();
	}
}