package com.tvd12.test.testing.assertion;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.test.assertion.Asserts;

public class AssertsMessageTest {
    
    @Test
    public void testNull() {
        try {
            Asserts.assertNull("abc");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNotNull() {
        try {
            Asserts.assertNotNull(null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNotNumber1() {
        try {
            Asserts.assertEquals(1, "2", false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNotNumber2() {
        try {
            Asserts.assertEquals("2", 1, false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testNotEquals() {
        try {
            Asserts.assertNotEquals(new A("a", 1), new A("a", 1));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testEqualsType() {
        try {
            Asserts.assertEqualsType(new A("a", 1), B.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testEqualsTypeButNull() {
        try {
            Asserts.assertEqualsType(null, B.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testForMapNotSameSize() {
        Map<String, Object> m1 = new HashMap<>();
        m1.put("hello", "world");
        Map<String, Object> m2 = new HashMap<>();
        m2.put("a", "1");
        m2.put("b", "2");
        try {
            Asserts.assertEquals(m1, m2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testForMapNotSameKey() {
        Map<String, Object> m1 = new HashMap<>();
        m1.put("hello", "world");
        Map<String, Object> m2 = new HashMap<>();
        m2.put("a", "1");
        try {
            Asserts.assertEquals(m1, m2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testForMapNotSameValue() {
        Map<String, Object> m1 = new HashMap<>();
        m1.put("hello", "world");
        Map<String, Object> m2 = new HashMap<>();
        m2.put("hello", "1");
        try {
            Asserts.assertEquals(m1, m2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testForMapNotSameValueType() {
        Map<String, Object> m1 = new HashMap<>();
        m1.put("hello", "world");
        Map<String, Object> m2 = new HashMap<>();
        m2.put("hello", Arrays.asList(1, 2, 3));
        try {
            Asserts.assertEquals(m1, m2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameTypeTest() {
        try {
            Asserts.assertEquals(1, "2");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameTypeObjectTest() {
        try {
            Asserts.assertEquals(new A(), new B());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameValueTest() {
        A a = new A("hello", 1);
        A b = new A("hello", 2);
        try {
            Asserts.assertEquals(a, b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameValueComplexTest() {
        B a = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        B b = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 2))
        );
        try {
            Asserts.assertEquals(a, b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameValueComplexListTest() {
        B a = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        B b = new B(
            "hello", 
            1,
            new A("2", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        try {
            Asserts.assertEquals(a, b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameValueComplexNullTest() {
        B a = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        B b = new B(
            "hello", 
            1,
            null,
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        try {
            Asserts.assertEquals(a, b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameValueComplexNumberTest() {
        B a = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap("m", new A("3", 3))
        );
        B b = new B(
            "hello", 
            1,
            new A("1", 1),
            Arrays.asList(new A("2", 2)),
            Collections.singletonMap(2, new A("3", 3))
        );
        try {
            Asserts.assertEquals(a, b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameListSize() {
        try {
            Asserts.assertEquals(
                Arrays.asList(1, 2),
                Arrays.asList(1)
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameListValue() {
        try {
            Asserts.assertEquals(
                Arrays.asList(1, 2),
                Arrays.asList(1, 3)
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameListValueObject() {
        try {
            Asserts.assertEquals(
                Arrays.asList(new A("1", 1), new A("2", 2)),
                Arrays.asList(new A("1", 1), new A("2", 3))
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameListKeyObject() {
        try {
            Asserts.assertEquals(
                Arrays.asList(new A("1", 1), new A("2", 2)),
                Arrays.asList(new A("1", 1), new A("1", 2))
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameArraySize() {
        try {
            Asserts.assertEquals(
                new int[] {1, 2},
                new int[] {1}
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameArrayValue() {
        try {
            Asserts.assertEquals(
                new int[] {1, 2},
                new int[] {1, 3}
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void notSameArrayValueObject() {
        try {
            Asserts.assertEquals(
                new A[] {new A("1", 1), new A("2", 2)},
                new A[] {new A("1", 1), new A("2", 3)}
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public static class A {
        public String name;
        public int value;
        
        public A() {}
        
        public A(String name, int value) {
            super();
            this.name = name;
            this.value = value;
        }
    }
    
    public static class B {
        public String foo;
        public int bar;
        public A a;
        public List<A> aList;
        public Map<Object, A> aMap;
        
        public B() {}
        
        public B(String foo, int bar, A a, List<A> aList, Map<Object, A> aMap) {
            super();
            this.foo = foo;
            this.bar = bar;
            this.a = a;
            this.aList = aList;
            this.aMap = aMap;
        }
    }
}
