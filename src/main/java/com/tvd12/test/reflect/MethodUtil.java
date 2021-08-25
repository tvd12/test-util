package com.tvd12.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class MethodUtil {
    
    //prevent new instance
    private MethodUtil() {}
    
    /**
     * Get a method (public, private, protected, package access) 
     * with the given method name and the given parameter types, 
     * declared on the given class or one of its superclasses.
     * 
     * @param methodName method name
     * @param clazz class to find
     * @param parameterTypes array of parameter types
     * @return a method
     * @exception IllegalStateException when method not exists
     */
    public static Method getMethod(String methodName, 
            Class<?> clazz, Class<?>... parameterTypes) {
        return ReflectMethodUtil.getMethod(methodName, clazz, parameterTypes);
    }
    
    /**
     * Get a method (public, private, protected, package access) 
     * with the given method name and the given parameter types, 
     * declared on the given class or one of its superclasses. 
     * 
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param object object to find
     * @param params array of parameters
     * @return a method
     * @exception IllegalStateException when method not exists
     */
    public static Method getMethod(String methodName, 
            Object object, Object... params) {
    	return ReflectMethodUtil.getMethod(methodName, object, params);
    }
    
    /**
     * Get constructor of class
     * 
     * @param clazz declaring class
     * @param parameterTypes array of parameter types
     * @return a constructor
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        return ReflectMethodUtil.getConstructor(clazz, parameterTypes);
    }
    
    /**
     * Invokes a method whose parameter types match exactly the parameter types given.
     * 
     * @param method method to invoke
     * @param object invoke method on this object 
     * @param params array of parameter
     * @param <T> the return type
     * @return the value returned by the invoked method
     * @exception IllegalStateException when can not invoke method
     */
    @SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Method method, 
            Object object, Object... params) {
        return (T)ReflectMethodUtil.invokeMethod(method, object, params);
    }
    
    /**
     * Invokes a method whose parameter types match exactly the parameter types given.
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param object invoke method on this object 
     * @param params array of parameter
     * @param <T> the result type
     * @return the value returned by the invoked method
     */
    @SuppressWarnings("unchecked")
	public static <T> T invokeMethod(String methodName, 
            Object object, Object... params) {
        return (T)ReflectMethodUtil.invokeMethod(methodName, object, params);
    }
    
    /**
     * Invokes a static method whose parameter types match exactly the parameter types given.
     * Note: unsupport params is primitive types
     * 
     * @param methodName method name
     * @param clazz invoke method on this class 
     * @param params array of parameter
     * @param <T> the result type
     * @return the value returned by the invoked method
     */
    @SuppressWarnings("unchecked")
	public static <T> T invokeStaticMethod(String methodName, 
            Class<?> clazz, Object... params) {
        return (T)ReflectMethodUtil.invokeStaticMethod(methodName, clazz, params);
    }
    
    /**
     * Invokes a static method whose parameter types match exactly the parameter types given.
     * 
     * @param method method to invoke
     * @param params array of parameter
     * @return the value returned by the invoked method
     */
    public static <T> T invokeStaticMethod(Method method, Object... params) {
        return invokeMethod(method, null, params);
    }
    
    /**
     * Invoke constructor on class
     * 
     * @param clazz declaring class
     * @param params array of parameters
     * @param <T> the result type
     * @return the result
     */
    @SuppressWarnings("unchecked")
	public static <T> T invokeConstructor(Class<?> clazz, Object... params) {
        return (T)ReflectMethodUtil.invokeConstructor(clazz, params);
    }
}
