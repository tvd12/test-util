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
        
        String get = MethodInvoker.create()
        		.method("get")
        		.param(int.class, 1)
        		.params("a")
        		.object(new B())
        		.invoke(String.class);
        assert get.equals("private method: 1-a");
        
        String call = MethodInvoker.create()
        		.method("get")
        		.param(int.class, 1)
        		.params("a")
        		.object(new B())
        		.call();
        assert call.equals("private method: 1-a");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void invokeMethodNameNull() {
        MethodInvoker.create()
            .invoke();
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void invokeMethodStaticClassAndObjectNull() {
        MethodInvoker.create()
            .method("method")
            .invoke();
    }
    
    @Test
    public void invokeMethodStaticNotNull() {
        MethodInvoker.create()
            .staticClass(A.class)
            .method("staticMethod")
            .invoke();
    }
    
    public static class ClassA {
        public String hello(String name) {
            return "Hello " + name;
        }
    }
    
    public static class A {
    		
        private String get(int i, String str) {
            return "private method: " + i + "-" + str;
        }
        
        public String get1() {
            return get(0, "");
        }
        
        public static void staticMethod() {
        }
    	
    }
    
    public static class B extends A {
    }
    
}
