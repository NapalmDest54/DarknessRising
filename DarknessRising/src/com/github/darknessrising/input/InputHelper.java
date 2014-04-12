package com.github.darknessrising.input;

import com.badlogic.gdx.InputProcessor;

public class InputHelper implements InputProcessor {

	private int mouseScroll = 0;
	private int mousePosX;
	private int mousePosY;
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosX = screenX;
		mousePosY = screenY;
		return false;
	}

	public int getMousePosX() {
		return mousePosX;
	}

	public int getMousePosY() {
		return mousePosY;
	}

	@Override
	public boolean scrolled(int amount) {
		synchronized (this) {
			mouseScroll = amount;
		}
		
		return false;
	}
	
	public int getMouseScroll() {
		synchronized (this) {
			return mouseScroll;
		}
		
	}

	public void tick() {
		synchronized (this) {
			mouseScroll = 0;
		}
	}

}
