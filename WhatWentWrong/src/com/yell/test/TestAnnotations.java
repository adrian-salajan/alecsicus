package com.yell.test;

import com.yell.webservice.Service;

public class TestAnnotations {

	public static void main(String[] args) {
		Lion l = new Lion();
		System.out.println(l.getLionAge());
		System.out.println(l.getLionAge());
		System.out.println(l.getLionAge());
		System.out.println(l.getLionAge());
		System.out.println(l.getLionAge());

		System.out.println("MyAlert: " + Service.yellMessageList.poll().getMessage());
		System.out.println("MyAlert: " + Service.yellMessageList.poll().getMessage());

	}

}
