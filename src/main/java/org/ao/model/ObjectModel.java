package org.ao.model;

import java.util.Map;

public class ObjectModel implements Model {
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

}
