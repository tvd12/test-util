package com.tvd12.test.testing.base;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;

public class QuickLogTest2 extends BaseTest {

    @Test
    public void test() {
        error("");
        try {
        		a();
        }
        catch (Exception e) {
        		error("how?", e);
		}
        
        info("");
        info("", new Exception());
        
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
