package com.tvd12.test.testing.base;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;

public class BaseTestingTest {

    @Test
    public void test() {
        BaseTest base = new BaseTest();
        base.beforeClass();
        base.afterClass();
    }
    
}
