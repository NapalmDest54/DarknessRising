package com.github.darknessrising.maps.tools.gleed2d.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.darknessrising.maps.tools.gleed2d.properties.MapProperty;

public class Gleed2DMapObject {

	private Vector2 position = new Vector2();
	private LinkedList<MapProperty> mapProperties = new LinkedList<MapProperty>();
	private Color fillColor = new Color();
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color color) {
		this.fillColor = color;
	}
	public LinkedList<MapProperty> getMapProperties() {
		return mapProperties;
	}
	
	
}
