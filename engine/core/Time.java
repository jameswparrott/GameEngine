package main.engine.core;

public class Time {
	
	private static final long SEC = 1000000000L;
	
	public static double getTime() {
		
		return (double)System.nanoTime() / (double)SEC;
		
	}
	
//	public static double getDelta() {
//	
//		return delta;
//		
//	}
//
//	public static void setDelta(double delta) {
//		
//		Time.delta = delta;
//		
//	}

//	public static final long SEC = 1000000000L;
//	
//	private static double delta;
//	
//	public static long getTime() {
//		
//		return System.nanoTime();
//		
//	}
//	
//	public static double getDelta() {
//		
//		return delta;
//		
//	}
//	
//	public static void setDelta(double delta) {
//		
//		Time.delta = delta;
//		
//	}
	
	
}
