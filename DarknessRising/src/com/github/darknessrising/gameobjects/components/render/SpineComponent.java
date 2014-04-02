package com.github.darknessrising.gameobjects.components.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.github.darknessrising.DarknessRisingGame;
import com.github.darknessrising.gameobjects.GameObject;

public class SpineComponent extends RenderComponent {
	private Skeleton skeleton;
	private SkeletonRenderer renderer;
	private SkeletonRendererDebug debugRenderer;
	private PolygonSpriteBatch spriteBatchPoly = new PolygonSpriteBatch();
	private AnimationState state;
	Texture tex;
	public SpineComponent(GameObject owner, String spinePath) {
		super(owner);
		renderer = new SkeletonRenderer();
		debugRenderer = new SkeletonRendererDebug();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(spinePath + ".atlas"));
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(spinePath + ".json"));

		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		skeleton.setX(10);
		skeleton.setY(-100);
		tex = new Texture(Gdx.files.internal("data/sprites/player.png"));
		
		AnimationStateData stateData = new AnimationStateData(skeletonData);
		state = new AnimationState(stateData);
		state.setAnimation(0, "animation", true);
		
		debugRenderer.setBones(true);
		debugRenderer.setRegionAttachments(true);
	}

	@Override
	public void render(OrthographicCamera camera) {
		if (skeleton != null) {
			if (state != null) {
				state.update(Gdx.graphics.getDeltaTime());
				state.apply(skeleton);
			}
			
			skeleton.updateWorldTransform();
			spriteBatchPoly.getProjectionMatrix().set(camera.combined);
			spriteBatchPoly.begin();
			renderer.draw(spriteBatchPoly, skeleton);
			spriteBatchPoly.end();
			
			
			if (DarknessRisingGame.DEBUG) {
				debugRenderer.getShapeRenderer().getProjectionMatrix().set(camera.combined);
				debugRenderer.draw(skeleton);
			}
			
			
		}
	}

}
