package com.github.darknessrising.maps.tools.gleed2d;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.darknessrising.maps.tools.gleed2d.objects.Gleed2DMapObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapLayer {

	private String name = "null";
	private boolean isVisible = true;
	private HashMap<String, Gleed2DMapObject> mapObjects = new HashMap<String, Gleed2DMapObject>();
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private HashMap<String, String> properties = new HashMap<String, String>();
	private SpriteBatch spriteBatch = new SpriteBatch();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public HashMap<String, Gleed2DMapObject> getMapObjects() {
		return mapObjects;
	}
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void render(TextureHelper textureHelper, OrthographicCamera camera) {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Tile tile : tiles) {
			tile.render(spriteBatch, textureHelper);
		}
		
		spriteBatch.end();
	}
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
}
