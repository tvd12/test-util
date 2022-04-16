package com.tvd12.test.testing.util;

import org.testng.annotations.Test;

import com.tvd12.test.base.BaseTest;
import com.tvd12.test.util.Pair;

public class PairTest extends BaseTest {

	@Test
	public void test() {
		Pair<String, String> pair = new Pair<String, String>("1", "a");
		assert pair.setValue("b").equals("a");
	}
}
