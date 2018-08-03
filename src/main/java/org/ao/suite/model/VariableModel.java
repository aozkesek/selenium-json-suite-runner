package org.ao.suite.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableModel {
	
	private String name;
	private String value;
	private String reValue;
	
	public VariableModel(String name, String value) {
		this.name = name;
		this.value = value;
		this.reValue = value;
	}
	
	public VariableModel(String name) {
		this(name, null);
	}
	
	public String getName() {		
		return name;
	}

	public String getValue() {
		if (value == null)
			return null;
		
		if (containsVariableName(value))
			//do not return the reValue, it mights to be re-calculated
			return value;
		
		return reValue;	
	}
	
	public void setValue(String value) {
		// always save into the reValue
		this.reValue = value;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	private final static Pattern VariablePattern = Pattern.compile("\\$\\{[A-Z,a-z,_][A-Z,a-z,0-9,.,_]+\\}");
	
	public static String VariableName(String name) {
		if (name == null)
			return null;
		
		Matcher matcher = VariablePattern.matcher(name);
		if (!matcher.find(0)) 
			return null;
			
		// before return the extracted ${}, 
		// do take care array and index notation -> [index_of_array]
		String varName = name.substring(matcher.start() + 2, matcher.end() - 1);
		return varName;
	}
	
	public static Boolean containsVariableName(String source) {
		if (source == null)
			return false;
		
		Matcher matcher = VariablePattern.matcher(source);
		
		return matcher.find(0);
	}
	
}
