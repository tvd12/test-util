package com.tvd12.test.assertion;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Assertion for test
 *
 * @author tvd12
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class Asserts {

    private Asserts() {}

    public static void assertNull(Object actual) {
        if (actual != null) {
            throw new AssertionError("expected: null but was: " + actual);
        }
    }

    public static void assertNotNull(Object actual) {
        if (actual == null) {
            throw new AssertionError("expected: not null but was: null");
        }
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

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("expected: true but was: false");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("expected: false but was: true");
        }
    }

    public static void assertZero(Number value) {
        assertEquals(value.longValue(), 0L);
    }

    public static void assertZero(BigInteger value) {
        assertEquals(value, BigInteger.ZERO);
    }

    public static void assertZero(BigDecimal value) {
        assertEquals(value, BigDecimal.ZERO);
    }

    public static void assertEmpty(Iterable iterable) {
        int size = 0;
        for (Object ignored : iterable) {
            ++size;
        }
        if (size == 0) {
            return;
        }
        throw new AssertionError("expected: empty but was: " + size);
    }

    public static void assertEmpty(Map map) {
        if (map.isEmpty()) {
            return;
        }
        throw new AssertionError("expected: empty but was: " + map.size());
    }

    public static void assertNotEquals(Object actual, Object expected) {
        assertNotEquals(actual, expected, true);
    }

    public static void assertNotEquals(
        Object actual,
        Object expected,
        boolean mustEqualsType
    ) {
        try {
            assertEquals(actual, expected, mustEqualsType);
        } catch (AssertionError e) {
            return;
        }
        throw new AssertionError(
            "\nexpected:\n" + toString(expected) + "\nis not equal to:\n" + toString(actual) +
                "\nbut equal"
        );
    }

    public static void assertEqualsType(Object actual, Class<?> expectedType) {
        if (actual == null) {
            throw new AssertionError("\nexpected:\n" + expectedType.getName() + "\nbut was: null");
        }
        if (actual.getClass() != expectedType) {
            throw new AssertionError(
                "\nexpected:\n" + expectedType.getName() + "\nbut was:\n" + actual.getClass().getName()
            );
        }
    }

    public static void assertEquals(Object actual, Object expected) {
        assertEquals(actual, expected, true);
    }

    @SuppressWarnings("MethodLength")
    public static void assertEquals(
        Object actual,
        Object expected,
        boolean mustEqualsType
    ) {
        if (expected == actual) {
            return;
        }
        if (expected != null) {
            if (actual == null) {
                throw new AssertionError("expected: " + toString(expected) + " but was: null");
            }
        } else {
            throw new AssertionError("expected: null but was: " + toString(actual));
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
            return;
        }

        // number
        if (Number.class.isAssignableFrom(expectedType)
            && Number.class.isAssignableFrom(actualType)) {
            Number expectedNumber = (Number) expected;
            Number actualNumber = (Number) actual;
            if (expectedNumber.doubleValue() != actualNumber.doubleValue()) {
                throw new AssertionError("expected: " + toString(expected) + " but was: " + toString(actual));
            }
            return;
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
            assertEqualsArrays(actual, expected, mustEqualsType);
            return;
        }
        if (expectedType.isArray()) {
            throw new AssertionError(
                "expected (array):\n" + toString(expected) + "\nbut was (not array):\n" + toString(actual)
            );
        } else if (actualType.isArray()) {
            throw new AssertionError(
                "expected (not array):\n" + toString(expected) + "\nbut was (array):\n" + toString(actual)
            );
        }

        // collection
        if (Collection.class.isAssignableFrom(expectedType)
            && Collection.class.isAssignableFrom(actualType)) {
            assertEqualsCollections(new ArrayList<>(
                    (Collection) actual),
                new ArrayList<>((Collection) expected),
                mustEqualsType
            );
            return;
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
            assertEqualsMaps((Map) actual, (Map) expected, mustEqualsType);
            return;
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
            assertEqualsObjects(actual, expected, mustEqualsType);
        } catch (Exception e) {
            throw new AssertionError(
                "\nexpected:\n" + toString(expectedType, expected) +
                    "\nbut was:\n" + toString(actualType, actual),
                e
            );
        }
    }

    private static void assertEqualsArrays(
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
    }

    private static void assertEqualsCollections(
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
                String builder = "expected: " + toString(expectedItem) +
                    " but was: " + toString(actualItem);
                throw new AssertionError(
                    builder + "\nexpect:\n" + toString(expected) +
                        "\nactual:\n" + toString(actual),
                    e
                );
            }
        }
    }

    private static void assertEqualsMaps(
        Map actual,
        Map expected,
        boolean mustEqualsType
    ) {
        assertEqualsMaps(
            actual.getClass(),
            expected.getClass(),
            actual,
            expected,
            mustEqualsType,
            "key"
        );
    }

    private static void assertEqualsMaps(
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
                "\nexpected (" + "size = " + expectedLength + "):\n" + toString(expectedType, expected) +
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
                        "\nexpected:\n" + toString(expectedType, expected) +
                        "\nactual:\n" + toString(actualType, actual),
                    e
                );
            }
            try {
                assertEquals(actualEntry.getValue(), expectedEntry.getValue(), mustEqualsType);
            } catch (AssertionError e) {
                throw new AssertionError(
                    "expected value: " + toString(expectedEntry.getValue()) +
                        " but was: " + toString(actualEntry.getValue()) +
                        "\nexpected:\n" + toString(expectedType, expected) +
                        "\nactual:\n" + toString(actualType, actual),
                    e
                );
            }
        }
    }

    private static void assertEqualsObjects(
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
        assertEqualsMaps(
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
