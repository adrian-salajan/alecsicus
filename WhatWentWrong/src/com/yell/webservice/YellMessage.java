package com.yell.webservice;

import java.util.Date;

public class YellMessage {

	private String message;
	private Date dateAndTime;
	private String className;
	private String methodName;

	public YellMessage() {
	}

	public YellMessage(String message,  String className,
			String methodName) {
		this.message = message;
		//this.dateAndTime = date;
		this.className = className;
		this.methodName = methodName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
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

}
