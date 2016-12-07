package org.ao.suite.model;

import java.util.LinkedHashMap;

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
		StringBuilder sb = new StringBuilder();
		objects.forEach((k,v) -> sb.append(k).append("=").append(v).append(", "));
		return sb.toString();
	}
}
