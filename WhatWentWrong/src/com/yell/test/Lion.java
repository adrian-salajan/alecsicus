package com.yell.test;

import java.security.KeyException;

import com.yell.annotation.Yell;
import com.yell.annotation.YellCustom;
import com.yell.annotation.YellException;
import com.yell.annotation.YellInt;
import com.yell.annotation.YellIntArray;
import com.yell.annotation.YellRegexPattern;
import com.yell.annotation.YellStringArray;

@Yell
public class Lion {

	Name name = new Name("Simba", "The Lion");
	
	@YellInt(message = "A lion can not have -1 years!!!", result = -1)
	public int getLionAge1(int x) {
		System.out.println("using x: " + x);
		return -1;
	}
	
	@YellInt(message = "A lion can not have -2 years!!!", result = -1, resultEquals = false)
	public int getLionAge2(int x) {
		System.out.println("using x: " + x);
		return 2;
	}
	
	@YellInt(message = "A lion can not have -3 years!!!", min = 0)
	public int getLionAge3(int x) {
		System.out.println("using x: " + x);
		return 7;
	}
	
	@YellInt(message = "A lion can not have -3a years!!!", min = 0)
	public int getLionAge3a(int x) {
		System.out.println("using x: " + x);
		return 0;
	}
	
	@YellInt(message = "A lion can not have -3b years!!!", min = 0)
	public int getLionAge3b(int x) {
		System.out.println("using x: " + x);
		return -7;
	}
	@YellInt(message = "A lion can not have -4 years!!!", min = 10, resultEquals = false)
	public int getLionAge4(int x) {
		System.out.println("using x: " + x);
		return 11;
	}
	
	@YellInt(message = "A lion can not have -5 years!!!", max = 10)
	public int getLionAge5(int x) {
		System.out.println("using x: " + x);
		return 30;
	}
	
	@YellInt(message = "A lion can not have -6 years!!!", max = 2, resultEquals = false)
	public int getLionAge6(int x) {
		System.out.println("using x: " + x);
		return 1;
	}
	
	@YellInt(message = "A lion can not have -7 years!!!", min = 0, max = 10)
	public int getLionAge7(int x) {
		System.out.println("using x: " + x);
		return 3;
	}
	
	@YellInt(message = "A lion can not have -8 years!!!", min = 10, max = 0, resultEquals = false)
	public int getLionAge8(int x) {
		System.out.println("using x: " + x);
		return 3;
	}
	
	@YellIntArray(message = "A lion can not have -9 years!!!", result = {1, 2, 3})
	public int getLionAge9(int x) {
		System.out.println("using x: " + x);
		return 3;
	}
	
	@YellIntArray(message = "A lion can not have -10 years!!!", result = {1, 2, 3}, in = false)
	public int getLionAge10(int x) {
		System.out.println("using x: " + x);
		return 10;
	}
	
	
	@YellStringArray(message = "A lion can not have -11 years!!!", result = {"SIMBA", "MAYA", "KING"})
	public String getLionName11() {
		
		return "SIMBA";
	}
	
	@YellStringArray(message = "A lion can not have -12 years!!!", result = {"MAYA", "KING"}, in = false)
	public String getLionName12() {
		
		return "SIMBA";
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

	@YellCustom(element = "firstName", message = "Lion name is Simba", result = "Simba")
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	@YellRegexPattern(message = "http://SimbaTheLion.com", regexPattern = "http(.*)")
	public String pageURL(){
		return "http://SimbaTheLion.com";
	}
	
	@YellInt(message = "3 was returned !! ", result = 3)
	public int runLion() throws InterruptedException {
		String r = "Lion is going to run........";
		Thread.sleep(2000L);
		return 3;
	}

}
