package com.github.darknessrising.maps.tools.gleed2d;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

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
				float green = Integer.parseInt(item.getChildByName("TintColor").getChildByName("B").getText()) / 255f;
				float blue = Integer.parseInt(item.getChildByName("TintColor").getChildByName("G").getText()) / 255f;
				float alpha = Integer.parseInt(item.getChildByName("TintColor").getChildByName("A").getText()) / 255f;
				tile.setTint(red, green, blue, alpha);
				
				
				tile.prepareSprite(map.getTextureHelper());
			} else if (itemType.equals("RectangleItem")) {
				
			}
		}
	}
}
