package com.tvd12.test.testing.performance;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.tvd12.test.performance.MethodPerformance;

public class MethodPerformanceTest {

    @Test
    public void test() {
        MethodPerformance.create()
            .loop(10000000)
            .method((Method)null)
            .method("hello")
            .param("Dung")
            .object(new ClassA())
            .execute()
            .getTime();
    }
    
    public static class ClassA {
        public String hello(String name) {
            return "Hello " + name;
        }
    }
}
