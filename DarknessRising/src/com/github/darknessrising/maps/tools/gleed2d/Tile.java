package com.github.darknessrising.maps.tools.gleed2d;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private String name = "";

	private float rotation;
	private int id;
	private Vector2 position = new Vector2();
	private float scaleX = 1;
	private float scaleY = 1;
	private MapProperties properties = new MapProperties();
	private Sprite sprite = new Sprite();
	private Color colorTint = new Color(1, 1, 1, 1);
	private boolean flipVertically = false;
	private boolean flipHorizontally = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFlipVertically() {
		return flipVertically;
	}

	public void setFlipVertically(boolean flipVertically) {
		this.flipVertically = flipVertically;
	}

	public boolean isFlipHorizontally() {
		return flipHorizontally;
	}

	public void setFlipHorizontally(boolean flipHorizontally) {
		this.flipHorizontally = flipHorizontally;
	}

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

	public void setTint(float r, float g, float b, float a) {
		colorTint.set(r, g, b, a);
	}

	public Color getTint() {
		return colorTint;
	}

	public void prepareSprite(TextureHelper textureHelper) {
		Texture texture = textureHelper.getTexture(id);
		sprite = new Sprite(texture);
		sprite.setScale(scaleX, scaleY);
		sprite.setRotation(MathUtils.radiansToDegrees * rotation);
		sprite.setPosition(position.x - sprite.getWidth() / 2, position.y
				- sprite.getHeight() / 2);
		sprite.setColor(colorTint);
		sprite.flip(flipHorizontally, flipVertically);
		sprite.getTexture().setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
	}

	public void render(SpriteBatch spriteBatch, TextureHelper textureHelper) {
		sprite.draw(spriteBatch);
	}

	public void setPosition(int xPos, int yPos) {
		position.x = xPos;
		position.y = yPos;
	}

	public MapProperties getProperties() {
		return properties;
	}
}
