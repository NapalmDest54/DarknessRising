package com.github.darknessrising;

import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.components.render.SpineComponent;
import com.github.darknessrising.input.InputHelper;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMap;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMapLoader;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DarknessRisingGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Gleed2DMap map;
	int movementspeed = 5;
	public static boolean DEBUG = true;
	SpineComponent spineTest;
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();

		texture = new Texture(
				Gdx.files.internal("data/textures/grass01_256.jpg"));

		Gleed2DMapLoader loader = new Gleed2DMapLoader();
		map = loader.load("maps/test3.xml");
		
		Gdx.input.setInputProcessor(new InputHelper());
		
		spineTest = new SpineComponent(new GameObject(), "data/spines/player/exports/skeleton");
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		map.render(camera);
		spineTest.render(camera);
		if (Gdx.input.isKeyPressed(Keys.W)) {
			camera.translate(0, movementspeed);
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			camera.translate(0, -movementspeed);
		} 
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			camera.translate(-movementspeed, 0);
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			camera.translate(movementspeed, 0);
		}
		
		camera.zoom += ((InputHelper) Gdx.input.getInputProcessor()).getMouseScroll() / 25f;
		camera.update(true);
		((InputHelper) Gdx.input.getInputProcessor()).tick();
	}

	@Override
	public void resize(int width, int height) {
		//camera.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
