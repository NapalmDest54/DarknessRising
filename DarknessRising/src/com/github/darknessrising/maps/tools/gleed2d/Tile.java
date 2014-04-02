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
	public void setId(int id) {
		this.id = id;
	}
	public void render(SpriteBatch spriteBatch, TextureHelper textureHelper) {
		Texture texture = textureHelper.getTexture(id);
		
		spriteBatch.draw(texture, position.x- texture.getWidth() / 2, position.y - texture.getHeight() / 2, texture.getWidth() * scaleX, texture.getHeight() * scaleY);
	}
	public void setPosition(int xPos, int yPos) {
		position.x = xPos;
		position.y = yPos;
	}
	

}
