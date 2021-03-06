package com.tvd12.test.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tvd12.test.util.Pair;

/**
 * 
 * A builder class support to build a method invoker
 * to invoke method
 * 
 * @author tavandung12
 *
 */
@SuppressWarnings("rawtypes")
public class MethodInvoker {

    //object invoke method on this object 
    private Object object;
    
    //method to invoke
    private Method method;
    
    //method name
    private String methodName;
    
    //list of parameters
	private List<Pair<Class, Object>> params;
    
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
     * @param value the parameter value
     * @return this pointer
     */
    public MethodInvoker param(Object value) {
    		Class valueType = value.getClass();
        this.params.add(new Pair<>(valueType, value));
        return this;
    }
    
    /**
     * append parameters
     * 
     * @param values the parameter values
     * @return this pointer
     */
    public MethodInvoker params(Object... values) {
    		for(Object value : values)
    			param(value);
    		return this;
    }
    
    /**
     * append parameter
     * 
     * @param paramType the parameter type
     * @param value the parameter value
     * @return this pointer
     */
    public MethodInvoker param(Class<?> paramType, Object value) {
        this.params.add(new Pair<>((Class)paramType, value));
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
    		int paramCount = params.size();
    		Object[] paramArray = new Object[paramCount];
    		Class<?>[] paramTypeArray = new Class<?>[paramCount];
    		for(int i = 0 ; i < paramCount ; i++) {
    			Pair<Class, Object> param = params.get(i);
    			paramTypeArray[i] = param.getKey();
    			paramArray[i] = param.getValue();
    		}
        if(method == null) {
        		Class<?> objectType = object.getClass();
        		method = ReflectMethodUtil
                .getMethod(methodName, objectType, paramTypeArray);
        }
        return ReflectMethodUtil.invokeMethod(method, object, paramArray);
    }
    
    /**
     * invoke method and cast returned result with type
     * 
     * @param <T> the output type
     * @param type casting type
     * @return the value returned by the invoked method
     */
    public <T> T invoke(Class<T> type) {
    		Object answer = invoke();
        return type.cast(answer);
    }
    
}
