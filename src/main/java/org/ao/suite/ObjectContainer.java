package org.ao.suite;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ObjectContainer {
	protected static Pattern VariablePattern = Pattern.compile("\\$\\{[A-Z,a-z,_][A-Z,a-z,0-9,.,_]+\\}");
	
	private static ConcurrentHashMap<String, Object> variables;
	private static Logger logger = LoggerFactory.getLogger(ObjectContainer.class);
	
	@PostConstruct
	public void init() {
		variables = new ConcurrentHashMap<String, Object>();
	}
	
	public void putVariable(String key, Object object) {
		variables.put(key, object);
		logger.debug("put object {} = {}", key, object);
	}
	
	public Object getVariable(String key) {
		return variables.get(key);
	}
	
	public boolean containsVariablesKey(String key) {
		return variables.containsKey(key);
	}
	
	public String getVariableName(String input) {
		if (input == null)
			return null;
		
		Matcher matcher = VariablePattern.matcher(input);
		if (!matcher.find(0)) 
			return null;
			
		// before return the extracted ${}, 
		// do take care array and index notation -> [index_of_array]
		String varName = input.substring(matcher.start() + 2, matcher.end() - 1);
		
		logger.debug("{} is extracted from {}", varName, input);
		return varName;
	}
	
	public boolean containsVariable(String input) {
		
		if (input == null)
			return false;
		
		Matcher matcher = VariablePattern.matcher(input);
		
		return matcher.find(0);
	}
	
	public String replaceVariables(String input) {
		if (input == null)
			return null;
		
		Matcher matcher = VariablePattern.matcher(input);
		boolean isFound = matcher.find(0);
		
		if (!isFound)
			return input;
		
		logger.debug("before {}", input);
		HashMap<String, String> kvPairs = new HashMap<String, String>();
		
		while (isFound) {
			String varName = input.substring(matcher.start() + 2, matcher.end() - 1);
			Object varValue = getVariable(varName);
			kvPairs.put(varName, String.valueOf(varValue));
			isFound = matcher.find();
		}
		
		for (Entry<String, String> pair: kvPairs.entrySet())
			input = input.replaceAll("\\$\\{" + pair.getKey() + "\\}", pair.getValue());	
		
		logger.debug("after {}", input);
		return input;
	}

}
