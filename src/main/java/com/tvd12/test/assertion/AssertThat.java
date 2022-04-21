package com.tvd12.test.assertion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Assert actual value
 *
 * @param <T> the value type
 * @author tvd12
 */
public class AssertThat<T> {

    private T actual;
    private AssertSupplier<T> actualSupplier;

    public AssertThat(T actual) {
        this.actual = actual;
    }

    public AssertThat(AssertSupplier<T> actualSupplier) {
        this.actualSupplier = actualSupplier;
    }

    public void isNull() {
        try {
            actual = getActualValue(true);
        } catch (Throwable e) {
            throw new AssertionError("expected: null but was exception: " + e);
        }
        Asserts.assertNull(actual);
    }

    public void isNotNull() {
        try {
            actual = getActualValue(true);
        } catch (Throwable e) {
            throw new AssertionError("expected: not null but was exception: " + e);
        }
        Asserts.assertNotNull(actual);
    }

    public void isTrue() {
        Boolean booleanValue;
        try {
            actual = getActualValue();
            booleanValue = (Boolean) actual;
        } catch (Throwable e) {
            throw new AssertionError("expected: not null but was exception: " + e);
        }
        Asserts.assertTrue(booleanValue);
    }

    public void isFalse() {
        Boolean booleanValue;
        try {
            actual = getActualValue();
            booleanValue = (Boolean) actual;
        } catch (Throwable e) {
            throw new AssertionError("expected: true but was exception: " + e);
        }
        Asserts.assertFalse(booleanValue);
    }

    public void isZero() {
        try {
            actual = getActualValue();
        } catch (Throwable e) {
            throw new AssertionError("expected: false but was exception: " + e);
        }
        if (actual instanceof BigInteger) {
            Asserts.assertZero((BigInteger) actual);
            return;
        }
        if (actual instanceof BigDecimal) {
            Asserts.assertZero((BigDecimal) actual);
            return;
        }
        if (actual instanceof Number) {
            Asserts.assertZero((Number) actual);
            return;
        }
        throw new AssertionError("expected 0 or ZERO but was: " + actual);
    }

    @SuppressWarnings("rawtypes")
    public void isEmpty() {
        try {
            actual = getActualValue();
        } catch (Throwable e) {
            throw new AssertionError("expected: false but was exception: " + e);
        }
        if (actual instanceof Iterable) {
            Asserts.assertEmpty((Iterable) actual);
            return;
        }
        if (actual instanceof Map) {
            Asserts.assertEmpty((Map) actual);
            return;
        }
        throw new AssertionError("expected empty (iterable or map) but was: " + actual);
    }

    public void test(Predicate<T> predicate) {
        try {
            actual = getActualValue();
        } catch (Throwable e) {
            throw new AssertionError("test fails due to exception: " + e);
        }
        if (!predicate.test(actual)) {
            throw new AssertionError("test fails");
        }
    }

    public void isEqualsType(Class<?> expectedType) {
        try {
            actual = getActualValue();
        } catch (Throwable e) {
            throw new AssertionError(
                "expected: " + expectedType.getName() + " but was exception: " + e
            );
        }
        Asserts.assertEqualsType(actual, expectedType);
    }

    public void isEqualsTo(Object expected) {
        isEqualsTo(expected, true);
    }

    @SuppressWarnings("unchecked")
    public void isEqualsTo(Object expected, boolean mustEqualsType) {
        try {
            actual = getActualValue();
        } catch (Throwable e) {
            if (expected instanceof Exception) {
                actual = (T) e;
            } else {
                throw new AssertionError("expected: " + expected + " but was exception: " + e);
            }
        }
        Asserts.assertEquals(expected, actual, mustEqualsType);
    }

    public void isEqualsTo(AssertSupplier<T> expectedSupplier) {
        Object expected;
        try {
            expected = expectedSupplier.apply();
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
        if (actualSupplier != null) {
            try {
                actual = getActualValue();
            } catch (Throwable e) {
                throw new AssertionError("expected: " + expected + " but was exception: " + e);
            }
        }
        Asserts.assertEquals(expected, actual);
    }

    public void willThrows(Class<?> expectedExceptionType) {
        acceptException(e -> {
            if (e.getClass() != expectedExceptionType) {
                throw new AssertionError(
                    "expected throws: " + expectedExceptionType.getName()
                        + " but was: " + e.getClass().getName());
            }
        });
    }

    public void acceptException(Consumer<Throwable> exceptionConsumer) {
        testException(it -> {
            exceptionConsumer.accept(it);
            return true;
        });
    }

    public void testException(Predicate<Throwable> exceptionPredicate) {
        if (actualSupplier == null) {
            throw new AssertionError("there is no Exception to throw");
        }
        try {
            actualSupplier.apply();
            throw new AssertionError("there is no Exception to throw");
        } catch (Throwable e) {
            if (!exceptionPredicate.test(e)) {
                throw new AssertionError("fails when test exception: " + e);
            }
        }
    }

    private T getActualValue() throws Throwable {
        return getActualValue(false);
    }

    @SuppressWarnings("unchecked")
    private T getActualValue(boolean nullable) throws Throwable {
        T answer = actualSupplier != null ? actualSupplier.apply() : actual;
        if (answer == null && !nullable) {
            throw new AssertionError("expected: a not null value but was: null");
        }
        if (answer instanceof Future) {
            answer = ((Future<T>) answer).get();
        }
        return answer;
    }
}
