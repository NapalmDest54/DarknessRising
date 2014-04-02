package com.github.darknessrising.maps.tools.gleed2d;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gleed2DMap {
	private ArrayList<MapLayer> mapLayers = new ArrayList<MapLayer>();
	private TextureHelper textureHelper = new TextureHelper();
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	public void addMapLayer(MapLayer maplayer) {
		mapLayers.add(maplayer);
	}
	
	public void addTexture(String path) {
		textureHelper.loadAndAdd(path.hashCode(), path);
	}
	
	public void render(OrthographicCamera camera) {
		for (MapLayer layer : mapLayers) {
			layer.render(textureHelper, camera);
		}
	}
}
