package com.github.darknessrising.maps.tools.gleed2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	
	private int rotation;
	private int id;
	private Vector2 position = new Vector2();
	private float scaleX = 1;
	private float scaleY = 1;
	
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public int getId() {
		return id;
	}
	public float getScaleX() {
		return scaleX;
	}
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void render(SpriteBatch spriteBatch, TextureHelper textureHelper) {
		Texture texture = textureHelper.getTexture(id);
		float sclWidth = texture.getWidth() * scaleX;
		float sclHeight = texture.getHeight() * scaleY;
		spriteBatch.draw(texture, position.x- sclWidth / 2, position.y - sclHeight / 2, sclWidth, sclHeight);
	}
	public void setPosition(int xPos, int yPos) {
		position.x = xPos;
		position.y = yPos;
	}
	

}
