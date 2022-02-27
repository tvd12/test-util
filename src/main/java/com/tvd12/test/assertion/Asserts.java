package com.tvd12.test.assertion;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * Assertion for test
 * 
 * @author tvd12
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class Asserts {
    
    private Asserts() {}

    public static boolean assertNull(Object actual) {
        if (actual != null) {
            throw new AssertionError("expected: null but was: " + actual);
        }
        return true;
    }

    public static boolean assertNotNull(Object actual) {
        if (actual == null) {
            throw new AssertionError("expected: not null but was: null");
        }
        return true;
    }

    public static <T> AssertThat<T> assertThat(T actual) {
        return new AssertThat<>(actual);
    }
    
    public static <T> AssertThat<T> assertThat(AssertSupplier<T> actualSuppler) {
        return new AssertThat<>(actualSuppler);
    }

    public static <T> AssertThat<T> assertThatFrom(AssertSupplier<T> actualSuppler) {
        return assertThat(actualSuppler);
    }

    public static AssertThat<Throwable> assertThatThrows(AssertApplier actualSuppler) {
        try {
            actualSuppler.apply();
        } catch (Throwable e) {
            return new AssertThat<>(e);
        }
        throw new AssertionError("there is no exception");
    }

    public static Throwable assertThrows(AssertApplier func) {
        try {
            func.apply();
        } catch (Throwable e) {
            return e;
        }
        throw new AssertionError("there is no exception");
    }

    public static boolean assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("expected: true but was: false");
        }
        return true;
    }

    public static boolean assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("expected: false but was: true");
        }
        return true;
    }

    public static boolean assertZero(Number value) {
        return assertEquals(value.longValue(), 0L);
    }

    public static boolean assertZero(BigInteger value) {
        return assertEquals(value, BigInteger.ZERO);
    }

    public static boolean assertZero(BigDecimal value) {
        return assertEquals(value, BigDecimal.ZERO);
    }

    public static boolean assertEmpty(Iterable iterable) {
        int size = 0;
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            ++size;
            iterator.next();
        }
        if (size == 0) {
            return true;
        }
        throw new AssertionError("expected: empty but was: " + size);
    }

    public static boolean assertEmpty(Map map) {
        if (map.isEmpty()) {
            return true;
        }
        throw new AssertionError("expected: empty but was: " + map.size());
    }

    public static boolean assertNotEquals(Object actual, Object expected) {
        return assertNotEquals(actual, expected, true);
    }

    public static boolean assertNotEquals(
        Object actual, 
        Object expected, 
        boolean mustEqualsType
    ) {
        try {
            assertEquals(actual, expected, mustEqualsType);
        } catch (AssertionError e) {
            return true;
        }
        throw new AssertionError(
            "\nexpected:\n" + toString(expected) + "\nis not equal to:\n" + toString(actual) +
            "\nbut equal"
        );
    }

    public static boolean assertEqualsType(Object actual, Class<?> expectedType) {
        if (actual == null) {
            throw new AssertionError("\nexpected:\n" + expectedType.getName() + "\nbut was: null");
        }
        if (actual.getClass() != expectedType) {
            throw new AssertionError(
                "\nexpected:\n" + expectedType.getName() + "\nbut was:\n" + actual.getClass().getName()
            );
        }
        return true;
    }

    public static boolean assertEquals(Object actual, Object expected) {
        return assertEquals(actual, expected, true);
    }

    @SuppressWarnings("MethodLength")
    public static boolean assertEquals(
        Object actual, 
        Object expected,
        boolean mustEqualsType
    ) {
        if (expected == actual) {
            return true;
        }
        if (expected != null) {
            if (actual == null) {
                throw new AssertionError("expected: " + toString(expected) + " but was: " + toString(actual));
            }
        } else {
            throw new AssertionError("expected: " + toString(expected) + " but was: " + toString(actual));
        }

        Class<?> expectedType = expected.getClass();
        Class<?> actualType = actual.getClass();
        if (mustEqualsType) {
            if (!expectedType.equals(actualType)) {
                throw new AssertionError(
                    "\nexpected:\n" + toString(expected) + "\nbut was:\n" + toString(actual)
                );
            }
        }
        if (expected.equals(actual)) {
            return true;
        }

        // number
        if (Number.class.isAssignableFrom(expectedType) 
                && Number.class.isAssignableFrom(actualType)) {
            Number expectedNumber = (Number) expected;
            Number actualNumber = (Number) actual;
            if (expectedNumber.doubleValue() != actualNumber.doubleValue()) {
                throw new AssertionError("expected: " + toString(expected) + " but was: " + toString(actual));
            }
            return true;
        }
        if (Number.class.isAssignableFrom(expectedType)) {
            throw new AssertionError(
                "expected (number): " + toString(expected) + " but was (not number): " + toString(actual)
            );
        } else if (Number.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not number): " + toString(expected) + " but was (number): " + toString(actual)
            );
        }

        if (expectedType.isArray() && actualType.isArray()) {
            return assertEqualsArrays(actual, expected, mustEqualsType);
        }
        if (expectedType.isArray()) {
            throw new AssertionError(
                "expected (array):\n" + toString(expected) + "\nbut was (not array): " + toString(actual)
            );
        } else if (actualType.isArray()) {
            throw new AssertionError(
                "expected (not array):\n" + toString(expected) + "\nbut was (array):\n" + toString(actual)
            );
        }

        // collection
        if (Collection.class.isAssignableFrom(expectedType) 
                && Collection.class.isAssignableFrom(actualType)) {
            return assertEqualsCollections(new ArrayList<>(
                    (Collection) actual), 
                    new ArrayList<>((Collection) expected),
                    mustEqualsType
            );
        }
        if (Collection.class.isAssignableFrom(expectedType)) {
            throw new AssertionError(
                "expected (collection):\n" + toString(expected) + "\nbut was (not collection):\n" + toString(actual)
            );
        } else if (Collection.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not collection):\n" + toString(expected) + "\nbut was (collection):\n" + toString(actual)
            );
        }

        // map
        if (Map.class.isAssignableFrom(expectedType) && Map.class.isAssignableFrom(actualType)) {
            return assertEqualsMaps((Map) actual, (Map) expected, mustEqualsType);
        }
        if (Map.class.isAssignableFrom(expectedType)) {
            throw new AssertionError(
                "expected (map):\n" + toString(expected) + "\nbut was (not map):\n" + toString(actual)
            );
        } else if (Map.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not map):\n" + toString(expected) + "\nbut was (map):\n" + toString(actual)
            );
        }
        if (containsEqualsMethod(expectedType) || containsEqualsMethod(actualType)) {
            throw new AssertionError("expected:\n" + toString(expected) + "\nbut was:\n" + toString(actual));
        }
        try {
            return assertEqualsObjects(actual, expected, mustEqualsType);
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw new AssertionError(
                "expected (" + expectedType.getSimpleName() + "): " + expected + 
                " but was (" + actualType.getSimpleName() + "): " + actual,
                e
            );
        }
    }

    private static boolean assertEqualsArrays(
        Object actual, 
        Object expected, 
        boolean mustEqualsType
    ) {
        int expectedLength = Array.getLength(expected);
        int actualLength = Array.getLength(actual);
        if (expectedLength != actualLength) {
            throw new AssertionError(
                "\nexpected (size = " + expectedLength + "):\n" + arrayToString(expected) +
                "\nbut was: (size = " + actualLength + "):\n" + arrayToString(actual)
            );
        }
        for (int i = 0; i < expectedLength; ++i) {
            Object expectedItem = Array.get(expected, i);
            Object actualItem = Array.get(actual, i);
            try {
                assertEquals(actualItem, expectedItem, mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected: " + toString(expectedItem) + " but was: " + toString(actualItem) + 
                    "\nexpect:\n" + arrayToString(expected) +
                    "\nactual:\n" + arrayToString(actual),
                    e
                );
            }
        }
        return true;
    }

    private static boolean assertEqualsCollections(
        List actual, 
        List expected, 
        boolean mustEqualsType
    ) {
        int expectedLength = expected.size();
        int actualLength = actual.size();
        if (expectedLength != actualLength) {
            throw new AssertionError(
                "\nexpected (size = " + expectedLength + "):\n" + toString(expected) +
                "\nbut was: (size = " + actualLength + "):\n" + toString(actual)
            );
        }
        for (int i = 0; i < expectedLength; ++i) {
            Object expectedItem = expected.get(i);
            Object actualItem = actual.get(i);
            try {
                assertEquals(actualItem, expectedItem, mustEqualsType);
            } catch (AssertionError e) {
                StringBuilder builder = new StringBuilder()
                    .append("expected: " + toString(expectedItem))
                    .append(" but was: " + toString(actualItem));
                throw new AssertionError(
                    builder + "\nexpect:\n" + toString(expected) + 
                    "\nactual:\n" + toString(actual), 
                    e
                );
            }
        }
        return true;
    }

    private static boolean assertEqualsMaps(
        Map actual, 
        Map expected,
        boolean mustEqualsType
    ) {
        return assertEqualsMaps(
            actual.getClass(),
            expected.getClass(),
            actual,
            expected,
            mustEqualsType,
            "key"
        );
    }

    private static boolean assertEqualsMaps(
        Class<?> actualType,
        Class<?> expectedType,
        Map actual, 
        Map expected, 
        boolean mustEqualsType, 
        String keyType
    ) {
        int expectedLength = expected.size();
        int actualLength = actual.size();
        if (expectedLength != actualLength) {
            throw new AssertionError(
                "\nexpected (" + "size = " + expectedLength +  "):\n" + toString(expectedType, expected) +
                "\nbut was (" + "size = " + actualLength + "):\n" + toString(actualType, actual)
            );
        }
        List<Entry> expectedEntries = new ArrayList<>(expected.entrySet());
        List<Entry> actualEntries = new ArrayList<>(actual.entrySet());
        for (int i = 0; i < expectedLength; ++i) {
            Entry expectedEntry = expectedEntries.get(i);
            Entry actualEntry = actualEntries.get(i);
            try {
                assertEquals(actualEntry.getKey(), expectedEntry.getKey(), mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected " + keyType + ": " + toString(expectedEntry.getKey()) + 
                    " but was: " + toString(actualEntry.getKey()) + 
                    "\nexpected:\n" + toString(expectedType, expected) + "\nactual:\n" + toString(actualType, actual),
                    e
                );
            }
            try {
                assertEquals(actualEntry.getValue(), expectedEntry.getValue(), mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected value: " + toString(expectedEntry.getValue()) + 
                    " but was: " + toString(actualEntry.getValue()) + 
                    "\nexpected:\n" + toString(expectedType, expected) + "\nactual:\n" + toString(actualType, actual),
                    e
                );
            }
        }
        return true;
    }

    private static boolean assertEqualsObjects(
        Object actual, 
        Object expected,
        boolean mustEqualsType
    ) throws Exception {
        Map expectedMap = new HashMap<>();
        Map actualMap = new HashMap<>();
        Class<?> expectedType = expected.getClass();
        Class<?> actualType = actual.getClass();
        while (expectedType != Object.class) {
            for (Field field : expectedType.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    expectedMap.put(field.getName(), field.get(expected));
                }
            }
            expectedType = expectedType.getSuperclass();
        }
        while (actualType != Object.class) {
            for (Field field : actualType.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    actualMap.put(field.getName(), field.get(actual));
                }
            }
            actualType = actualType.getSuperclass();
        }
        return assertEqualsMaps(
            actual.getClass(),
            expected.getClass(),
            actualMap,
            expectedMap,
            mustEqualsType,
            "field"
        );
    }

    private static String arrayToString(Object array) {
        int length = Array.getLength(array);
        StringBuilder builder = new StringBuilder()
            .append(array.getClass().getTypeName())
            .append("{");
        for (int i = 0; i < length; ++i) {
            builder.append(toString(Array.get(array, i)));
            if (i < length - 1) {
                builder.append(", ");
            }
        }
        return builder.append("}").toString();
    }

    private static boolean containsEqualsMethod(Class<?> clazz) {
        try {
            clazz.getDeclaredMethod("equals", Object.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    private static String toString(Object value) {
        if (value == null) {
            return "null";
        }
        return toString(value.getClass(), value);
    }
    
    private static String toString(Class<?> type, Object value) {
        String str = String.valueOf(value);
        if (str.contains(type.getSimpleName())) {
            return str;
        }
        return type.getSimpleName() + "(" + str + ")";
    }
}
