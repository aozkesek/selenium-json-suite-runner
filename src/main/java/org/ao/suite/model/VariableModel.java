package org.ao.suite.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * this class holds variables 
 */
public class VariableModel {
	
	private String name;
	private Object value;
	private Object reValue;
	
	public VariableModel(String name, Object value) {
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

	public Object getValue() {
		if (value == null)
			return null;
		
		if (value instanceof String)
			if (containsVariableName(String.valueOf(value)))
				//do not return the reValue, it mights to be re-calculated
				return value;
		
		return reValue;	
	}
	
	public void setValue(Object value) {
		// always save into the reValue
		this.reValue = value;
	}
	
	@Override
	public String toString() {
		return getValue() == null ? null : String.valueOf(getValue());
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
