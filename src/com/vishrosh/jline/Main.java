package com.vishrosh.jline;

public class Main {
	
	static Engine e;
	
	public static void main(String[] args) {
		System.out.println(args[0]);
		e = new Engine();
		e.setFilePath(args[0]);
		e.run();
	}
	
}
