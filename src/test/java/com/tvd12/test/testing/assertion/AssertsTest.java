package com.tvd12.test.testing.assertion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.test.assertion.AssertThat;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class AssertsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return Asserts.class;
	}
	
	@Test
	public void assertThrowsOk() {
		Exception err = new Exception("just test");
		assert Asserts.assertThrows(() -> {throw err;}) == err;
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertThrowsFail() {
		Asserts.assertThrows(() -> {});
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertTrueFail() {
		Asserts.assertTrue(true);
		Asserts.assertTrue(false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertFalseFail() {
		Asserts.assertFalse(true);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsTypeFailDueToActualNull() {
		Asserts.assertEqualsType(null, getClass());
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsTypeFailDueToDifference() {
		Asserts.assertEqualsType(A.class, B.class);
	}
	
	@Test
	public void assertEqualsNumbersOk() {
		Asserts.assertEquals(null, null);
		Asserts.assertNotEquals(Integer.valueOf(1), Long.valueOf(1));
		Asserts.assertNotEquals(Integer.valueOf(1), Double.valueOf(1));
		Asserts.assertNotEquals(Integer.valueOf(1), Long.valueOf(2));
		Asserts.assertNotEquals(Integer.valueOf(1), Long.valueOf(2), false);
		Asserts.assertEquals(Integer.valueOf(1), Long.valueOf(1), false);
		Asserts.assertEquals(Integer.valueOf(1), Double.valueOf(1), false);
		Asserts.assertEquals(Integer.valueOf(1), Integer.valueOf(1));
		Asserts.assertEquals(new Double(1.0D), new Double(1.0D));
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertNotEqualNumberFail() {
		Asserts.assertNotEquals(1, 1);
	}
	
	@Test
	public void assertEqualsObjects() {
		Asserts.assertEquals(new C(1), new C(1));
		Asserts.assertEquals(new B("b", 1), new B("b", 1));
	}
	
	@Test
	public void assertEqualsArrays() {
		Asserts.assertEquals(new C[] {new C(1)}, new C[] {new C(1)});
		Asserts.assertEquals(new B[] {new B("b", 1)}, new B[] {new B("b", 1)});
	}
	
	@Test
	public void assertEqualsCollections() {
		Asserts.assertEquals(Arrays.asList(new C(1)), Arrays.asList(new C(1)));
		Asserts.assertEquals(Arrays.asList(new B("b", 1)), Arrays.asList(new B("b", 1)));
	}
	
	@Test
	public void assertEqualsMaps() {
		Map<Integer, C> cMap1 = new HashMap<>();
		cMap1.put(1, new C(1));
		Map<Integer, C> cMap2 = new HashMap<>();
		cMap2.put(1, new C(1));
		Asserts.assertEquals(cMap1, cMap2);
		Map<Integer, B> bMap1 = new HashMap<>();
		bMap1.put(1, new B("b", 1));
		Map<Integer, B> bMap2 = new HashMap<>();
		bMap2.put(1, new B("b", 1));
		Asserts.assertEquals(bMap1, bMap2);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedNotNull() {
		Asserts.assertEquals(1, null);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedNull() {
		Asserts.assertEquals(null, 1);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsNumber() {
		Asserts.assertEquals(1, "1", false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsNotNumber() {
		Asserts.assertEquals("1", 1, false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsArray() {
		Asserts.assertEquals(new int[0], "1", false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsNotArray() {
		Asserts.assertEquals("1", new int[0], false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToArrayLength() {
		Asserts.assertEquals(new int[0], new int[1], false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToArraysAreNotEquals1() {
		Asserts.assertEquals(new int[] {1, 2}, new int[] {3, 4}, false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToArraysAreNotEquals2() {
		Asserts.assertEquals(new C[] {new C(1)}, new C[] {new C(2)});
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsList() {
		Asserts.assertEquals(Arrays.asList(1), "1", false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsNotList() {
		Asserts.assertEquals("1", Arrays.asList(1), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToCollectionSize() {
		Asserts.assertEquals(Arrays.asList(1, 2), Arrays.asList(1), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToListsAreNotEquals1() {
		Asserts.assertEquals(Arrays.asList(1, 2), Arrays.asList(3, 4), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToListsAreNotEquals2() {
		Asserts.assertEquals(Arrays.asList(new B("a", 1)), Arrays.asList(new B("b", 2)), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsMap() {
		Asserts.assertEquals(new HashMap<>(), "1", false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToExpectedIsNotMap() {
		Asserts.assertEquals("1", new HashMap<>(), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsFailDueToMapSize() {
		Asserts.assertEquals(Collections.singletonMap(1, 2), new HashMap<>(), false);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsMapsFailDueToKyesAreNotEquals() {
		Map<Integer, C> cMap1 = new HashMap<>();
		cMap1.put(1, new C(1));
		Map<Integer, C> cMap2 = new HashMap<>();
		cMap2.put(2, new C(2));
		Asserts.assertEquals(cMap1, cMap2);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsObjectsFailDueToNotEquals() {
		Asserts.assertEquals(new C(1), new B("b", 1), false);
	}
	
	@Test
	public void assertEqualsObjectsRecursive() {
		Asserts.assertEquals(new R(new R()), new R(new R()));
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsObjectsFailDueToException() {
		Asserts.assertEquals(new A1(), new A1());
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsObjectsFailDueToExpectedItemNull() {
		Asserts.assertEquals(Arrays.asList("a", null, "c"), Arrays.asList("a", "b", "c"));
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void assertEqualsObjectsFailDueToActualItemNull() {
		Asserts.assertEquals(Arrays.asList("a", "b", "c"), Arrays.asList("a", null, "c"));
	}
	
	@Test
	public void assertZeroOk() {
	    Asserts.assertZero((byte)0);
	    Asserts.assertZero(0.0D);
	    Asserts.assertZero(0.0F);
	    Asserts.assertZero(0);
	    Asserts.assertZero(0L);
	    Asserts.assertZero((short)0);
	    Asserts.assertZero(BigInteger.ZERO);
	    Asserts.assertZero(BigDecimal.ZERO);
	}
	
	@Test
	public void assertThatThrowsTest() {
	    // given
	    Exception e = new Exception("just test");
	    
	    // when
	    AssertThat<Throwable> assertThatThrows = Asserts.assertThatThrows(() -> {
	        throw e;
	    });
	    
	    // then
	    assertThatThrows.isEqualsTo(e);
	}
	
	public static class A1 {
		public A2 a2 = new A2();
	}
	
	public static class A2 {
		@Override
		public boolean equals(Object obj) {
			throw new RuntimeException("just test");
		}
	}
	
	public static class R {
		private R r = r2;
		public R r3 = r2;
		private static R r2 = new R();
		
		public R() {}
		
		public R(R r) {
			this.r = r;
		}
		
		public R getR() {
			return r;
		}
	}
	
	public static class C {
		private int id;
		
		public C(int id) {
			this.id = id;
		}
		
		@Override
		public boolean equals(Object obj) {
			return id == ((C)obj).id;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
	}
	
	public static class B extends A {
		private int value;
		public B(String name, int value) {
			super(name);
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public static class A {
		private String name;
		
		public A(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
}
