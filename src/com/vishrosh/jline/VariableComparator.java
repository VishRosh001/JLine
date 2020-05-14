package com.vishrosh.jline;

import java.util.Comparator;

public class VariableComparator implements Comparator<String>{
	
	private int getLineFromVariable(String variable) {
		return Integer.parseInt(variable.substring(2, variable.length()));
	}
	
	@Override
	public int compare(String variable1, String variable2) {
		int temp = this.getLineFromVariable(variable1)-this.getLineFromVariable(variable2);
		if(temp == 0)return 0;
		if(temp < 0)return -1;
		return 1;
	}
	
}
