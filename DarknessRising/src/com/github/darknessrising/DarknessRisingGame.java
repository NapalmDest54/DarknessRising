package com.github.darknessrising;

import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.GameObjectFactory;
import com.github.darknessrising.gameobjects.components.PhysicsComponent;
import com.github.darknessrising.gameobjects.components.PlayerControlableComponent;
import com.github.darknessrising.gameobjects.components.render.SpineComponent;
import com.github.darknessrising.input.InputHelper;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMap;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMapLoader;
import com.github.evms.eventmangement.EventManager;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class DarknessRisingGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Gleed2DMap map;
	int movementspeed = 5;
	public static boolean DEBUG = true;
	GameObject player;
	private World world;
	Box2DDebugRenderer phyiscsDebugRenderer;
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
		world = new World(new Vector2(0, 0), true);
		player = GameObjectFactory.getSpinedCharacter("data/spines/player/exports/skeleton", world);
		player.placeComponent("ControlComp", new PlayerControlableComponent(player));
		phyiscsDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
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
		phyiscsDebugRenderer.render(world, camera.combined);
		
		EventManager.getInstance().raiseImmediateEvent(EventManager.getInstance().getNewEvent("EVENT_RENDER_CALL", camera));
		
		
		
		camera.zoom += ((InputHelper) Gdx.input.getInputProcessor()).getMouseScroll() / 25f;
		Vector2 pos = ((SpineComponent) player.getComponent("SpineComp")).getPosition();
		camera.position.x = pos.x;
		camera.position.y = pos.y;
		camera.update(true);
		EventManager.getInstance().raiseImmediateEvent(EventManager.getInstance().getNewEvent("EVENT_UPDATE_CALL", camera));
		EventManager.getInstance().tick();
		((InputHelper) Gdx.input.getInputProcessor()).tick();
		world.step(1/60f, 6, 2);
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
