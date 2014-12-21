package share.core;

import java.util.Calendar;

public class Date extends Shareable {
	
	private static final long serialVersionUID = 1L;
	
	public int year = 0;
	public int month = 0;
	public int day = 0;
	
	public Date() {
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public Date(Calendar calendar) {
		this.day = calendar.get(Calendar.DATE);
		this.month = calendar.get(Calendar.MONTH) + 1;
		this.year = calendar.get(Calendar.YEAR);
	}
	
	public Date(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		this.day = calendar.get(Calendar.DATE);
		this.month = calendar.get(Calendar.MONTH) + 1;
		this.year = calendar.get(Calendar.YEAR);
	}
	
	public java.sql.Date getSQLDate() {
		return new java.sql.Date(getCalendar().getTimeInMillis());
	}
	
	public static Calendar getToday() {
		Calendar today = Calendar.getInstance();
		today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
		today.set(Calendar.MILLISECOND, 0);
		
		return today;
	}
	
	public static Date getTodayDate() {
		return new Date(Date.getToday());
	}
	
	public Calendar getCalendar() {
		Calendar date = Calendar.getInstance();
		date.set(this.year, this.month - 1, this.day, 0, 0, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date;
	}
	
	public String toString() {
		String result = "";
		
		if (!isEmpty()) {
			if (this.day < 10) {
				result += "0";
			}
			
			result += this.day + "/";
			
			if (this.month < 10) {
				result += "0";
			}
			
			result += this.month + "/" + this.year;
		}
		
		return result;
	}
	
	public boolean isBefore(Date date) {
		java.sql.Date local = new java.sql.Date(getCalendar().getTimeInMillis());
		java.sql.Date remote = new java.sql.Date(date.getCalendar().getTimeInMillis());
		
		return local.before(remote);
	}
	
	public boolean isAfter(Date date) {
		java.sql.Date local = new java.sql.Date(getCalendar().getTimeInMillis());
		java.sql.Date remote = new java.sql.Date(date.getCalendar().getTimeInMillis());
		
		return local.after(remote);
	}
	
	public boolean isEmpty() {
		return (this.year == 0) && (this.month == 0) && (this.day == 0);
	}
	
	public boolean equals(Date date) {
		return (date.getYear() == this.year) && (date.getMonth() == this.month) && (date.getDay() == this.day);
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
}