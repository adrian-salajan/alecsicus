package com.webapp1;

import com.yell.annotation.Yell;
import com.yell.annotation.YellInt;

//to be instrumented java class
@Yell
public class Lion {
	
	@YellInt(message = "3 was returned !! ", result = 3)
	public int runLion() throws InterruptedException {
		String r = "Lion is going to run........";
		Thread.sleep(2000L);
		return 3;
	}

}
