package com.tvd12.test.testing.reflect;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import com.tvd12.test.reflect.ReflectMethodUtil;

public class ReflectMethodUtilTest {
    
    @Test
    public void testGetMethodOnClassValidCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing0", ClassB.class);
        assertNotNull(method);
        method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        assertNotNull(method);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testGetMethodOnClassInvalidCase() {
        ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.TYPE);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testInvokeMethodWithMethodInvalidCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        ReflectMethodUtil.invokeMethod(method, new ClassA(), new Integer(1));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testInvokeMethodWithMethodInvalidCase1() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        ReflectMethodUtil.invokeMethod(method, new ClassA());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testInvokeMethodWithMethodInvalidCase2() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        ReflectMethodUtil.invokeMethod(method, new Object(), new Integer(1));
    }
    
    @Test
    public void testGetMethodOnObjectValidCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing0", new ClassB());
        assertNotNull(method);
        method = ReflectMethodUtil.getMethod("doSomeThing1", new ClassA(), 10);
        assertNotNull(method);
    }
    
    @Test
    public void testInvokeMethodByNameCase() {
        ReflectMethodUtil.invokeMethod("doSomeThing0", new ClassB());
        int value = (int) ReflectMethodUtil.invokeMethod("doSomeThing1", new ClassA(), 10);
        assertEquals(10, value);
    }
    
    @Test
    public void testInvokeMethodCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", new ClassA(), new Integer(10));
        assertNotNull(method);
        int value = (int) ReflectMethodUtil.invokeMethod(method, new ClassA(), 10);
        assertEquals(10, value);
    }
    
    @Test
    public void testCallStaticMethodCase() {
        ReflectMethodUtil.invokeMethod("printSomeThing", new ClassA());
        ReflectMethodUtil.invokeStaticMethod("printSomeThing", ClassA.class);
        Method method = ReflectMethodUtil.getMethod("printSomeThing1", ClassA.class, String.class);
        ReflectMethodUtil.invokeStaticMethod(method, "mobile phone");
        
        Class<?>[] types = (Class<?>[]) ReflectMethodUtil.invokeStaticMethod(
                ReflectMethodUtil.getMethod("getParameterTypes", ReflectMethodUtil.class, Object[].class), 
                new Object[] {new Object[] {"String"}});
        assertEquals(1, types.length);
        
        types = (Class<?>[]) ReflectMethodUtil.invokeStaticMethod(
                ReflectMethodUtil.getMethod("getParameterTypes", ReflectMethodUtil.class, Object[].class), 
                new Object[] {(Object[])null});
        assertNull(types);
    }
    
    public static abstract class Class0 {
        public abstract void doSomeThing0();
    }
    
    public static class ClassA extends Class0 {
        @SuppressWarnings("unused")
        private static void printSomeThing() {
            System.out.println("dung");
        }
        @SuppressWarnings("unused")
        private static void printSomeThing1(String thing) {
            System.out.println(thing);
        }
        @SuppressWarnings("unused")
        private void doSomeThing() {
            int a = 1 + 1;
        }
        
        @SuppressWarnings("unused")
        private int doSomeThing1(Integer a) {
            return a;
        }

        @Override
        public void doSomeThing0() {
            
        }
    }
    
    public static class ClassB extends ClassA {
        public void doSomeThing1() {}
    }
}
