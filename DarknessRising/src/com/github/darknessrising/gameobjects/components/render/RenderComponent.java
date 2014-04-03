package com.github.darknessrising.gameobjects.components.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.components.Component;
import com.github.evms.eventmangement.Event;
import com.github.evms.eventmangement.EventHandler;
import com.github.evms.eventmangement.EventManager;

public abstract class RenderComponent extends Component implements EventHandler {
	protected SpriteBatch spriteBatch;
	protected int renderLevel = 0;
	public RenderComponent(GameObject owner) {
		super(owner);
		spriteBatch = new SpriteBatch();
		EventManager.getInstance().registerEvent("EVENT_RENDER_CALL", this);
	}
	
	public abstract void render(OrthographicCamera camera);

	@Override
	public void onEvent(Event event) {
		if (event.getType().equals("EVENT_RENDER_CALL")) {
			render((OrthographicCamera) event.getParams()[0]);
		}
	}

}
