package com.github.darknessrising.gameobjects;

import java.util.HashMap;
import java.util.UUID;

import com.github.darknessrising.gameobjects.components.Component;
import com.github.evms.eventmangement.EventManager;

public class GameObject {

	private String guid;
	private HashMap<String, Component> componentMap;
	
	public GameObject() {
		guid = UUID.randomUUID().toString();
		EventManager.getInstance().raiseEvent(EventManager.getInstance().getNewEvent("EVENT_GAMEOBJECT_CREATED", guid));
	}
	
	public String getGUID() {
		return guid;
	}
	
	public Component getComponent(String key) {
		return componentMap.get(key);
	}
	
	/**
	 * 
	 * @param value
	 * @param component
	 * @return True if component was place. False if component at key exists.
	 */
	public boolean placeComponent(String key, Component component) {
		if (componentMap.containsKey(key)) {
			return false;
		}
		return true;
	}
	
	public Component removeComponent(String key) {
		return componentMap.remove(key);
	}
}
