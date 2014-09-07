package com.yell.webservice;

import java.util.LinkedList;
import java.util.Queue;


public class Service {

	private static Service instance  = new Service();
	
	private boolean run = false;
	
	private Service(){
		yellMessageList.add(new YellMessage("Yell service is OFF!"));
	};
	
	public static Service getInstance(){
		return instance;
	}

	private Queue<YellMessage> yellMessageList = new LinkedList<YellMessage>();
	
	public Queue<YellMessage> getMessages() {
		return yellMessageList;
	}
	
	public void setRun(boolean run) {
		this.run = run;
		if (run) {
			Service.getInstance().getMessages().add(new YellMessage("Enjoy the silence."));
		} else 
			Service.getInstance().getMessages().add(new YellMessage("Yell service is OFF!"));
	}
	
	public boolean isRun() {
		return this.run;
	}
	
	
}
