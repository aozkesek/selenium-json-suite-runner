package org.ao.suite.model;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectModel {
	private String[] includes;
	private LinkedHashMap<String, Object> objects;

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public LinkedHashMap<String, Object> getObjects() {
		return objects;
	}

	public void setObjects(LinkedHashMap<String, Object> objects) {
		this.objects = objects;
	}

	@Override
	public String toString() {
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
