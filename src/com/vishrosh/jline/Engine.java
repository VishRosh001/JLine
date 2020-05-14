package com.vishrosh.jline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {
	
	private String filePath;
	List<String> variables = new ArrayList<>();
	VariableComparator variableComparator = new VariableComparator();
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	private String getJavaFilePath() {
		return new String(this.filePath).replace("jline", "java");
	}
	
	@SuppressWarnings("unused")
	private boolean doesLineContainVars(String line) {
		Pattern pattern = Pattern.compile("ln[0-9]+");
		return pattern.matcher(line).find();
	}
	
	@SuppressWarnings("unused")
	private int getLineFromVariable(String variable) {
		return Integer.parseInt(variable.substring(3, variable.length()-1));
	}
	
	private void addToVariables(String line) {
		Matcher m = Pattern.compile("ln[0-9]+").matcher(line);
		while(m.find()) {
			String res = m.group();
			if(!variables.contains(res)) {
				variables.add(res);
			}
		}
	}
	
	private void setupJavaFile() {
		File javaFile = new File(this.getJavaFilePath());
		BufferedReader br = null;
		FileWriter fileWriter = null;
		FileReader fileReader = null;;
		try {
			fileReader = new FileReader(this.getFilePath());
			br = new BufferedReader(fileReader);
			fileWriter = new FileWriter(javaFile, true);
			
			fileWriter.write("public class test{\n");
			fileWriter.write("\tpublic static void main(String[] args){\n");
			String s;
			while((s = br.readLine()) != null) {
				addToVariables(s);
			}
			variables.sort(variableComparator);
			
			fileReader = new FileReader(this.getFilePath());
			br = new BufferedReader(fileReader);
			
			int lineNumber = 1;
			while((s = br.readLine()) != null) {
				if(variables.contains("ln"+lineNumber))
					fileWriter.write("\t\t int ln"+lineNumber +" = " +s+";\n");
				else
					fileWriter.write("\t\t"+s+"\n");
				++lineNumber;
			}
			
			fileWriter.write("\t}\n}");
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileWriter != null)fileWriter.close();
				if(br != null)br.close();
				if(fileReader != null)fileReader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void run() {
		this.setupJavaFile();
		System.out.println(variables.toString());
	}
	
	
}
