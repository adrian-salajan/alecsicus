package com.yell.test;

import java.security.KeyException;

import com.yell.annotation.Yell;
import com.yell.annotation.YellException;
import com.yell.annotation.YellInt;

@Yell
public class Lion {

	@YellInt(message = "A lion can not have -1 years!!!", result = -1)
	public int getLionAge(int x) {
		System.out.println("using x: " + x);
		return x;
	}
	
	@YellException(throwable = NullPointerException.class)
	public int throwRuntimeException() {
		throw new NullPointerException("test-npe");
	}
	@YellException(throwable = NullPointerException.class)
	public void throwRuntimeException2() {
		throw new NullPointerException("test-npe-void");
	}
	
	@YellException(throwable = KeyException.class)
	public int throwCheckedException() throws KeyException {
		throw new KeyException("checked-test");
	}
	
	@YellException(throwable = KeyException.class)
	public void throwCheckedException2() throws KeyException {
		throw new KeyException("checked-test-void");
	}
}
