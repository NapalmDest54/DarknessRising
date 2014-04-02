package com.github.darknessrising;

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
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		//camera.translate(400, 400);
		batch = new SpriteBatch();

		texture = new Texture(
				Gdx.files.internal("data/textures/grass01_256.jpg"));

		Gleed2DMapLoader loader = new Gleed2DMapLoader();
		map = loader.load("maps/test2.xml");
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

		if (Gdx.input.isKeyPressed(Keys.W)) {
			camera.translate(0, movementspeed);
			camera.update(true);
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			camera.translate(0, -movementspeed);
			camera.update(true);
		} 
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			camera.translate(-movementspeed, 0);
			camera.update(true);
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			camera.translate(movementspeed, 0);
			camera.update(true);
		}
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
