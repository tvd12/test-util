package com.tvd12.test.testing.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.MethodBuilder;
import com.tvd12.test.reflect.ReflectMethodUtil;

import static org.testng.Assert.*;

public class ReflectMethodUtilTest extends BaseTest {
    
    @Test
    public void testGetMethodOnClassValidCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing0", ClassB.class);
        assertNotNull(method);
        method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        assertNotNull(method);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testGetMethodOnClassInvalidCase() {
        ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.TYPE);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        ReflectMethodUtil.invokeMethod(method, new ClassA(), new Integer(1));
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase1() {
        Method method = ReflectMethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        ReflectMethodUtil.invokeMethod(method, new ClassA());
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
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
    
    @Test
    public void getConstructorValidCaseTest() {
        Constructor<?> con = ReflectMethodUtil.getConstructor(ClassC.class);
        assertNotNull(con);
        con = ReflectMethodUtil.getConstructor(ClassC.class, String.class);
        assertNotNull(con);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void getConstructorInvalidCase() {
        ReflectMethodUtil.getConstructor(ClassC.class, Integer.class);
    }
    
    @Test
    public void invokeConstructorValidCaseTest() {
        Object object = ReflectMethodUtil.invokeConstructor(ClassC.class);
        assertNotNull(object);
        object = ReflectMethodUtil.invokeConstructor(ClassC.class, "Dung");
        assertNotNull(object);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void invokeConstructorInvalidCaseTest() {
        ReflectMethodUtil.invokeConstructor(ClassC.class, new Integer(1));
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeConstructorInvalidCase() {
        ReflectMethodUtil.invokeConstructor(ClassD.class);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase3() {
        Method method = MethodBuilder.create()
                .clazz(ClassB.class)
                .method("doSomeThing2")
                .build();
        ReflectMethodUtil.invokeMethod(method, null, String.class);
    }
    
    @Override
    public Class<?> getTestClass() {
        return ReflectMethodUtil.class;
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
        
        public static void doSomeThing2() {}
    }
    
    public static class ClassC {
        public ClassC() {}
        
        public ClassC(String a) {}
        
        public ClassC(String a, String b) {}
    }
    
    public static class ClassD {
        public ClassD() throws IllegalAccessException {
            throw new IllegalAccessException();
        }
    }
}
