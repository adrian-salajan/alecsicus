package com.yell.webservice;

import java.sql.Timestamp;
import java.util.Date;

import com.yell.annotation.Priority;

public class YellMessage {

	private String message;
	private Timestamp timestamp;
	private String className;
	private String methodName;
	private Priority priority;

	public YellMessage() {
	}

	public YellMessage(String message,  String className,
			String methodName, String priority) {
		this.message = message;
		Date d = new Date();
		long time = d.getTime();
		timestamp = new Timestamp(time);
		this.className = className;
		this.methodName = methodName;
		
		if (Priority.HEIGH.toString().equals(priority)){
			this.priority = Priority.HEIGH;
		} else if (Priority.MEDIUM.toString().equals(priority)){
			this.priority = Priority.MEDIUM;
		} else {
			this.priority = Priority.LOW;
		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	
}
