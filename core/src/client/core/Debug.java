package client.core;

import share.core.Environment;

public class Debug {
	
	private static String ERRORS = "";
	private static String INFO = "";
	
	private static long totalTime = 0;
	private static long totalRequests = 0;
	
	public static void setInfo(String info) {
		Debug.INFO += info.trim() + Environment.newLine() + Environment.newLine();
		System.out.println(info.trim() + Environment.newLine());
	}
	
	public static void setInfo() {
		Debug.setInfo("");
	}
	
	public static String getInfo() {
		return Debug.INFO;
	}
	
	public static void setError(Exception exception) {
		Debug.ERRORS += exception.toString() + Environment.newLine();
		
		StackTraceElement[] stack = exception.getStackTrace();
		
		for (StackTraceElement element : stack) {
			Debug.ERRORS += "\tat " + element + Environment.newLine();
		}
		
		Debug.ERRORS += Environment.newLine() + "------------------------------------------------" + Environment.newLine() + Environment.newLine();
		
		exception.printStackTrace();
	}
	
	public static String getErrors() {
		return Debug.ERRORS;
	}
	
	public static void setTimeRequest(long time) {
		Debug.totalTime += time;
		Debug.totalRequests++;
		
		Debug.setInfo("»» Average time: " + (Debug.totalTime / Debug.totalRequests) + " ms");
	}
}