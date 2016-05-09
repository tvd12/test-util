package com.tvd12.test.testing.reflect;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.MethodBuilder;

import static org.testng.Assert.*;

public class MethodBuilderTest extends BaseTest {
    
    @Test
    public void test() {
        Method method = MethodBuilder.create()
                .clazz(ClassA.class)
                .method("setName")
                .argument(String.class)
                .build();
        assertEquals(method.getName(), "setName");
    }
    
    public static class ClassA {
        public void setName(String name) {
            
        }
    }
}
