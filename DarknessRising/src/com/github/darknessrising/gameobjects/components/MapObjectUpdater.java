package com.github.darknessrising.gameobjects.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.Gleed2DMapObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.RectangleMapObject;
import com.github.evms.eventmangement.Event;
import com.github.evms.eventmangement.EventHandler;
import com.github.evms.eventmangement.EventManager;

public class MapObjectUpdater extends Component implements EventHandler {
	private Gleed2DMapObject mapObject;
	
	public MapObjectUpdater(GameObject owner, Gleed2DMapObject mapObject) {
		super(owner);
		this.mapObject = mapObject;
		EventManager.getInstance().registerEvent("EVENT_GAMEOBJECT_PHYSICS_CHANGES", this);
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType().equals("EVENT_GAMEOBJECT_PHYSICS_CHANGES")) {
			if (event.getParams()[0].equals(owner)) {
				Body body = (Body) event.getParams()[1];
				mapObject.getPosition().x = body.getPosition().x * PhysicsComponent.METERS_TO_PIXELS;
				mapObject.getPosition().y = body.getPosition().y * PhysicsComponent.METERS_TO_PIXELS;
				if (mapObject instanceof RectangleMapObject) {
					RectangleMapObject obj = (RectangleMapObject) mapObject;
					obj.getPosition().x -= obj.getWidth() / 2f;
					obj.getPosition().y += obj.getHeight() / 2f;
				}
				mapObject.setRotation(body.getAngle());
			}
		}
	}

}
