package com.github.darknessrising.gameobjects.components.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.github.darknessrising.gameobjects.GameObject;

public class SpineComponent extends RenderComponent {
	private Skeleton skeleton;
	private SkeletonRenderer renderer;
	private SkeletonRendererDebug debugRenderer;
	
	public SpineComponent(GameObject owner, String spinePath) {
		super(owner);
		renderer = new SkeletonRenderer();
		debugRenderer = new SkeletonRendererDebug();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(spinePath + ".atlas"));
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(spinePath + ".json"));

		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setX(100);
		skeleton.setY(100);
	}

	@Override
	public void render(OrthographicCamera camera) {
		if (skeleton != null) {
			//skeleton.updateWorldTransform();
			System.out.println(skeleton.getX());
			debugRenderer.getShapeRenderer().getProjectionMatrix().set(camera.combined);
			spriteBatch.getProjectionMatrix().set(camera.combined);
			spriteBatch.begin();
			renderer.draw(spriteBatch, skeleton);
			spriteBatch.end();
			debugRenderer.draw(skeleton);
		}
	}

}
