package com.tvd12.test.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A builder class support to build a method invoker
 * to invoke method
 * 
 * @author tavandung12
 *
 */
public class MethodInvoker {

    //list of parameters
    @SuppressWarnings("rawtypes")
    private List params;

    //object invoke method on this object 
    private Object object;
    
    //method to invoke
    private Method method;
    
    //method name
    private String methodName;
    
    //constructor
    public MethodInvoker() {
        this.params = new ArrayList<>();
    }
    
    //builder method
    public static MethodInvoker create() {
        return new MethodInvoker();
    }
    
    /**
     * Set method name
     * 
     * @param name method name
     * @return this pointer
     */
    public MethodInvoker method(String name) {
        this.methodName = name;
        return this;
    }
    
    /**
     * Set method
     * 
     * @param method method to invoke
     * @return this pointer
     */
    public MethodInvoker method(Method method) {
        this.method = method;
        return this;
    }
    
    /**
     * append parameter
     * 
     * @param value parameter value
     * @return this pointer
     */
    @SuppressWarnings("unchecked")
    public MethodInvoker param(Object value) {
        this.params.add(value);
        return this;
    }
    
    /**
     * set object
     * 
     * @param object object
     * @return this pointer
     */
    public MethodInvoker object(Object object) {
        this.object = object;
        return this;
    }
    
    /**
     * invoke method
     * 
     * @return the value returned by the invoked method
     */
    public Object invoke() {
        Object[] paramArray = params.toArray();
        if(method == null) 
            method = ReflectMethodUtil
                .getMethod(methodName, object, paramArray);
        return ReflectMethodUtil.invokeMethod(method, object, paramArray);
    }
    
    /**
     * invoke method and cast returned result with type
     * 
     * @param <T> the class type
     * @param type casting type
     * @return the value returned by the invoked method
     */
    public <T> T invoke(Class<T> type) {
        return type.cast(invoke());
    }
    
}
