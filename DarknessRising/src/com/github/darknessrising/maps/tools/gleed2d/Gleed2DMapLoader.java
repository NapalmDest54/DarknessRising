package com.github.darknessrising.maps.tools.gleed2d;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.github.darknessrising.maps.tools.gleed2d.properties.MapProperty;

public class Gleed2DMapLoader {
	
	private Element root;
	private XmlReader xml = new XmlReader();
	private Gleed2DMap map;
	
	public Gleed2DMapLoader() {
		
	}
	
	public Gleed2DMap load(String filename) {
		try {
			map = new Gleed2DMap();
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
				
			}
		}
	}

	private void processCustomProperties(MapProperties properties, Element item) {
		for (Element property : item.getChildrenByName("Property")) {
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
		}
	}
}
