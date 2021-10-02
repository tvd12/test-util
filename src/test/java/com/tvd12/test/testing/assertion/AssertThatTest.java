package com.tvd12.test.testing.assertion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.testng.annotations.Test;

import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class AssertThatTest extends BaseTest {
	
	@Test
	public void isNullSuccess() {
		Asserts.assertThat(null).isNull();
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isNullFailDueToNotNull() {
		Asserts.assertThat(1).isNull();
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isNullFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		}).isNull();
	}
	
	@Test
	public void isNotNullSuccess() {
		Asserts.assertThat(1).isNotNull();
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isNotNullFailDueToNull() {
		Asserts.assertThat(null).isNotNull();
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isNotNullFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		}).isNotNull();
	}
	
	@Test
	public void testSuccessWithFuture() {
		CompletableFuture<Integer> future = new CompletableFuture<>();
		future.complete(1);
		Asserts.assertThat(future).isEqualsTo(1);
	}
	
	@Test
	public void testSuccessWithSupplier() {
		Asserts.assertThat(() -> 1).test(it -> it.equals(1));
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void testFailDueToNotAccept() {
		Asserts.assertThat(() -> {
			return 1;
		})
		.test(it -> it.equals(2));
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void testFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.test(it -> it.equals(1));
	}
	
	@Test
	public void isEqualsTypeSuccessWithSupplier() {
		Asserts.assertThat(() -> 1).isEqualsType(Integer.class);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isEqualsTypeFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsType(Integer.class);
	}
	
	@Test
	public void isEqualsSuccessWithSupplier() {
		Asserts.assertThat(() -> 1).isEqualsTo(1);
	}
	
	@Test
	public void isEqualsSuccessWithValue() {
		Asserts.assertThat(1).isEqualsTo(1);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isEqualsFailDueToNotEquals() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsTo(1);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isEqualsFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsTo(1);
	}
	
	@Test
	public void isEqualsSupplierSuccessWithSupplier() {
		Asserts.assertThat(() -> 1).isEqualsTo(() -> 1);
	}
	
	@Test
	public void isEqualsSupplierSuccessWithValue() {
		Asserts.assertThat(1).isEqualsTo(() -> 1);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isEqualsSupplierFailDueToNotEquals() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsTo(() -> 1);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void isEqualsSupplierFailDueToException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsTo(() -> 1);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualsSupplierFailDueToExpectedException() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.isEqualsTo(() -> {
			throw new Exception("just test");
		});
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void willThrowsExceptionTypeFail() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.willThrows(RuntimeException.class);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void willThrowsConsumerFail() {
		Asserts.assertThat(() -> {
			throw new Exception("just test");
		})
		.testException(it -> it.getClass() == RuntimeException.class);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void testExceptionFailDueToNoActualSupplier() {
		Asserts.assertThat(1)
		.testException(it -> it.getClass() == RuntimeException.class);
	}
	
	@Test(expectedExceptions = AssertionError.class)
	public void testExceptionFailDueToNoException() {
		Asserts.assertThat(() -> {
			return 1;
		})
		.testException(it -> it.getClass() == RuntimeException.class);
	}
	
	@Test
    public void isTrueWithValue() {
        Asserts.assertThat(true).isTrue();
    }
	
	@Test(expectedExceptions = AssertionError.class)
    public void isTrueButFalse() {
        Asserts.assertThat(false).isTrue();
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void isTrueFailDueToNotEquals() {
        Asserts.assertThat(() -> {
            throw new Exception("just test");
        })
        .isTrue();
    }
    
    @Test
    public void isFalseWithValue() {
        Asserts.assertThat(false).isFalse();
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void isFalseButTrue() {
        Asserts.assertThat(true).isFalse();
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void isFaseFailDueToNotEquals() {
        Asserts.assertThat(() -> {
            throw new Exception("just test");
        })
        .isFalse();
    }
    
    @Test
    public void assertZeroSuccess() {
        Asserts.assertThat((byte)0).isZero();
        Asserts.assertThat(0.0D).isZero();
        Asserts.assertThat(0.0F).isZero();
        Asserts.assertThat(0).isZero();
        Asserts.assertThat(0L).isZero();
        Asserts.assertThat((short)0).isZero();
        Asserts.assertThat(BigInteger.ZERO).isZero();
        Asserts.assertThat(BigDecimal.ZERO).isZero();
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void isFalseButString() {
        Asserts.assertThat("0").isZero();
    }
    
    @Test(expectedExceptions = AssertionError.class)
    public void assertZeroFailDueToNotEquals() {
        Asserts.assertThat(() -> {
            throw new Exception("just test");
        })
        .isZero();
    }
}
