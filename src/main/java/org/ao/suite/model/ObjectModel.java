package org.ao.suite.model;

import java.util.Map;
import org.ao.suite.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * This class holds objects definition that loaded from user object script file 
 */
public class ObjectModel {
	private String[] includes;
	private Map<String, String> objects;

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public Map<String, String> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, String> objects) {
		this.objects = objects;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapperFactory().getObjectMapper()
					.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
