package com.github.darknessrising.gameobjects.components;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.darknessrising.DarknessRisingGame;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.input.InputHelper;
import com.github.evms.eventmangement.Event;
import com.github.evms.eventmangement.EventHandler;
import com.github.evms.eventmangement.EventManager;

public class RotateToMouseComponent extends Component implements EventHandler {
	private EventManager eventManager;
	private Vector3 lastMousePos = new Vector3();
	private Vector2 lastMousePos2 = new Vector2();
	private Vector2 lastPosition = new Vector2();
	public RotateToMouseComponent(GameObject owner) {
		super(owner);
		eventManager = EventManager.getInstance();
		eventManager.registerEvent("EVENT_UPDATE_CALL", this);
		eventManager.registerEvent("EVENT_GAMEOBJECT_PHYSICS_CHANGES", this);
		
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType().equals("EVENT_UPDATE_CALL")) {
			InputHelper input = ((InputHelper)Gdx.input.getInputProcessor());
			if (input.getMousePosX() != lastMousePos.x || input.getMousePosY() != lastMousePos.y) {
				lastMousePos.x = input.getMousePosX();
				lastMousePos.y = input.getMousePosY();
				DarknessRisingGame.camera.unproject(lastMousePos);
				lastMousePos2.x = lastMousePos.x;
				lastMousePos2.y = lastMousePos.y;
				DarknessRisingGame.camera.project(lastMousePos);
				eventManager.raiseEvent(eventManager.getNewEvent("EVENT_FORCE_CHANGE_ROTATION", owner, lastMousePos2.sub(lastPosition).angle() * MathUtils.degreesToRadians));
			} 
		} else if (event.getType().equals("EVENT_GAMEOBJECT_PHYSICS_CHANGES") && event.getParams()[0].equals(owner)) {
			Body body = (Body) event.getParams()[1];
			lastPosition.x = body.getPosition().x * PhysicsComponent.METERS_TO_PIXELS;
			lastPosition.y = body.getPosition().y * PhysicsComponent.METERS_TO_PIXELS;
		}
	}
	
	

}
