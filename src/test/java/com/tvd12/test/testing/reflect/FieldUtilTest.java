package com.tvd12.test.testing.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.FieldUtil;

public class FieldUtilTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return FieldUtil.class;
    }

    @Test
    public void getFieldTest() {
        assert FieldUtil.getField(B.class, "a") != null;
        assert FieldUtil.getField(B.class, "abc") == null;
    }

    @Test
    public void getFieldValue1Test() {
        B cb = new B();
        List<String> b = FieldUtil.getFieldValue(cb, "b");
        assert b != null;

    }

    @Test
    public void getFieldValue2Test1() {
        B cb = new B();
        List<String> a = FieldUtil.getFieldValue(cb, "a", null);
        assert a == null;
        List<String> c = FieldUtil.getFieldValue(cb, "c", new ArrayList<>());
        assert c != null;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getFieldValue2Test2() {
        FieldUtil.getFieldValue(new B(), "abc");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getFieldValue2Test3() {
        FieldUtil.getFieldValue(new B(), "a", "");
    }

    @Test
    public void setFieldValueTest1() {
        B cb = new B();
        FieldUtil.setFieldValue(cb, "a", new ArrayList<String>());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setFieldValueTest2() {
        FieldUtil.setFieldValue(new B(), "abc", "");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void setFieldValueTest3() {
        FieldUtil.setFieldValue(new B(), "a", "");
    }

    @Test
    public void test4() {
        String key = "key";
        String fieldValue = FieldUtil.getStaticFieldValue(A1.class, "key", key);
        assert Objects.equals(fieldValue, key);
        assert key == FieldUtil.getStaticFieldValue(A1.class, "key");
        FieldUtil.setStaticFieldValue(A1.class, "key", key);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test5() {
        FieldUtil.getStaticFieldValue(A1.class, "no way");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void test6() {
        FieldUtil.getStaticFieldValue(A1.class, "version", 10);
        FieldUtil.getStaticFieldValue(A1.class, "version", 10);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test7() {
        FieldUtil.setStaticFieldValue(A1.class, "no way", "value");
    }

    @Test
    public void setStaticFinalValue() {
        // given
        // when
        FieldUtil.setStaticFieldValue(A1.class, "value", "newValue");

        // then
        assert FieldUtil.getStaticFieldValue(A1.class, "value").equals("newValue");
    }

    @Test
    public void setStaticFinalValueFailedDueToWrongValueType() {
        // given
        // when
        Throwable e = Asserts.assertThrows(() ->
            FieldUtil.setStaticFieldValue(A1.class, "value", 0L)
        );

        // then
        Asserts.assertEqualsType(e, IllegalStateException.class);
    }

    @Test
    public void test9() {
        assert FieldUtil.getStaticFieldValue(A1.class, "hello", "value")
            .equals("world");
        assert FieldUtil.getStaticFieldValue(A1.class, "see") == null;
    }

    @Test
    public void setFinalFieldValue() {
        // given
        A a = new A();
        List<String> newValue = RandomUtil.randomShortList(String.class);

        // when
        FieldUtil.setFieldValue(a, "b", newValue);

        // then
        Asserts.assertEquals(
            FieldUtil.getFieldValue(a, "b"),
            newValue
        );
    }

    @Test
    public void setFinalFieldValueFailedDueToWrongValueType() {
        // given
        A a = new A();
        Set<String> newValue = RandomUtil.randomShortSet(String.class);

        // when
        Throwable e = Asserts.assertThrows(() ->
            FieldUtil.setFieldValue(a, "b", newValue)
        );

        // then
        Asserts.assertEqualsType(e, IllegalStateException.class);
    }

    @SuppressWarnings("unused")
    public static class A1 {
        public static Object key;
        public static final String value = "";
        public static String version;
        public static String hello = "world";
        public static String see;
    }

    @SuppressWarnings("unused")
    public static class A {
        private List<String> a;
        private final List<String> b = new ArrayList<>();
        protected List<String> c;
        protected List<String> d;
        protected List<String> e = new ArrayList<>();

        public List<String> getA() {
            return a;
        }

        public List<String> getB() {
            return b;
        }

        private String get() {
            return "private method";
        }

        protected String get2() {
            return get();
        }
    }

    @SuppressWarnings("unused")
    public static class B extends A {
        public String str = "";
    }
}
