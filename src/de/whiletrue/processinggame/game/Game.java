package de.whiletrue.processinggame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import de.whiletrue.processinggame.game.ingame.WorldLoader;
import de.whiletrue.processinggame.game.startmenu.StateStartMenu;
import de.whiletrue.processinggame.rendering.Fonts;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.settings.Settings;
import de.whiletrue.processinggame.utils.KeyHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Game {
	
	private static Game instance;
	private PApplet window;
	
	private KeyHandler keyhandler;
	
	private GameState state;
	
	private Settings gamesettings = new Settings();
	
	public Game(PApplet window) {
		instance = this;
		this.window = window;

		//Creates the keyhandler
		this.keyhandler = new KeyHandler();
		
		//Loads the settings
		this.loadSettings();
		
		//Gets the screen size
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		//Sets the size of the window relative to the screen size
		window.setSize((int) (size.width/1.4), (int) (size.height/1.4));
	}
	
	public void init() {
		//Sets the max frame rate
		this.window.frameRate(60);
		
		//Loads the fonts
		Fonts.init(this.window);
		
		//Creates the renderer
		new Renderer(this.window);
		
		//Creates the worldloader
		new WorldLoader();
		
		//Starts the startmenu
		this.changeState(new StateStartMenu());
	}
	
	/*
	 * Loads the settings
	 * */
	private void loadSettings() {
		//Sets the default values
		this.gamesettings.setDefault("showHitboxes", false);
		this.gamesettings.setDefault("key_dropitem", 70);
		this.gamesettings.setDefault("key_right", 68);
		this.gamesettings.setDefault("key_left", 65);
		this.gamesettings.setDefault("key_jump", 32);
		this.gamesettings.setDefault("key_attack", 87);
		this.gamesettings.setDefault("key_item", 16);
		this.gamesettings.setDefault("key_fly_up", 87);
		this.gamesettings.setDefault("key_fly_down", 83);
		
		//Loads the settings
		this.gamesettings.loadSettings();
	}
	
	public void handleRender() {

		//Gets the mouse stuff
		int mx = this.window.mouseX,my = this.window.mouseY;
		boolean mp = this.window.mousePressed;
		
		//Handles the ticks
		this.onTick();
		
		//Renders a default background
		this.window.background(Color.WHITE.getRGB());
		
		//Renders the current state
		this.state.handleRender(mx, my, mp);
	}

	public void onTick() {
		this.state.handleTick();
	}
	
	public void handleKeyPressed(KeyEvent event) {
		
		//Handles the keyhandler
		this.keyhandler.pressed(event);
		
		//Handles the key event on the state
		this.state.handleKeyPressed(event);
		
		//Prevents the application from exiting
		if(event.getKey()==PApplet.ESC)
			this.window.key=0;
	}
	
	public void handleKeyReleased(KeyEvent event) {
		//Handles the keyhandler
		this.keyhandler.released(event);
		
		//Handles the key event on the state
		this.state.handleKeyReleased(event);
	}
		
	public void handleMouseClicked(MouseEvent event) {
		this.state.handleMouseClicked(event);
	}

	public void handleMouseDragged(MouseEvent event) {
		this.state.handleMouseDragged(event);
	}
		
	public void handleMousePressed(MouseEvent event) {
		this.state.handleMousePressed(event);
	}
	
	public void handleMouseReleased(MouseEvent event) {
		this.state.handleMouseReleased(event);
	}
	
	public void handleMouseMoved(MouseEvent event) {
		this.state.handleMouseMoved(event);
	}
	
	/*
	 * Closes the game properly
	 * */
	public final void closeGame() {
		//Saves the settings
		this.gamesettings.saveSettings();
		//Exits the game
		System.exit(0);
	}
	
	public final void changeState(GameState state) {
		this.state = state;
		this.state.init();
	}
	
	/**
	 * @return the game instance
	 */
	public static Game getInstance() {
		return instance;
	}

	/**
	 * @return the gamesettings
	 */
	public final Settings getSettings() {
		return this.gamesettings;
	}

	/**
	 * @return the state
	 */
	public final GameState getState() {
		return this.state;
	}
}
