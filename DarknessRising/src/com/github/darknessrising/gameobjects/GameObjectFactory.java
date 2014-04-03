package com.github.darknessrising.gameobjects;

import com.badlogic.gdx.physics.box2d.World;
import com.github.darknessrising.gameobjects.components.PhysicsComponent;
import com.github.darknessrising.gameobjects.components.render.SpineComponent;

public class GameObjectFactory {

	public static GameObject getSpinedCharacter(String path, World world) {
		GameObject gameObject = new GameObject();
		SpineComponent spineComp = new SpineComponent(gameObject, path, world);
		gameObject.placeComponent("SpineComp", spineComp);
		if (spineComp.getBoundingBody() != null) {
			System.out.println("adding physics");
			System.out.println(gameObject.placeComponent("PhysicsComp", new PhysicsComponent(
					gameObject, spineComp.getBoundingBody())));
		}

		return gameObject;
	}
}
