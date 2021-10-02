package com.tvd12.test.assertion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AssertThat<T> {

	private T actual;
	private AssertSupplier<T> actualSupplier;
	
	public AssertThat(T actual) {
		this.actual = actual;
	}
	
	public AssertThat(AssertSupplier<T> actualSupplier) {
		this.actualSupplier = actualSupplier;
	}
	
	public boolean isNull() {
		try {
			actual = getActualValue();
		}
		catch (Throwable e) {
			throw new AssertionError("expected: null but was exception: " + e);
		}
		return Asserts.assertNull(actual);
	}
	
	public boolean isNotNull() {
		try {
			actual = getActualValue();
		}
		catch (Throwable e) {
			throw new AssertionError("expected: not null but was exception: " + e);
		}
		return Asserts.assertNotNull(actual);
	}
	
	public boolean isTrue() {
	    Boolean booleanValue;
	    try {
            actual = getActualValue();
            booleanValue = (Boolean)actual;
        }
        catch (Throwable e) {
            throw new AssertionError("expected: not null but was exception: " + e);
        }
        return Asserts.assertTrue(booleanValue);
	}
	
	public boolean isFalse() {
        Boolean booleanValue;
        try {
            actual = getActualValue();
            booleanValue = (Boolean)actual;
        }
        catch (Throwable e) {
            throw new AssertionError("expected: true but was exception: " + e);
        }
        return Asserts.assertFalse(booleanValue);
    }
	
	public boolean isZero() {
        try {
            actual = getActualValue();
        }
        catch (Throwable e) {
            throw new AssertionError("expected: false but was exception: " + e);
        }
        if(actual instanceof BigInteger)
            return Asserts.assertZero((BigInteger)actual);
        if(actual instanceof BigDecimal)
            return Asserts.assertZero((BigDecimal)actual);
        if(actual instanceof Number)
            return Asserts.assertZero((Number)actual);
        throw new AssertionError("expected 0 or ZERO but was: " + actual);
    }
	
	public boolean test(Predicate<T> predicate) {
		try {
			actual = getActualValue();
		}
		catch (Throwable e) {
			throw new AssertionError("test fails due to exception: " + e);
		}
		if(!predicate.test(actual))
			throw new AssertionError("test fails");
		return true;
	}
	
	public boolean isEqualsType(Class<?> expectedType) {
		try {
			actual = getActualValue();
		}
		catch (Throwable e) {
			throw new AssertionError("expected: " + expectedType.getName() + " but was exception: " + e);
		}
		return Asserts.assertEqualsType(expectedType, actual);
	}
	
	public boolean isEqualsTo(Object expected) {
		return isEqualsTo(expected, true);
	}
	
	@SuppressWarnings("unchecked")
    public boolean isEqualsTo(Object expected, boolean mustEqualsType) {
		try {
			actual = getActualValue();
		}
		catch (Throwable e) {
		    if (expected instanceof Exception) {
		        actual = (T) e;
		    }
		    else {
		        throw new AssertionError("expected: " + expected + " but was exception: " + e);
		    }
		}
		return Asserts.assertEquals(expected, actual, mustEqualsType);
	}
	
	public boolean isEqualsTo(AssertSupplier<T> expectedSupplier) {
		Object expected = null;
		try {
			expected = expectedSupplier.apply();
		}
		catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
		if(actualSupplier != null) {
			try {
				actual = getActualValue();
			}
			catch (Throwable e) {
				throw new AssertionError("expected: " + expected + " but was exception: " + e);
			}
		}
		return Asserts.assertEquals(expected, actual);
	}
	
	public boolean willThrows(Class<?> expectedExceptionType) {
		return acceptException(e -> {
			if(e.getClass() != expectedExceptionType)
				throw new AssertionError("expected throws: " + expectedExceptionType.getName() + " but was: " + e.getClass().getName());
		});
	}
	
	public boolean acceptException(Consumer<Throwable> exceptionConsumer) {
		return testException(t -> {
			exceptionConsumer.accept(t);
			return true;
		});
	}
	
	public boolean testException(Predicate<Throwable> exceptionPredicate) {
		if(actualSupplier == null)
			throw new AssertionError("there is no Exception to throw");
		try {
			actualSupplier.apply();
			throw new AssertionError("there is no Exception to throw");
		}
		catch (Throwable e) {
			if(!exceptionPredicate.test(e))
				throw new AssertionError("fails when test exception: " + e);
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	private T getActualValue() throws Throwable {
		T answer = actualSupplier != null ? actualSupplier.apply() : actual;
		if(answer instanceof Future)
			answer = ((Future<T>)answer).get();
		return answer;
	}
	
}
