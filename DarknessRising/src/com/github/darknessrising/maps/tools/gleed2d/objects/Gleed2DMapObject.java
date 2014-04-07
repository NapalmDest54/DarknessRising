package com.github.darknessrising.maps.tools.gleed2d.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.github.darknessrising.maps.tools.gleed2d.properties.MapProperty;

public class Gleed2DMapObject {

	private String name = "";
	private Vector2 position = new Vector2();
	private MapProperties mapProperties = new MapProperties();
	private Color fillColor = new Color();
	private float rotation;

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public MapProperties getMapProperties() {
		return mapProperties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFillColor(float red, float green, float blue, float alpha) {
		fillColor.r = red;
		fillColor.g = green;
		fillColor.b = blue;
		fillColor.a = alpha;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}


}
