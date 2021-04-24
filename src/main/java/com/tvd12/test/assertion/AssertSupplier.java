package com.tvd12.test.assertion;

public interface AssertSupplier<T> {

	T apply() throws Throwable;
	
}
