package com.github.darknessrising.gameobjects.components.render;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.github.darknessrising.DarknessRisingGame;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.components.PhysicsComponent;

public class SpineComponent extends RenderComponent {
	private Skeleton skeleton;
	private SkeletonRenderer renderer;
	private SkeletonRendererDebug debugRenderer;
	private PolygonSpriteBatch spriteBatchPoly = new PolygonSpriteBatch();
	private AnimationState state;
	private Body boundingBody;
	Texture tex;
	
	public SpineComponent(GameObject owner, String spinePath, World world) {
		super(owner);
		renderer = new SkeletonRenderer();
		debugRenderer = new SkeletonRendererDebug();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(spinePath + ".atlas"));
		SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(spinePath + ".json"));

		skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
		
		tex = new Texture(Gdx.files.internal("data/sprites/player.png"));
		
		AnimationStateData stateData = new AnimationStateData(skeletonData);
		state = new AnimationState(stateData);
		state.setAnimation(0, "animation", true);
		
		debugRenderer.setBones(true);
		debugRenderer.setRegionAttachments(true);
		for (Slot slot : skeleton.getSlots()) {
			Attachment attachment = slot.getAttachment();
			if (attachment instanceof BoundingBoxAttachment && attachment.getName().equals("bounding-body")) {
				BoundingBoxAttachment boundBox = (BoundingBoxAttachment) attachment;
				BodyDef bd = new BodyDef();
				bd.position.x = 0;
				bd.position.y = 0;
				PolygonShape shape = new PolygonShape();
				float[] vertices = boundBox.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					vertices[i] = vertices[i] * PhysicsComponent.PIXELS_TO_METERS;
				}
				shape.set(vertices);
				bd.type = BodyType.DynamicBody;
				boundingBody = world.createBody(bd);
				Fixture fix = boundingBody.createFixture(shape, 1); 
			}
		}
	}
	
	public Body getBoundingBody() {
		return boundingBody;
	}
	
	public Vector2 getPosition() {
		return (new Vector2(boundingBody.getPosition().x, boundingBody.getPosition().y)).scl(PhysicsComponent.METERS_TO_PIXELS);
	}

	@Override
	public void render(OrthographicCamera camera) {
		if (skeleton != null) {
			if (state != null) {
				state.update(Gdx.graphics.getDeltaTime());
				state.apply(skeleton);
			}
			skeleton.setX(boundingBody.getPosition().x * PhysicsComponent.METERS_TO_PIXELS);
			skeleton.setY(boundingBody.getPosition().y * PhysicsComponent.METERS_TO_PIXELS);
			skeleton.getRootBone().setRotation(boundingBody.getAngle() * MathUtils.radiansToDegrees + 90f);
			skeleton.updateWorldTransform();
			Matrix4 combined = new Matrix4(camera.combined);
			
			spriteBatchPoly.getProjectionMatrix().set(camera.combined);
			
			spriteBatchPoly.begin();
			renderer.draw(spriteBatchPoly, skeleton);
			spriteBatchPoly.end();
			
			
			if (DarknessRisingGame.DEBUG) {
				debugRenderer.getShapeRenderer().getProjectionMatrix().set(camera.projection);
				debugRenderer.draw(skeleton);
			}
			
			
		}
	}

}
