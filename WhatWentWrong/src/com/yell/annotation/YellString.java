package com.yell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YellString {

	String message();

	String result();
	
	boolean resultEquals() default true;
	
	int times() default 1;

	int seconds() default 60;
	
	Priority priority() default Priority.MEDIUM;
}
