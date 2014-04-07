package com.github.darknessrising.maps.tools.gleed2d;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.github.darknessrising.maps.tools.gleed2d.objects.CircleMapObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.Gleed2DMapObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.RectangleMapObject;
import com.github.darknessrising.maps.tools.gleed2d.properties.MapProperty;

public class Gleed2dDebugRenderer {
	private ShapeRenderer shapeRenderer;
	public Gleed2dDebugRenderer() {
		shapeRenderer = new ShapeRenderer();
	}
	
	public void draw(SpriteBatch batch, Gleed2DMap map) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		
		for(MapLayer layer : map.getLayers()) {
			for (Gleed2DMapObject obj: layer.getMapObjects()) {
				Vector2 pos = obj.getPosition();
				Color fillColor = obj.getFillColor();
				shapeRenderer.setColor(fillColor);
				if (obj instanceof RectangleMapObject) {
					RectangleMapObject rectObj = (RectangleMapObject) obj;
					shapeRenderer.rect(pos.x, pos.y, rectObj.getWidth(), rectObj.getHeight());
				}
				if (obj instanceof CircleMapObject) {
					CircleMapObject cirObj = (CircleMapObject) obj;
					shapeRenderer.circle(pos.x, pos.y, cirObj.getRadius());
				}
			}
		}
		shapeRenderer.end();
	}
	
}
