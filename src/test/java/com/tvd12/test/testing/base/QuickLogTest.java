package com.tvd12.test.testing.base;

import org.testng.annotations.Test;

import com.tvd12.test.base.QuickLog;

public class QuickLogTest {

    @Test
    public void test() {
        QuickLog log = new QuickLog();
        log.DEBUG("");
        log.DEBUG("", null);
        
        log.ERROR("");
        log.ERROR("", null);
        
        log.INFO("");
        log.INFO("", null);
        
        log.WARN("");
        log.WARN("", null);
        
    }
    
}
