package com.yell.webservice;

import java.util.LinkedList;
import java.util.Queue;


public class Service {

	private static Service instance;
	
	private Service(){};
	
	public static Service getInstance(){
		if (instance == null){
			instance = new Service();
		}
		return instance;
	}

	public static Queue<YellMessage> yellMessageList = new LinkedList<YellMessage>();
	
	
}
