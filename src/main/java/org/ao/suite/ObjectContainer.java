package org.ao.suite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;

import org.ao.suite.model.VariableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@Scope("prototype")
public class ObjectContainer {
	
	private static Logger logger = LoggerFactory.getLogger(ObjectContainer.class);
	
	private ConcurrentHashMap<String, VariableModel> variables;
	
	@PostConstruct
	public void init() {
		variables = new ConcurrentHashMap<String, VariableModel>();
	}
	
	public VariableModel putVariable(String key, String value) {
		VariableModel variableModel = new VariableModel(key, value);
		
		if (variables.containsKey(key)) {
			variableModel = variables.replace(key, variableModel);
			logger.debug("replace {} = {}", key, value);
		}
		else {
			variableModel = variables.put(key, variableModel);
			logger.debug("new {} = {}", key, value);
		}
		
		return variableModel;
	}
	
	public String getVariable(String key) {
		if (key == null)
			return null;
		
		//check if the key is a variable
		if (VariableModel.containsVariableName(key))
			//yes, go deep inside then
			return getVariable(VariableModel.VariableName(key));
		
		if (!variables.containsKey(key))
			//not found in the container, check if it is system variable then
			return getSysVariable(key);
		
		String value = variables.get(key).getValue();
		if (!VariableModel.containsVariableName(value))
			//has a basic value, so return the what is found
			return value;
		
		// the value has another variable, go deep inside
		String refKey = VariableModel.VariableName(value);
		
		// first check if it is system variable
		if (refKey.startsWith("SYS_"))
			return getSysVariable(refKey);
		
		return getVariable(refKey);
		
		
	}
	
	public String getReplacedVariable(String source) {
		
		if (!VariableModel.containsVariableName(source))		
			return source;
		
		String ref = VariableModel.VariableName(source);
		String val = getVariable(ref);
		if (source.equals("${"+ref+"}"))
			source = val;
		else
			source = source.replace("${"+ref+"}", val);
		
		//continue to check if there is another
		return getReplacedVariable(source);
	}
	
	private String getSysVariable(String key) {
		
		if (key.equals("SYS_DATETIME_NOW"))
			//give it always the refreshed one
			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyMMdd.HHmmss.SSS"));
					
		// not a system variable, return back with it's own
		return key;
	}
	
	@Override
	public String toString() {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(variables);
		} catch (JsonProcessingException e) {
			return variables.toString();
		}
		
	}
	
	
}
