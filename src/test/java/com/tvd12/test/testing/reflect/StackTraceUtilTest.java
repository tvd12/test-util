package com.tvd12.test.testing.reflect;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.StackTraceUtil;

public class StackTraceUtilTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return StackTraceUtil.class;
	}
	
	@Test
	public void test() {
		assertEquals(StackTraceUtil.getCallerInfo(new StackTraceElement[0], 4), "[Unknown]");
	}
}
