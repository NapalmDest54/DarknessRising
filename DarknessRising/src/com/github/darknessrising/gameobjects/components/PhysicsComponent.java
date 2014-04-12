package com.github.darknessrising.gameobjects.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.evms.eventmangement.Event;
import com.github.evms.eventmangement.EventHandler;
import com.github.evms.eventmangement.EventManager;

public class PhysicsComponent extends Component implements EventHandler {

	private Body body;
	private EventManager eventManager;
	private float prevOrientation;
	private Vector2 prevPosition;
	
	public static final float PIXELS_TO_METERS = 1/100f;
	public static final float METERS_TO_PIXELS = 100f;
	
	public PhysicsComponent(GameObject owner, Body body) {
		super(owner);
		this.body = body;
		eventManager = EventManager.getInstance();
		prevPosition = new Vector2(body.getPosition());
		prevOrientation = body.getAngle();
		eventManager.raiseEvent(eventManager.getNewEvent("EVENT_GAMEOBJECT_PHYSICS_CHANGES", owner, body));
		eventManager.registerEvent("EVENT_UPDATE_CALL", this);
		eventManager.registerEvent("EVENT_APPLY_VELOCITY_CENTER", this);
		eventManager.registerEvent("EVENT_FORCE_CHANGE_ROTATION", this);
		
		body.setLinearVelocity(0, 0);
		body.setLinearDamping(0.2f);
		
	}
	
	private void update() {
		if (prevPosition.dst2(body.getPosition()) != 0 || prevOrientation != body.getAngle()) {
			prevOrientation = body.getAngle();
			prevPosition.x = body.getPosition().x;
			prevPosition.y = body.getPosition().y;
			eventManager.raiseEvent(eventManager.getNewEvent("EVENT_GAMEOBJECT_PHYSICS_CHANGES", owner, body));
		}
	}
	
	@Override
	public void onEvent(Event event) {
		if (event.getType().equals("EVENT_UPDATE_CALL")) {
			update();
		} else if (event.getType().equals("EVENT_APPLY_VELOCITY_CENTER")) {
			if (event.getParams()[0].equals(owner)) {
				body.setLinearVelocity(((Vector2)event.getParams()[1]).x * 2, ((Vector2)event.getParams()[1]).y * 2);
			}
		} else if (event.getType().equals("EVENT_FORCE_CHANGE_ROTATION")) {
			if (event.getParams()[0].equals(owner)) {
				System.out.println("happening: " + (Float) event.getParams()[1] * MathUtils.radiansToDegrees);
				body.setTransform(body.getPosition(), (Float) event.getParams()[1]);
			}
		}
		
	}

	public Body getBody() {
		return body;
	}

}
