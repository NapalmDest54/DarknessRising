package com.github.darknessrising;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DarknessRising";
		cfg.width = 800;
		cfg.height = 600;
		cfg.samples = 4;
		cfg.vSyncEnabled = false;
		cfg.foregroundFPS = 800;
		new LwjglApplication(new DarknessRisingGame(), cfg);
	}
}
