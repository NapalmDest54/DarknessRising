package com.github.darknessrising.maps.tools.gleed2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	
	private float rotation;
	private int id;
	private Vector2 position = new Vector2();
	private float scaleX = 1;
	private float scaleY = 1;
	private Sprite sprite = new Sprite();
	
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation2) {
		this.rotation = rotation2;
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
	
	public void prepareSprite(TextureHelper textureHelper) {
		Texture texture = textureHelper.getTexture(id);
		sprite = new Sprite(texture);
		sprite.setScale(scaleX, scaleY);
		sprite.setRotation(MathUtils.radiansToDegrees * rotation);
		sprite.setPosition(position.x - sprite.getWidth() / 2 , position.y - sprite.getHeight() / 2);
	}
	
	public void render(SpriteBatch spriteBatch, TextureHelper textureHelper) {
		sprite.draw(spriteBatch);
	}
	
	public void setPosition(int xPos, int yPos) {
		position.x = xPos;
		position.y = yPos;
	}
	

}
