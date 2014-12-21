package client.core;

import java.util.Calendar;

public class TimeManager {
	
	public static long getMilliseconds() {
		return Calendar.getInstance().getTimeInMillis();
	}
}