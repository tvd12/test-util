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
        return new AssertThat<>(() -> {
            actualSuppler.apply();
            return null;
        });
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
        throw new AssertionError(expected + " is equal to: " + actual);
    }

    public static boolean assertEqualsType(Object actual, Class<?> expectedType) {
        if (actual == null) {
            throw new AssertionError("expected: " + expectedType.getName() + " but was: null");
        }
        if (actual.getClass() != expectedType) {
            throw new AssertionError(
                "expected: " + expectedType.getName() + " but was: " + actual.getClass().getName()
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
                throw new AssertionError("expected: " + expected + " but was: " + actual);
            }
        } else {
            throw new AssertionError("expected: " + expected + " but was: " + actual);
        }

        Class<?> expectedType = expected.getClass();
        Class<?> actualType = actual.getClass();
        if (mustEqualsType) {
            if (!expectedType.equals(actualType)) {
                throw new AssertionError(
                    "expected type: " + expectedType.getName() + " but was: " + actualType.getName()
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
                throw new AssertionError("expected: " + expected + " but was: " + actual);
            }
            return true;
        }
        if (Number.class.isAssignableFrom(expectedType)) {
            throw new AssertionError(
                "expected (number): " + expected + " but was (not number): " + actual
            );
        } else if (Number.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not number): " + expected + " but was (number): " + actual
            );
        }

        if (expectedType.isArray() && actualType.isArray()) {
            return assertEqualsArrays(actual, expected, mustEqualsType);
        }
        if (expectedType.isArray()) {
            throw new AssertionError(
                "expected (array): " + expected + " but was (not array): " + actual
            );
        } else if (actualType.isArray()) {
            throw new AssertionError(
                "expected (not array): " + expected + " but was (array): " + actual
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
                "expected (collection): " + expected + " but was (not collection): " + actual
            );
        } else if (Collection.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not collection): " + expected + " but was (collection): " + actual
            );
        }

        // map
        if (Map.class.isAssignableFrom(expectedType) && Map.class.isAssignableFrom(actualType)) {
            return assertEqualsMaps((Map) actual, (Map) expected, mustEqualsType);
        }
        if (Map.class.isAssignableFrom(expectedType)) {
            throw new AssertionError(
                "expected (map): " + expected + " but was (not map): " + actual
            );
        } else if (Map.class.isAssignableFrom(actualType)) {
            throw new AssertionError(
                "expected (not map): " + expected + " but was (map): " + actual
            );
        }
        if (containsEqualsMethod(expectedType) || containsEqualsMethod(actualType)) {
            throw new AssertionError("expected: " + expected + " but was: " + actual);
        }
        try {
            return assertEqualsObjects(actual, expected, mustEqualsType);
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw new AssertionError(
                "expected (" + expectedType.getSimpleName() + "): " 
                    + expected + " but was ("
                    + actualType.getSimpleName() + "): " + actual);
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
                "expected array.length: " + expectedLength + " but was: " + actualLength
            );
        }
        for (int i = 0; i < expectedLength; ++i) {
            Object expectedItem = Array.get(expected, i);
            Object actualItem = Array.get(actual, i);
            try {
                assertEquals(actualItem, expectedItem, mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected: " + expectedItem + " but was: " + actualItem + "\nexpect: "
                        + arrayToString(expected) + "\nactual: " + arrayToString(actual));
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
                "expected collection.size: " + expectedLength + " but was: " + actualLength
            );
        }
        for (int i = 0; i < expectedLength; ++i) {
            Object expectedItem = expected.get(i);
            Object actualItem = actual.get(i);
            try {
                assertEquals(actualItem, expectedItem, mustEqualsType);
            } catch (AssertionError e) {
                StringBuilder builder = new StringBuilder().append("expected: " + expectedItem);
                if (expectedItem != null) {
                    builder.append(" (" + expectedItem.getClass().getSimpleName() + ")");
                }
                builder.append(" but was: " + actualItem);
                if (actualItem != null) {
                    builder.append(" (" + actual.getClass().getSimpleName() + ")");
                }
                throw new AssertionError(builder + "\nexpect: " + expected + "\nactual: " + actual);
            }
        }
        return true;
    }

    private static boolean assertEqualsMaps(
        Map actual, 
        Map expected,
        boolean mustEqualsType
    ) {
        return assertEqualsMaps(actual, expected, mustEqualsType, "map", "key");
    }

    private static boolean assertEqualsMaps(
        Map actual, 
        Map expected, 
        boolean mustEqualsType, 
        String mapType,
        String keyType
    ) {
        int expectedLength = expected.size();
        int actualLength = actual.size();
        if (expectedLength != actualLength) {
            throw new AssertionError(
                "expected " + mapType + ".size: " + expectedLength + " but was: " + actualLength
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
                    "expected " + keyType + ": " + expectedEntry.getKey() + " but was: "
                        + actualEntry.getKey() + "\nexpect: " + expected + "\nactual: " + actual);
            }
            try {
                assertEquals(actualEntry.getValue(), expectedEntry.getValue(), mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected value: " + expectedEntry.getValue() + " but was: "
                        + actualEntry.getValue() + "\nexpect: " + expected + "\nactual: " + actual
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
        return assertEqualsMaps(actualMap, expectedMap, mustEqualsType, "object", "field");
    }

    private static String arrayToString(Object array) {
        int length = Array.getLength(array);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(Array.get(array, i));
            if (i < length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private static boolean containsEqualsMethod(Class<?> clazz) {
        try {
            clazz.getDeclaredMethod("equals", Object.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
