package com.tvd12.test.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * This class support for logging quickly,
 * you don't need declare a logger object with long syntax
 * 
 * @author tavandung12
 *
 */
public class QuickLog {

	private static final String LEVEL_INFO = "INFO";
	private static final String LEVEL_ERROR = "ERROR";
	
    /**
     * Log a message at the INFO level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void INFO(String msg) {
    		StringBuilder builder = messageBuilder(LEVEL_INFO, msg, null);
        System.out.println(builder);
    }
    
    /**
     * Log an exception (throwable) at the INFO level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void INFO(String msg, Throwable e) {
    	StringBuilder builder = messageBuilder(LEVEL_INFO, msg, e);
        System.out.println(builder);
    }
    
    /**
     * Log a message at the ERROR level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void ERROR(String msg) {
    		StringBuilder builder = messageBuilder(LEVEL_ERROR, msg, null);
        System.out.println(builder);
    }
    
    /**
     * Log an exception (throwable) at the ERROR level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void ERROR(String msg, Throwable e) {
    		StringBuilder builder = messageBuilder(LEVEL_ERROR, msg, e);
        System.out.println(builder);
    }
    
    private StringBuilder messageBuilder(String level, String message, Throwable e) {
    		Date now = new Date();
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss,SSS");
    		StringBuilder builder = new StringBuilder()
    				.append(dateFormat.format(now)).append(" | ")
    				.append(timeFormat.format(now)).append(" | ")
    				.append(level).append(" | ")
    				.append(Thread.currentThread().getName()).append(" | ")
    				.append(getClass().getSimpleName()).append(" |\t| ")
    				.append(message);
    		if(e != null)
    			builder.append("\n").append(getStackTrace(e));
    		return builder;
    }
    
    private String getStackTrace(Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        StringBuffer buffer = sw.getBuffer();
        String string = buffer.toString();
        return string;
   }
    
}
