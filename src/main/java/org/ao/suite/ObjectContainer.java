package org.ao.suite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.ao.suite.model.VariableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ObjectContainer {
	
	private Map<String, VariableModel<Object>> variables = new HashMap<>();
	
	@Autowired
	private ObjectMapperFactory objectMapperFactory;
    
	public VariableModel<Object> putVariable(String key, String value) {
        
        VariableModel<Object> variableModel = variables
            .put(key, new VariableModel<Object>(key, value));
        
		return variableModel;
	}
	
	public Object getVariable(String key) {
		if (key == null)
			return null;
		
		//check if the key is a variable
		if (VariableModel.containesVariable(key)) {
			//yes, go deep inside then
            return getVariable(VariableModel.toVariable(key));
        }
		
		if (!variables.containsKey(key)) {
			//not found in the container, check if it is system variable then
            return getSysVariable(key);
        }
		
		VariableModel<Object> variable = variables.get(key);
		if (variable.getValue() == null) {
            return null;
        }
		
		if (!(variable.getValue() instanceof String)) {
			// not a string, then return it
            return variable.getValue();
        }
			
		String value = variable.getValue().toString();
		if (!VariableModel.containesVariable(value)) {
			//has a basic value, so return the what is found
            return value;
        }
		
		// the value has another variable, go deep inside
		String refKey = VariableModel.toVariable(value);
		
		// first check if it is system variable
		if (refKey.startsWith("SYS_")) {
            return getSysVariable(refKey);
        }
		
		return getVariable(refKey);
		
		
	}
	
	public String getReplacedVariable(String source) {
		
		if (!VariableModel.containesVariable(source)) {
		// not include variable, return it's own
            return source;
        }
		
		String ref = VariableModel.toVariable(source);
		Object oVal = getVariable(ref); 
		// we expect here pure string value
		String val = oVal == null ? "" : oVal instanceof String ? String.valueOf(oVal) : "";
		if (source.equals("${"+ref+"}")) {
			source = val;
        }
        else {
            source = source.replace("${"+ref+"}", val);
        }
		
		//continue to check if there is another
		return getReplacedVariable(source);
	}
	
	private String getSysVariable(String key) {
        
        // make this plugable later
		if (key.equals("SYS_DATETIME_NOW")) {
			//give it always the refreshed one
			return LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss.SSS"));
        }
		// insert here others you want
		
		// not a system variable, return back with it's own
		return key;
	}
	
	@Override
	public String toString() {
		try {
            return objectMapperFactory
                .getObjectMapper()
				.writeValueAsString(variables);
		} catch (JsonProcessingException e) {
			return variables.toString();
		}		
	}
	
	
}
