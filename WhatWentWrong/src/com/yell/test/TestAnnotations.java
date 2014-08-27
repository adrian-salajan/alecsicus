package com.yell.test;

import java.security.KeyException;

import com.yell.webservice.Service;

public class TestAnnotations {

	public static void main(String[] args) {
		Lion l = new Lion();
		System.out.println(l.getLionAge(-1));
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		
		Service.getInstance().setRun(false);
		
		System.out.println(l.getLionAge(-1));
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll());
		
		
		
		
//		System.out.println(l.getLionAge());
//		System.out.println(l.getLionAge());
//		System.out.println(l.getLionAge());
//		System.out.println(l.getLionAge());
		
		try {
			l.throwCheckedException();
		} catch (Exception e) {
		}
		
		try {
			l.throwCheckedException2();
		} catch (Exception e) {
		}
		
		try {
			l.throwRuntimeException();
		} catch (Exception e) {
		}
		
		try {
			l.throwRuntimeException2();
		} catch (Exception e) {
		}
		
		

		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll());

	}

}
