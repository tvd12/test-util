[![Build Status](https://travis-ci.org/tvd12/test-util.svg?branch=master)](https://travis-ci.org/tvd12/test-util)
[![Dependency Status](https://www.versioneye.com/user/projects/5717990efcd19a00415b1f61/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5717990efcd19a00415b1f61)
[![Coverage Status](https://coveralls.io/repos/github/tvd12/test-util/badge.svg?branch=master)](https://coveralls.io/github/tvd12/test-util?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.tvd12/test-util/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.tvd12/test-util)
[![Javadocs](https://www.javadoc.io/badge/com.tvd12/test-util.svg)](https://www.javadoc.io/doc/com.tvd12/test-util)

# Synopsis

This project support for: 
- testing private, protected, package access and public method
- testing script performance

# Documentation

[https://youngmonkeys.org/project/test-util](https://youngmonkeys.org/project/test-utilities/)

# Code Example

**1. Test script performance**

```java
long time = Performance.create()
	.threadCount(100) // set 0 if you want to run in sync mode
	.loop(1000000000) // optional, default 1000000
	.test(() -> System.out.println("Hello World"))
	.getTime();
```

**2. Assertion**

```java
Asserts.assertEquals(expected, actual);

Asserts.assertThat(actual).isEqualsTo(expected);

Asserts.assertThat(future).isEqualsTo(expected);
```

**3. Random**

```java
RandomUtil.randomSmallInt();

RandomUtil.randomShortAlphabetString();

RandomUtil.randomMap(size, int.class, String.class);
```

**4. Get method by name**

```java
// with no arguments
Method nothing = MethodUtil.getMethod("nothing", ClassA.class);

// with one argument (Integer)
Method add = MethodUtil.getMethod("add", ClassA.class, Integer.class);
```

**5. Invoke method**

```java
// invoke method
Integer result = MethodUtil.invokeMethod(add, new ClassA(), new Integer(1));

//invoke method by name
Integer result = MethodUtil.invokeMethod("add", new ClassA(), new Integer(1));

// invoke static method by name
MethodUtil.invokeStaticMethod("hello", ClassA.class, "tvd12.com");

// use builder syntax
Integer result = MethodInvoker.create()
        .method("add")
        .param(new Integer(1))
        .object(new ClassA())
        .invoke(Integer.class);
```

# Motivation

Because sometimes we want to call private, protected, package access and public method,
we need test performance to our script and export result to file
so, we need create a library to support them

# Installation

### Maven

```xml
<dependency>
	<groupId>com.tvd12</groupId>
	<artifactId>test-util</artifactId>
	<version>1.1.1</version>
</dependency>
```

You need create file `AllTests.tng.xml` in your `src/test/resources` folder with content, example:

```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="All-Test">
    <test name="All">
    	<packages>
    		<package name="your_test_package"/>
    	</packages>
    </test>
</suite>
```

### Gradle

```groovy
testImplementation 'com.tvd12:test-util:1.1.1'
```

# API Reference

http://www.javadoc.io/doc/com.tvd12/test-util

# Tests

mvn test

# Contributors

- [DungTV](mailto:itprono3@gmail.com)

# License

- Apache License, Version 2.0
	


