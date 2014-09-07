package com.yell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YellInt {

	String message();

	int result() default 2147483646;
	
	boolean resultEquals() default true;
	
	int times() default 1;

	int seconds() default -1;
	
	int min() default 2147483647;
	
	int max() default -2147483648;
	
	Priority priority() default Priority.MEDIUM;
}
