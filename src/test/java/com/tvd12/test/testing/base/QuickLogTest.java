package com.tvd12.test.testing.base;

import org.testng.annotations.Test;

import com.tvd12.test.base.QuickLog;

public class QuickLogTest {

    @Test
    public void test() {
        QuickLog log = new QuickLog();
        
        log.ERROR("");
        try {
        		a();
        }
        catch (Exception e) {
        		log.ERROR("how?", e);
		}
        
        log.INFO("");
        log.INFO("", new Exception());
        
    }
    
    public void a() {
    		try {
    			b();
    		}
    		catch (Exception e) {
    			throw new IllegalStateException("can be", e);
		}
    }
    
    public void b() {
    		try {
    			throw new IllegalArgumentException("no one");
    		}
    		catch (Exception e) {
    			throw new RuntimeException("error runtime", e);
		}
    }
    
}
