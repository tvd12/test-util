package com.tvd12.test.assertion;

/**
 * Assert functional interface
 *
 * @author tvd12
 */
public interface AssertSupplier<T> {

    T apply() throws Throwable;
}
