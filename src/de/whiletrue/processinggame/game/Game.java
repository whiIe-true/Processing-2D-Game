package de.whiletrue.processinggame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.PSObject;
import de.whiletrue.processinggame.objects.psobject.PSSlime;
import de.whiletrue.processinggame.objects.psobject.PSWall;
import de.whiletrue.processinggame.rendering.Fonts;
import de.whiletrue.processinggame.rendering.Overlay;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.guis.GuiPause;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Game {
	
	private static Game instance;
	private PApplet window;
	
	private KeyHandler keyhandler = new KeyHandler();
	private Settings settings = new Settings();
	private DefaultGui openGui = null;
	private Renderer renderer;
	private Overlay gameoverlay;
	
	private Player player;
	private List<PSObject> objects = new ArrayList<>();
	
	public Game(PApplet window) {
		instance = this;
		this.window = window;
		
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
		this.renderer = new Renderer(this.window);

		//Creates the overlay
		this.gameoverlay = new Overlay(this.renderer);
		
		//Creates the player object
		this.player = new Player(this,this.renderer,this.keyhandler);
		
		//Adds the objects
		this.objects.add(new PSWall(this,this.renderer, 100, 400, 300));
		this.objects.add(new PSWall(this,this.renderer, 0, this.window.height-10, this.window.width));
		
		//Adds the entitys
		this.objects.add(new PSSlime(this,this.renderer, 200, 380));
	}
	
	public void handleRender() {
		//Gets the mouse stuff
		int mx = this.window.mouseX,my = this.window.mouseY;
		boolean mp = this.window.mousePressed;
		
		//Handles the ticks
		this.onTick();
		
		//Draws a default background
		this.window.background(Color.gray.getRGB());
		
		//Draws the objects
		this.objects.forEach(i->i.handleRender(mx, my, mp));
		
		//Draws the player
		this.player.handleRender(mx, my, mp);
	
		//Renders the overlay
		this.gameoverlay.handleRender(mx, my, mp);
		
		//Checks if a gui is open
		if(!this.isGameRunning())
			//Draws the gui
			this.openGui.handleRender(mx, my, mp);
	}

	public void onTick() {
		//Removes dead entitys
		this.objects = this.objects.stream().filter(i->!(i instanceof PSEntity)||!((PSEntity)i).isDead()).collect(Collectors.toList());
		
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Handles the tick for the player
			this.player.handleTick();
			
			//Handles the tick for the objects
			this.objects.forEach(i->i.handleTick());
		}else {
			this.openGui.handleTick();
		}
	}
	
	public void handleKeyPressed(KeyEvent event) {
		//Handles the keyhandler
		this.keyhandler.pressed(event);
		
		//Checks if the key is the esc key
		if(event.getKey() == PApplet.ESC) {
			//Checks if the gui is already open
			if(this.isGameRunning())
				//Opens the gui
				this.openGui(new GuiPause(this,this.renderer));
			else
				//Closes the gui
				this.openGui(null);
			//Prevents the application from exiting
			this.window.key = 0;
		}
	}
	
	public void handleKeyReleased(KeyEvent event) {
		this.keyhandler.released(event);
	}
	
	
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Sends the player the mouse click
			this.player.handleMouseClicked(event);
			//Sends the objects the mouse click
			this.objects.forEach(i->i.handleMouseClicked(event));
		}else
			//Sends the event to the gui
			this.openGui.handleMouseClicked(event);
	}

	
	public void handleMouseDragged(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Sends the player the mouse click
			this.player.handleMouseDragged(event);
			//Sends the objects the mouse click
			this.objects.forEach(i->i.handleMouseDragged(event));
		}else
			//Sends the event to the gui
			this.openGui.handleMouseDragged(event);
	}
	
	
	
	public void handleMousePressed(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Sends the player the mouse click
			this.player.handleMousePressed(event);
			//Sends the objects the mouse click
			this.objects.forEach(i->i.handleMousePressed(event));
		}else
			//Sends the event to the gui
			this.openGui.handleMousePressed(event);
	}
	
	
	public void handleMouseReleased(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Sends the player the mouse click
			this.player.handleMouseReleased(event);
			//Sends the objects the mouse click
			this.objects.forEach(i->i.handleMouseReleased(event));
		}else
			//Sends the event to the gui
			this.openGui.handleMouseReleased(event);
	}
	
	
	public void handleMouseMoved(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Sends the player the mouse click
			this.player.handleMouseMoved(event);
			//Sends the objects the mouse click
			this.objects.forEach(i->i.handleMouseMoved(event));
		}else
			//Sends the event to the gui
			this.openGui.handleMouseMoved(event);
	}
	
	
	/*
	 * Opens a gui
	 * */
	public final void openGui(DefaultGui gui) {
		//Checks if gui should close
		if(gui==null) {
			this.openGui=null;
			return;
		}
		//Prepares the gui
		gui.init();
		//Opens the gui
		this.openGui = gui;
	}
	
	/*
	 * Returns if the killing/deleteing of the object was successfull
	 * */
	public final boolean removeObject(PSObject obj) {
		//Checks if that object is loaded
		boolean success = this.objects.contains(obj);
		//Removes the object
		this.objects.remove(obj);
		return success;
	}
	
	/*
	 * Adds a object to the world
	 * */
	public final void addObject(PSObject obj) {
		this.objects.add(obj);
	}
	
	/**
	 * @return the settings
	 */
	public final Settings getSettings() {
		return this.settings;
	}

	/**
	 * @return the player
	 */
	public final Player getPlayer() {
		return this.player;
	}

	/**
	 * @return the objects
	 */
	public final List<PSObject> getObjects() {
		return this.objects;
	}
	
	/**
	 * @return if the game is running
	 */
	public final boolean isGameRunning() {
		return this.openGui==null;
	}
	
	/**
	 * @return the window width
	 */
	public final int getWidth() {
		return this.window.width;
	}
	
	/**
	 * @return the window height
	 */
	public final int getHeight() {
		return this.window.height;
	}
	
	/**
	 * @return the game instance
	 */
	public static Game getInstance() {
		return instance;
	}
}
