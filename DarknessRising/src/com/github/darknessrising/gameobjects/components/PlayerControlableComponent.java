package com.github.darknessrising.gameobjects.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.evms.eventmangement.Event;
import com.github.evms.eventmangement.EventHandler;
import com.github.evms.eventmangement.EventManager;

public class PlayerControlableComponent extends Component implements EventHandler {

	private EventManager eventManager;
	private Vector2 directionVector = new Vector2();
	public PlayerControlableComponent(GameObject owner) {
		super(owner);
		eventManager = EventManager.getInstance();
		eventManager.registerEvent("EVENT_GAME_INPUT", this);
		eventManager.registerEvent("EVENT_UPDATE_CALL", this);
	}
	
	private void update() {
		directionVector.x = 0;
		directionVector.y = 0;
		if (Gdx.input.isKeyPressed(Keys.W)) {
			directionVector.y = 1;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			directionVector.y = -1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			directionVector.x = -1;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			directionVector.x = 1;
		}
		
		if (directionVector.dst(Vector2.Zero) != 0) {
			eventManager.raiseEvent(eventManager.getNewEvent("EVENT_APPLY_VELOCITY_CENTER", owner, directionVector));
		}
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType().equals("EVENT_GAME_INPUT")) {
			
		} else if (event.getType().equals("EVENT_UPDATE_CALL")) {
			update();
		}
		
	}

}
