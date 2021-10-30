package com.tvd12.test.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Build to get reflection method
 * 
 * @author tvd12
 *
 */
public class MethodBuilder {

    // method name
    private String methodName;

    // declaring class of method
    private Class<?> clazz;

    // list of arguments
    private List<Class<?>> arguments;

    public MethodBuilder() {
        arguments = new ArrayList<>();
    }

    // factory method
    public static MethodBuilder create() {
        return new MethodBuilder();
    }

    /**
     * Set method name
     * 
     * @param name method name
     * @return this pointer
     */
    public MethodBuilder method(String name) {
        this.methodName = name;
        return this;
    }

    /**
     * Set declaring class of method
     * 
     * @param clazz class
     * @return this pointer
     */
    public MethodBuilder clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * Add argument
     * 
     * @param argument the argument
     * @return this pointer
     */
    public MethodBuilder argument(Class<?> argument) {
        this.arguments.add(argument);
        return this;
    }

    /**
     * Add arguments
     * 
     * @param arguments the arguments
     * @return this pointer
     */
    public MethodBuilder arguments(Class<?>... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    /**
     * Build a method
     * 
     * @return a method
     */
    public Method build() {
        return ReflectMethodUtil.getMethod(
            methodName, 
            clazz, 
            arguments.toArray(new Class<?>[arguments.size()])
        );
    }

}
