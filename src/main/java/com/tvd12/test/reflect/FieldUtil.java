package com.tvd12.test.reflect;

import java.lang.reflect.Field;

/**
 * 
 * Supports for reflection field interaction
 * 
 * @author tvd12
 *
 */
public final class FieldUtil {

    private FieldUtil() {}

    /**
     * Get field by name
     * 
     * @param clazz     the class contains the field
     * @param fieldName the field name
     * @return the field, null if field not found
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        if (clazz.equals(Object.class)) {
            return null;
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    /**
     * Get the field's value
     * 
     * @param <V>       the value type
     * @param object    the object to call
     * @param fieldName the field name
     * @return the field's value
     */
    public static <V> V getFieldValue(Object object, String fieldName) {
        V value = getFieldValue(object, fieldName, null);
        return value;
    }

    /**
     * Get the field's value and set default value if the field's value is null
     * 
     * @param <V>          the value type
     * @param object       the object to call
     * @param fieldName    the field name
     * @param defaultValue the default value
     * @return the field's value or default value
     */
    @SuppressWarnings("unchecked")
    public static <V> V getFieldValue(Object object, String fieldName, V defaultValue) {
        Class<?> clazz = object.getClass();
        Field field = getField(clazz, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("has no field: " + fieldName + " on " + clazz);
        }
        field.setAccessible(true);
        try {
            V value = (V) field.get(object);
            if (value == null && defaultValue != null) {
                value = defaultValue;
                field.set(object, defaultValue);
            }
            return value;
        } catch (Exception e) {
            throw new IllegalStateException(
                "can not get value of field: " + fieldName + " on " + object, 
                e
            );
        }
    }

    /**
     * Set value to the field
     * 
     * @param object    the object to call
     * @param fieldName the field name
     * @param value     the value to set
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Class<?> clazz = object.getClass();
        Field field = getField(clazz, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("has no field: " + fieldName + " on " + clazz);
        }
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (Exception e) {
            throw new IllegalStateException(
                "can not set value: " + value + " for field: " + fieldName + " on " + object, 
                e
            );
        }
    }

    /**
     * Get the field's value
     * 
     * @param <V>       the value type
     * @param clazz     the class to call
     * @param fieldName the field name
     * @return the field's value
     */
    public static <V> V getStaticFieldValue(Class<?> clazz, String fieldName) {
        V value = getStaticFieldValue(clazz, fieldName, null);
        return value;
    }

    /**
     * Get the field's value and set default value if the field's value is null
     * 
     * @param <V>          the value type
     * @param clazz        the class to call
     * @param fieldName    the field name
     * @param defaultValue the default value
     * @return the field's value or default value
     */
    @SuppressWarnings("unchecked")
    public static <V> V getStaticFieldValue(Class<?> clazz, String fieldName, V defaultValue) {
        Field field = getField(clazz, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("has no field: " + fieldName + " on " + clazz);
        }
        field.setAccessible(true);
        try {
            V value = (V) field.get(null);
            if (value == null && defaultValue != null) {
                value = defaultValue;
                field.set(null, defaultValue);
            }
            return value;
        } catch (Exception e) {
            throw new IllegalStateException(
                "can not get value of field: " + fieldName + " on " + clazz, 
                e
            );
        }
    }

    /**
     * Set value to the field of static class
     * 
     * @param clazz     the class to call
     * @param fieldName the field name
     * @param value     the value to set
     */
    public static void setStaticFieldValue(Class<?> clazz, String fieldName, Object value) {
        Field field = getField(clazz, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("has no field: " + fieldName + " on " + clazz);
        }
        field.setAccessible(true);
        try {
            field.set(null, value);
        } catch (Exception e) {
            throw new IllegalStateException(
                "can not set value: " + value + " for field: " + fieldName + " on " + clazz,
                e
            );
        }
    }

}
