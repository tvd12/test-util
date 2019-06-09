package com.tvd12.test.testing.reflect;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.ClassUtil;

public class ClassUtilTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return ClassUtil.class;
	}
	
	@Test
	public void test() {
		assertEquals(ClassUtil.shortClassName("A"), "A");
		assertEquals(ClassUtil.shortClassName("hello.A"), "hello.A");
		assertEquals(ClassUtil.shortClassName("foo.bar.A"), "bar.A");
	}
	
}
