package com.github.darknessrising.maps.tools.gleed2d.properties;

public class MapProperty {
	private String name = "";
	private String description = "";
	private Object data = "";
	
	public MapProperty(String name, String description, Object data) {
		setName(name);
		setDescription(description);
		setData(data);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
