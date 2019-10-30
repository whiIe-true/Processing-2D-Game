package de.whiletrue.processinggame.utils;

import java.util.Set;
import java.util.TreeSet;

import de.whiletrue.processinggame.Game;
import processing.event.KeyEvent;

public class KeyHandler {

	private Set<Integer> pressedKeys = new TreeSet<Integer>();
	private Game game;
	
	public KeyHandler() {
		this.game = Game.getInstance();
	}
	
	/*
	 * Executes whenever a key is pressed
	 * */
	public void pressed(KeyEvent event) {
		this.pressedKeys.add(event.getKeyCode());
	}
	
	/*
	 * Executes whenever a key is released
	 * */
	public void released(KeyEvent event) {
		this.pressedKeys.remove(event.getKeyCode());
	}
	
	/*
	 * Returns if the given key is pressed
	 * */
	public boolean keyPressed(int code) {
		return this.pressedKeys.contains(code);
	}
	
	/*
	 * Returns if the given key is pressed
	 * */
	public boolean keyPressedSetting(String name) {
		return this.pressedKeys.contains(this.game.getSettings().getInt("key_"+name));
	}
	
	/*
	 * Returns if any of the given keys are pressed
	 * */
	public boolean anyPressed(int... keys) {
		//Iterates over all given keys
		for(int k : keys)
			//Checks if the pressed keys contain the given key
			if(this.pressedKeys.contains(k))
				return true;
		return false;
	}
	
	/*
	 * Returns if any of the given keys are pressed
	 * */
	public boolean anyPressedSettings(String... names) {
		//Iterates over all given keys
		for(String k : names)
			//Checks if the pressed keys contain the given key
			if(this.pressedKeys.contains(this.game.getSettings().getInt("key_"+k)))
				return true;
		return false;
	}
	
}
