package com.webapp1;

//to be instrumented java class
public class Lion {
	public String runLion() throws InterruptedException {
		String r = "Lion is going to run........";
		Thread.sleep(2000L);
		return r;
	}

}
