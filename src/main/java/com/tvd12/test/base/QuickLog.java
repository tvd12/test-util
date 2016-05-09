package com.tvd12.test.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class support for logging quickly,
 * you don't need declare a logger object with long syntax
 * 
 * @author tavandung12
 *
 */
public class QuickLog {

    /**
     * @return logger
     */
    public Logger LOGGER() {
        return LoggerFactory.getLogger(getClass());
    }
    
    /**
     * Log a message at the INFO level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void INFO(String msg) {
        LOGGER().info(msg);
    }
    
    /**
     * Log an exception (throwable) at the INFO level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void INFO(String msg, Throwable e) {
        LOGGER().info(msg, e);
    }
    
    /**
     * Log a message at the DEBUG level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void DEBUG(String msg) {
        LOGGER().debug(msg);
    }
    
    /**
     * Log an exception (throwable) at the DEBUG level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void DEBUG(String msg, Throwable e) {
        LOGGER().debug(msg, e);
    }
    
    /**
     * Log a message at the WARN level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void WARN(String msg) {
        LOGGER().warn(msg);
    }
    
    /**
     * Log an exception (throwable) at the WARN level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void WARN(String msg, Throwable e) {
        LOGGER().warn(msg, e);
    }
    
    /**
     * Log a message at the ERROR level according to the specified format and arguments.
     * 
     * @param msg message
     */
    public void ERROR(String msg) {
        LOGGER().error(msg);
    }
    
    /**
     * Log an exception (throwable) at the ERROR level with an accompanying message.
     * 
     * @param msg message
     * @param e exception
     */
    public void ERROR(String msg, Throwable e) {
        LOGGER().error(msg, e);
    }
    
}
