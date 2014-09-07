package com.yell.test;

import com.yell.webservice.Service;

public class TestAnnotations {

	public static void main(String[] args) {
		Service.getInstance().setRun(true);
		Lion l = new Lion();
		System.out.println(l.getLionAge1(-1));
		System.out.println(l.getLionAge2(2));
		System.out.println(l.getLionAge3(-1));	
		System.out.println(l.getLionAge3a(-1));
		System.out.println(l.getLionAge3b(-1));
		System.out.println(l.getLionAge4(-1));
		System.out.println(l.getLionAge5(-1));
		System.out.println(l.getLionAge6(-1));
		System.out.println(l.getLionAge7(-1));
		System.out.println(l.getLionAge8(-1));
		System.out.println(l.getName());
		System.out.println(l.getLionAge9(-1));
		System.out.println(l.getLionAge10(-1));
		System.out.println(l.getLionName11());
		System.out.println(l.getLionName12());
		System.out.println(l.pageURL());
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
		
		

		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());
		System.out.println("MyAlert: " + Service.getInstance().getMessages().poll().getMessage());

	}

}
