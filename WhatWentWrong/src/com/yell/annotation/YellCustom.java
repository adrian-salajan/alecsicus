package com.yell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YellCustom {

	String message();

	String element();
	
	String result();
	
	boolean resultEquals() default true;
	
	Priority priority() default Priority.MEDIUM;
}
