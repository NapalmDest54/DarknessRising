package com.github.darknessrising.gameobjects.components;

import com.github.darknessrising.gameobjects.GameObject;

public abstract class Component {
	
	protected GameObject owner;
	
	public Component(GameObject owner) {
		this.owner = owner;
	}

}
