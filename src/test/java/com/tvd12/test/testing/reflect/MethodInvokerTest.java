package com.tvd12.test.testing.reflect;

import org.testng.annotations.Test;

import com.tvd12.test.reflect.MethodInvoker;

import static org.testng.Assert.*;

public class MethodInvokerTest {

    @Test
    public void test() {
        String hello = MethodInvoker.create()
                .method("hello")
                .param("Dung")
                .object(new ClassA())
                .invoke(String.class);
        assertEquals("Hello Dung", hello);
    }
    
    public static class ClassA {
        public String hello(String name) {
            return "Hello " + name;
        }
    }
    
}
