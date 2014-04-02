package com.github.darknessrising.maps.tools.gleed2d;

import java.util.HashMap;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class TextureHelper implements Disposable {

	private HashMap<Integer, Texture> textureMap = new HashMap<Integer, Texture>();
	
	public static String internalPath(String path) {
		if (path.contains("assets")) {
			return path.substring(path.indexOf("assets\\") + 7, path.length());
		}
		
		return path;
	}
	
	public void loadAndAdd(int key, String texture) {
		
		textureMap.put(key, new Texture(Gdx.files.internal(texture)));
	}

	@Override
	public void dispose() {
		for (Texture tex : textureMap.values()) {
			tex.dispose();
		}
	}

	public Texture getTexture(int id) {
		// TODO Auto-generated method stub
		return textureMap.get(id);
	}
	
}
