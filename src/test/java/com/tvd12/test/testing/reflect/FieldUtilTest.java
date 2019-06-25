package com.tvd12.test.testing.reflect;

import java.util.ArrayList;
import java.util.List;

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
		List<String> c = FieldUtil.getFieldValue(cb, "c", new ArrayList<String>());
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
	
	public static class A {
		private List<String> a;
		private List<String> b = new ArrayList<>();
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
	
	public static class B extends A {
		
		public String str = "";
		
	}
	
}
