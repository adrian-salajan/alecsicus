package com.yell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YellThrowsException {

	String message();

	Class<? extends Throwable> expected() default None.class;

	static class None extends Throwable {
	}
	
}
