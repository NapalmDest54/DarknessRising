package com.github.darknessrising.maps.tools.gleed2d;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.github.darknessrising.gameobjects.GameObject;
import com.github.darknessrising.gameobjects.components.PhysicsComponent;
import com.github.darknessrising.maps.tools.gleed2d.objects.CircleMapObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.Gleed2DMapObject;
import com.github.darknessrising.maps.tools.gleed2d.objects.RectangleMapObject;
import com.github.darknessrising.maps.tools.gleed2d.properties.MapProperty;

public class Gleed2DMapLoader {
	
	private Element root;
	private XmlReader xml = new XmlReader();
	private Gleed2DMap map;
	private World world;
	public Gleed2DMapLoader() {
		
	}
	
	public Gleed2DMap load(String filename, World world) {
		try {
			map = new Gleed2DMap();
			this.world = world;
			root = xml.parse(Gdx.files.internal(filename));
			Array<Element> layers = root.getChildrenByName("Layers").get(0).getChildrenByName("Layer");
			System.out.println(layers.size);
			for (Element ele : layers) {
				MapLayer mapLayer = new MapLayer();
				map.addMapLayer(mapLayer);
				processLayer(ele, mapLayer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}

	private void processLayer(Element layer, MapLayer mapLayer) {
		mapLayer.setName(layer.getAttribute("Name", "null"));
		
		for (Element item : layer.getChildByName("Items").getChildrenByName("Item")) {
			String itemType = item.get("xsi:type", "null");
			if (itemType.equals("TextureItem")) {
				
				Tile tile = new Tile();
				tile.setName(item.get("Name", "null"));
				String texturePath = TextureHelper.internalPath(item.getChildByName("texture_filename").getText());
				tile.setId(texturePath.hashCode());
				mapLayer.addTile(tile);
				map.addTexture(texturePath);
				int xPos = Integer.parseInt(item.getChildByName("Position").getChildByName("X").getText());
				int yPos = -1 * Integer.parseInt(item.getChildByName("Position").getChildByName("Y").getText());
				tile.setPosition(xPos, yPos);
				
				tile.setScaleX(Float.parseFloat(item.getChildByName("Scale").getChildByName("X").getText()));
				tile.setScaleY(Float.parseFloat(item.getChildByName("Scale").getChildByName("Y").getText()));
				
				float rotation = Float.parseFloat(item.getChildByName("Rotation").getText());
				tile.setRotation(rotation);
				
				// Tinting
				float red = Integer.parseInt(item.getChildByName("TintColor").getChildByName("R").getText()) / 255f;
				float green = Integer.parseInt(item.getChildByName("TintColor").getChildByName("G").getText()) / 255f;
				float blue = Integer.parseInt(item.getChildByName("TintColor").getChildByName("B").getText()) / 255f;
				float alpha = Integer.parseInt(item.getChildByName("TintColor").getChildByName("A").getText()) / 255f;
				tile.setTint(red, green, blue, alpha);
				
				// Flipping
				tile.setFlipHorizontally(Boolean.parseBoolean(item.getChildByName("FlipHorizontally").getText()));
				tile.setFlipVertically(Boolean.parseBoolean(item.getChildByName("FlipVertically").getText()));
				
				processCustomProperties(tile.getProperties(), item);
				
				tile.prepareSprite(map.getTextureHelper());
			} else if (itemType.equals("RectangleItem")) {
				RectangleMapObject mapObject = new RectangleMapObject();
				mapLayer.addMapObject(mapObject);
				mapObject.setWidth(Integer.parseInt(item.getChildByName("Width").getText()));
				mapObject.setHeight(Integer.parseInt(item.getChildByName("Height").getText()));
				mapObject.setPosition(mapObject.getPosition().x, mapObject.getPosition().y - mapObject.getHeight());
				processMapObject(mapObject, item);
			} else if (itemType.equals("CircleItem")) {
				CircleMapObject mapObject = new CircleMapObject();
				mapLayer.addMapObject(mapObject);
				processMapObject(mapObject, item);
				mapObject.setRadius(Float.parseFloat(item.getChildByName("Radius").getText()));
			}
		}
	}
	
	private void processMapObject(Gleed2DMapObject mapObject, Element item) {
		mapObject.setName(item.get("Name", "null"));
		int xPos = Integer.parseInt(item.getChildByName("Position").getChildByName("X").getText());
		int yPos = -1 * Integer.parseInt(item.getChildByName("Position").getChildByName("Y").getText());
		mapObject.setPosition(xPos, yPos);
		processCustomProperties(mapObject.getMapProperties(), item);
		
		float red = Integer.parseInt(item.getChildByName("FillColor").getChildByName("R").getText()) / 255f;
		float green = Integer.parseInt(item.getChildByName("FillColor").getChildByName("G").getText()) / 255f;
		float blue = Integer.parseInt(item.getChildByName("FillColor").getChildByName("B").getText()) / 255f;
		float alpha = Integer.parseInt(item.getChildByName("FillColor").getChildByName("A").getText()) / 255f;
		mapObject.setFillColor(red, green, blue, alpha);
		
		Iterator<Object> properties = mapObject.getMapProperties().getValues();
		while (properties.hasNext()) {
			MapProperty property = (MapProperty) properties.next();
			if (property.getName().equalsIgnoreCase("box2d")) {
				GameObject obj = new GameObject();
				String des = property.getDescription();
				BodyDef bodyDef = new BodyDef();
				Shape shape = null;
				BodyType type = null;
				if (des.equalsIgnoreCase("dynamic")) {
					type = BodyType.DynamicBody;
				} else if (des.equalsIgnoreCase("kinetic")) {
					type = BodyType.KinematicBody;
				} else { // treat as static
					type = BodyType.StaticBody;
				}
				if (mapObject instanceof CircleMapObject) {
					shape = new CircleShape();
					shape.setRadius(((CircleMapObject) mapObject).getRadius()  * PhysicsComponent.PIXELS_TO_METERS);
				} else {
					shape = new PolygonShape();
					((PolygonShape) shape).setAsBox(((RectangleMapObject) mapObject).getWidth()  * PhysicsComponent.PIXELS_TO_METERS / 2f, ((RectangleMapObject) mapObject).getHeight() * PhysicsComponent.PIXELS_TO_METERS / 2f);
				}
				bodyDef.type = type;
				bodyDef.position.x = mapObject.getPosition().x * PhysicsComponent.PIXELS_TO_METERS;
				bodyDef.position.y = mapObject.getPosition().y * PhysicsComponent.PIXELS_TO_METERS;
				Body body = world.createBody(bodyDef);
				body.createFixture(shape, 1);
				
				PhysicsComponent physicsComp = new PhysicsComponent(obj, body);
				obj.placeComponent("PhysicsComp", physicsComp);
			}
		}
	}

	private void processCustomProperties(MapProperties properties, Element item) {
		for (Element property : item.getChildByName("CustomProperties").getChildrenByName("Property")) {
			String name = property.get("Name", "null");
			String description = property.get("Description", "null");
			Object data = null;
			String type = property.get("Type", "");
			if (type.equals("string")) {
				data = property.getChildByName("string").getText();
			} else if (type.equals("Item")) {
				data = property.getText();
			} else if (type.equals("bool")) {
				data = Boolean.parseBoolean( property.getChildByName("boolean").getText());
			} else if (type.equals("Vector2")) {
				float x = Float.parseFloat(property.getChildByName("Vector2").getChildByName("X").getText());
				float y = Float.parseFloat(property.getChildByName("Vector2").getChildByName("Y").getText());
				data = new Vector2(x, y);
			} else if (type.equals("Color")) {
				float r = Integer.parseInt(property.getChildByName("Color").getChildByName("R").getText()) / 255f;
				float g = Integer.parseInt(property.getChildByName("Color").getChildByName("G").getText()) / 255f;
				float b = Integer.parseInt(property.getChildByName("Color").getChildByName("B").getText()) / 255f;
				float a = Integer.parseInt(property.getChildByName("Color").getChildByName("A").getText()) / 255f;
				data = new Color(r, g, b, a);
			}
			MapProperty mapProperty = new MapProperty(name, description, data);
			properties.put(name, mapProperty);
		}
	}
}
