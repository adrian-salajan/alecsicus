package com.yell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YellException {
	
	String message() default "Exception ";
	
	Class<? extends Throwable> throwable();

	Priority priority() default Priority.HEIGH;
}
