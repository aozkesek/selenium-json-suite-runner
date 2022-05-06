package org.ao.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableModel<T> implements Model {
	
	private String name;
	private T value;
	private T reValue;
	
	public VariableModel(String name, T value) {
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

	public T getValue() {
        
        if (value == null)
            return null;
		
		if (value instanceof String)
			if (contains(String.valueOf(value)))
				//do not return the reValue, it needs to be re-calculated
                return value;

		return reValue;	
	}
	
	public void setValue(T value) {
		// always save into the reValue
		this.reValue = value;
	}
	
	@Override
	public String toString() {
		return getValue() == null ? null : String.valueOf(getValue());
	}
	
    private final static Pattern VariablePattern = Pattern
        .compile("\\$\\{[A-Z,a-z,_][A-Z,a-z,0-9,.,_]+\\}");
	
	public static String toVariable(String name) {
        
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
	
	public static Boolean contains(String source) {
        
        if (source == null)
            return false;
		
        return VariablePattern
            .matcher(source)
            .find(0);
	}
	
}
