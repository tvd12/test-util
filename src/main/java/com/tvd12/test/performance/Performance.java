package com.tvd12.test.performance;

/**
 * A builder class support for performance testing
 * This class run a script test and calculate 
 * time offset
 * 
 * @author tavandung12
 *
 */
public class Performance {

    //script running time
	private long time;
	
	//number of script invocations
	private int loopCount = 1000000;
	
	/**
	 * builder method
	 * 
	 * @return a Performance instance
	 */
	public static Performance create() {
		return new Performance();
	}
	
	/**
	 * execute script test and calculate offset time
	 * 
	 * @param script script to test
	 * @return this pointer
	 */
	public Performance test(Script script) {
		time = System.currentTimeMillis();
		for(int i = 0 ; i < loopCount ; i++)
			script.execute();
		time = System.currentTimeMillis() - time;
		return this;
	}
	
	/**
	 * set number of script invocations
	 * 
	 * @param count number of script invocations
	 * @return this pointer
	 */
	public Performance loop(int count) {
		this.loopCount = count;
		return this;
	}
	
	/**
	 * @return script running time
	 */
	public long getTime() {
		return time;
	}
	
}
