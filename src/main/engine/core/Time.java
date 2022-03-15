package main.engine.core;

import java.time.LocalDate;
import java.time.LocalTime;

public class Time {

    private static final long SEC = 1000000000L;

    public static double getTime() {

        return (double) System.nanoTime() / (double) SEC;

    }
    
    public static double getNanoTime() {
        
        return (double) System.nanoTime();
        
    }
    
    public static double getUnixTime() {
        
        return (double) System.currentTimeMillis();
        
    }
    
    public static String getLocalDate() {
        
        return LocalDate.now().toString();
        
    }
    
    public static String getLocalTime() {
        
        return LocalTime.now().toString();
        
    }

}
