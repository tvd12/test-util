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
    	
    }
    
    public static class B extends A {
    }
    
}
