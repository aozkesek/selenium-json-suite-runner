package org.ao.suite.model;

import java.util.LinkedHashMap;

import org.ao.suite.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

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
		try {
			return new ObjectMapperFactory().getObjectMapper()
					.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
