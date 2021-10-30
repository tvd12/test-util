package com.tvd12.test.performance;

import java.lang.reflect.Method;

import com.tvd12.test.reflect.MethodInvoker;

/**
 * 
 * This class support to test performance of method
 * 
 * @author tavandung12
 *
 */
public class MethodPerformance {

    // performance object
    private Performance performance = Performance.create();

    // object invoker object
    private MethodInvoker invoker = MethodInvoker.create();

    // builder object
    public static MethodPerformance create() {
        return new MethodPerformance();
    }

    /**
     * execute test script
     * 
     * @return this pointer
     */
    public MethodPerformance execute() {
        performance.test(new Script() {
            @Override
            public void execute() {
                invoker.invoke();
            }
        });
        return this;
    }

    /**
     * set number of script invocations
     * 
     * @param count number script invocations
     * @return this pointer
     */
    public MethodPerformance loop(int count) {
        performance.loop(count);
        return this;
    }

    /**
     * set method name
     * 
     * @param name method name
     * @return this pointer
     */
    public MethodPerformance method(String name) {
        invoker.method(name);
        return this;
    }

    /**
     * set method to invoke
     * 
     * @param method method
     * @return this pointer
     */
    public MethodPerformance method(Method method) {
        invoker.method(method);
        return this;
    }

    /**
     * append parameter
     * 
     * @param value parameter value
     * @return this pointer
     */
    public MethodPerformance param(Object value) {
        invoker.param(value);
        return this;
    }

    /**
     * set which object call method
     * 
     * @param object object
     * @return this pointer
     */
    public MethodPerformance object(Object object) {
        invoker.object(object);
        return this;
    }

    /**
     * get performance time
     * 
     * @return time
     */
    public long getTime() {
        return performance.getTime();
    }

}
