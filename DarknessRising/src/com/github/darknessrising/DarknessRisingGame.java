package com.github.darknessrising;

import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.GameObjectFactory;
import com.github.darknessrising.gameobjects.components.PhysicsComponent;
import com.github.darknessrising.gameobjects.components.PlayerControlableComponent;
import com.github.darknessrising.gameobjects.components.RotateToMouseComponent;
import com.github.darknessrising.gameobjects.components.render.SpineComponent;
import com.github.darknessrising.input.InputHelper;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMap;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2DMapLoader;
import com.github.darknessrising.maps.tools.gleed2d.Gleed2dDebugRenderer;
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
	public static OrthographicCamera camera;
	public static OrthographicCamera debugCamera;
	private SpriteBatch batch;
	private Gleed2DMap map;
	int movementspeed = 5;
	public static boolean DEBUG = true;
	GameObject player;
	private World world;
	Box2DDebugRenderer phyiscsDebugRenderer;
	Gleed2dDebugRenderer debugMapRenderer;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		world = new World(new Vector2(0, 0), true);
		camera = new OrthographicCamera(w, h);
		debugCamera = new OrthographicCamera(w, h);
		batch = new SpriteBatch();

		Gleed2DMapLoader loader = new Gleed2DMapLoader();
		map = loader.load("maps/test3.gleed", world);
		
		Gdx.input.setInputProcessor(new InputHelper());
		
		player = GameObjectFactory.getSpinedCharacter("data/spines/player/exports/skeleton", world);
		player.placeComponent("ControlComp", new PlayerControlableComponent(player));
		player.placeComponent("FollowMouse", new RotateToMouseComponent(player));
		phyiscsDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
		debugMapRenderer = new Gleed2dDebugRenderer();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		map.render(camera);
		
		
		EventManager.getInstance().raiseImmediateEvent(EventManager.getInstance().getNewEvent("EVENT_RENDER_CALL", camera));
		debugMapRenderer.draw(batch, map);
		if (DEBUG) {
			debugCamera.zoom = camera.zoom * 1/100f;
			debugCamera.position.x = camera.position.x * 1/100f;
			debugCamera.position.y = camera.position.y * 1/100f;
			debugCamera.update(true);
			phyiscsDebugRenderer.render(world, debugCamera.combined);
		}
		
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
