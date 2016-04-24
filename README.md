[![Build Status](https://travis-ci.org/tavandung12/test-util.svg?branch=master)](https://travis-ci.org/tavandung12/test-util)
[![Dependency Status](https://www.versioneye.com/user/projects/5717990efcd19a00415b1f61/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5717990efcd19a00415b1f61)
[![Coverage Status](https://coveralls.io/repos/github/tavandung12/test-util/badge.svg?branch=master)](https://coveralls.io/github/tavandung12/test-util?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.tvd12/test-util/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.tvd12/test-util)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tvd12/test-util/badge.svg)](http://www.javadoc.io/doc/com.tvd12/test-util)

#Synopsis

This project support for: 
- testing private, protected, package access and public method
- testing script performance

#Code Example

**1. Get method by name**

```java
	// with no arguments
	Method nothing = ReflectMethodUtil.getMethod("nothing", ClassA.class);
	// with one argument (Integer)
	Method add = ReflectMethodUtil.getMethod("add", ClassA.class, Integer.class);
```

**2. Invoke method**
```java
	// invoke method
	Object result = ReflectMethodUtil.invokeMethod(add, new ClassA(), new Integer(1));
	//invoke method by name
	Object result = ReflectMethodUtil.invokeMethod("add", new ClassA(), new Integer(1));
	// invoke static method by name
	ReflectMethodUtil.invokeStaticMethod("hello", ClassA.class, "tvd12.com");
	// use builder sytax
	Integer result = MethodInvoker.create()
                .method("add")
                .param(new Integer(1))
                .object(new ClassA())
                .invoke(Integer.class);
```

**3. Test script performance**
- With java 7
```java
	long time = Performance.create()
            .loop(1000000000) // optional, default 1000000
            .test(new Script() {
                @Override
                public void execute() {
                	System.out.println("Hello World");
                }
            })
            .getTime();
```
- With java 8
```java
	long time = Performance.create()
            .loop(1000000000) // optional, default 1000000
            .test(() -> {System.out.println("Hello World");})
            .getTime();
```
#Motivation

Because sometimes we want to call private, protected, package access and public method,
we need test performance to our script and export result to file
so, we need create a library to support theme

#Installation

```xml
	<dependency>
		<groupId>com.tvd12</groupId>
		<artifactId>test-util</artifactId>
		<version>1.0.1</version>
	</dependency>
```
#API Reference

http://www.javadoc.io/doc/com.tvd12/test-util

#Tests

mvn test

#Contributors

None

#License

- Apache License, Version 2.0
	


