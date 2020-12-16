package com.tvd12.test.performance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	
	
	//number of thread use to concurrent test mode
	private int threadCount = 0;
	
	/**
	 * builder method
	 * 
	 * @return a Performance instance
	 */
	public static Performance create() {
		return new Performance();
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
	 * Set number of thread use in concurrent test mode
	 * 
	 * @param threadCount the number of threads
	 * @return this pointer
	 */
	public Performance threadCount(int threadCount) {
		this.threadCount = threadCount;
		return this;
	}
	
	/**
	 * @return script running time
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * execute script test and calculate offset time
	 * 
	 * @param script script to test
	 * @return this pointer
	 */
	public Performance test(Script script) {
		try {
			if(threadCount <= 0)
				testInSyncMode(script);
			else
				testInConcurrentMode(script);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return this;
	}
	
	private void testInSyncMode(Script script) throws Exception {
		long startTime = System.currentTimeMillis();
		for(int i = 0 ; i < loopCount ; ++i)
			script.execute();
		time = System.currentTimeMillis() - startTime;
	}
	
	private void testInConcurrentMode(Script script) throws Exception {
		CountDownLatch barrier = new CountDownLatch(loopCount);
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		long startTime = System.currentTimeMillis();
		for(int i = 0 ; i < loopCount ; ++i) {
			executorService.execute(() -> {
				script.execute();
				barrier.countDown();
			});
		}
		barrier.await();
		time = System.currentTimeMillis() - startTime;
		executorService.shutdown();
	}
	
}
