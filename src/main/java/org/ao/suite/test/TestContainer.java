package org.ao.suite.test;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TestContainer {

	protected static Pattern VariablePattern = Pattern.compile("\\$\\{[A-Z,a-z,_][A-Z,a-z,0-9,.,_,\\[,\\]]+\\}");
	
	private static ConcurrentHashMap<String, TestDriver> testDrivers;
	private static ConcurrentHashMap<String, Object> variables;
	
	@PostConstruct
	public void init() {
		testDrivers = new ConcurrentHashMap<String, TestDriver>();
		variables = new ConcurrentHashMap<String, Object>();
	}
	
	public void putTestDriver(String key, TestDriver testDriver) {
		testDrivers.put(key, testDriver);
		
	}
	
	public TestDriver getTestDriver(String key) {
		return testDrivers.get(key);
	}
	
	public boolean containsTestDriverKey(String key) {
		return testDrivers.containsKey(key);
	}

	public void putVariable(String key, Object object) {
		variables.put(key, object);
		
	}
	
	public Object getVariable(String key) {
		return variables.get(key);
	}
	
	public boolean containsVariablesKey(String key) {
		return variables.containsKey(key);
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
		
		HashMap<String, String> kvPairs = new HashMap<String, String>();
		
		while (isFound) {
			String varName = input.substring(matcher.start() + 2, matcher.end() - 1);
			Object varValue = getVariable(varName);
			kvPairs.put(varName, varValue.toString());
			isFound = matcher.find();
		}
		
		for (Entry<String, String> pair: kvPairs.entrySet())
			input = input.replaceAll("\\$\\{" + pair.getKey() + "\\}", pair.getValue());	
		
		return input;
	}
}
