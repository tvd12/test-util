package com.tvd12.test.testing.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.MethodBuilder;
import com.tvd12.test.reflect.MethodUtil;

import static org.testng.Assert.*;

public class MethodUtilTest extends BaseTest {
    
    @Test
    public void testGetMethodOnClassValidCase() {
        Method method = MethodUtil.getMethod("doSomeThing0", ClassB.class);
        assertNotNull(method);
        method = MethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        assertNotNull(method);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testGetMethodOnClassInvalidCase() {
        MethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.TYPE);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase() {
        Method method = MethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        MethodUtil.invokeMethod(method, new ClassA(), new Integer(1));
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase1() {
        Method method = MethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        MethodUtil.invokeMethod(method, new ClassA());
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase2() {
        Method method = MethodUtil.getMethod("doSomeThing1", ClassA.class, Integer.class);
        method.setAccessible(false);
        MethodUtil.invokeMethod(method, new Object(), new Integer(1));
    }
    
    @Test
    public void testGetMethodOnObjectValidCase() {
        Method method = MethodUtil.getMethod("doSomeThing0", new ClassB());
        assertNotNull(method);
        method = MethodUtil.getMethod("doSomeThing1", new ClassA(), 10);
        assertNotNull(method);
    }
    
    @Test
    public void testInvokeMethodByNameCase() {
        MethodUtil.invokeMethod("doSomeThing0", new ClassB());
        int value = (int) MethodUtil.invokeMethod("doSomeThing1", new ClassA(), 10);
        assertEquals(10, value);
    }
    
    @Test
    public void testInvokeMethodCase() {
        Method method = MethodUtil.getMethod("doSomeThing1", new ClassA(), new Integer(10));
        assertNotNull(method);
        int value = (int) MethodUtil.invokeMethod(method, new ClassA(), 10);
        assertEquals(10, value);
    }
    
    @Test
    public void testCallStaticMethodCase() {
        MethodUtil.invokeMethod("printSomeThing", new ClassA());
        MethodUtil.invokeStaticMethod("printSomeThing", ClassA.class);
        Method method = MethodUtil.getMethod("printSomeThing1", ClassA.class, String.class);
        MethodUtil.invokeStaticMethod(method, "mobile phone");
    }
    
    @Test
    public void getConstructorValidCaseTest() {
        Constructor<?> con = MethodUtil.getConstructor(ClassC.class);
        assertNotNull(con);
        con = MethodUtil.getConstructor(ClassC.class, String.class);
        assertNotNull(con);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void getConstructorInvalidCase() {
        MethodUtil.getConstructor(ClassC.class, Integer.class);
    }
    
    @Test
    public void invokeConstructorValidCaseTest() {
        Object object = MethodUtil.invokeConstructor(ClassC.class);
        assertNotNull(object);
        object = MethodUtil.invokeConstructor(ClassC.class, "Dung");
        assertNotNull(object);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void invokeConstructorInvalidCaseTest() {
        MethodUtil.invokeConstructor(ClassC.class, new Integer(1));
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeConstructorInvalidCase() {
        MethodUtil.invokeConstructor(ClassD.class);
    }
    
    @Test(expectedExceptions = {IllegalStateException.class})
    public void testInvokeMethodWithMethodInvalidCase3() {
        Method method = MethodBuilder.create()
                .clazz(ClassB.class)
                .method("doSomeThing2")
                .build();
        MethodUtil.invokeMethod(method, null, String.class);
    }
    
    @Override
    public Class<?> getTestClass() {
        return MethodUtil.class;
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
