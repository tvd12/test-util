package com.tvd12.test.base;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tvd12.test.reflect.ReflectMethodUtil;

/**
 * This class support for test default constructor and
 * calculate and print elapsed time
 * 
 * @author tavandung12
 *
 */
public class BaseTest extends QuickLog {

    // starting time
    private long time = 0;
    
    /**
     * run once before any of the test methods in the class,
     * save starting time and print a log
     */
    @BeforeClass
    public void beforeClass() {
        INFO("===== Start test " + getClass().getSimpleName() + " =====");
        time = System.currentTimeMillis();
    }
    
    /**
     * run once after any of the test methods in the class,
     * calculate elapsed time and print a log
     */
    @AfterClass
    public void afterClass() {
        time = System.currentTimeMillis() - time;
        INFO("===== Finish test " + getClass().getSimpleName() + ", Time elapsed = " + time + " =====");
    }
    
    /**
     * some time, you want to test default constructor,
     * this function will help you
     */
    @Test
    public void testNoArgsConstructor() {
        if(getTestClass() == null) 
            return;
        Object object = ReflectMethodUtil.invokeConstructor(getTestClass());
        assertNotNull(object);
    }
    
    /**
     * Which class you want to test default constructor
     * 
     * @return a class object
     */
    public Class<?> getTestClass() {
        return null;
    }
    
}
