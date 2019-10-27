package de.whiletrue.processinggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.player.Camera;
import de.whiletrue.processinggame.player.PlayerController;
import de.whiletrue.processinggame.player.Settings;
import de.whiletrue.processinggame.rendering.Fonts;
import de.whiletrue.processinggame.rendering.Overlay;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.utils.KeyHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Game {
	
	private static Game instance;
	private PApplet window;
	
	private World world;
	
	private Camera camera;
	private EntityPlayer player;
	private PlayerController playercontroller;
	
	private KeyHandler keyhandler = new KeyHandler();
	
	private Settings settings = new Settings();
	private DefaultGui openGui = null;
	private Renderer renderer;
	private Overlay gameoverlay;
	
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
		
		//Creates the camera
		this.camera = new Camera();
		
		//Loads the fonts
		Fonts.init(this.window);
		
		//Creates the renderer
		this.renderer = new Renderer(this.window);
		
		//Creates the player object
		this.player = new EntityPlayer(this.camera);

		//Creates the player controller
		this.playercontroller = new PlayerController(this.keyhandler);
		
		//Creates the overlay
		this.gameoverlay = new Overlay();
		
		//Creates the world
		this.world = new World();
	}
	
	public void handleRender() {

		//Gets the mouse stuff
		int mx = this.window.mouseX,my = this.window.mouseY;
		boolean mp = this.window.mousePressed;
		
		//Handles the ticks
		this.onTick();
		
		//Draws a default background
		this.window.background(Color.gray.getRGB());
		
		//Renders the game
		this.renderer.push();
		{
			//Handles the camera
			this.camera.doRender(this.window);
			
			//Renders the world
			this.world.handleRender(mx,my,mp);
		}
		this.renderer.pop();
		
		//Renders the overlay and gui
		this.renderer.push();
		{
			//Renders the overlay
			this.gameoverlay.handleRender(mx, my, mp);
			
			//Checks if a gui is open
			if(!this.isGameRunning())
				//Draws the gui
				this.openGui.handleRender(mx, my, mp);
		}
		this.renderer.pop();
		
	}

	public void onTick() {
		//Checks if the game is running
		if(this.isGameRunning()) {
			
			//Handles the tick for the playercontroller
			this.playercontroller.handleTick();
			
			//Handles the tick for the world
			this.world.handleTick();
		}else {
			this.openGui.handleTick();
		}
	}
	
	public void handleKeyPressed(KeyEvent event) {
		
		//Handles the keyhandler
		this.keyhandler.pressed(event);
		
		//Handles the key pressing for the playercontroller
		this.playercontroller.handleKeyPressed(event,this.isGameRunning());
		
		//Prevents the application from exiting
		if(event.getKey()==PApplet.ESC)
			this.window.key=0;
	}
	
	public void handleKeyReleased(KeyEvent event) {
		//Handles the keyhandler
		this.keyhandler.released(event);
		
		//Handles the playercontroller
		this.playercontroller.handleKeyReleased(event,this.isGameRunning());
	}
	
	
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning())
			return;
		
		//Sends the event to the gui
		this.openGui.handleMouseClicked(event);
	}

	public void handleMouseDragged(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning())
			return;
		//Sends the event to the gui
		this.openGui.handleMouseDragged(event);
	}
	
	
	
	public void handleMousePressed(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning())
			return;
		//Sends the event to the gui
		this.openGui.handleMousePressed(event);
	}
	
	
	public void handleMouseReleased(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning())
			return;
		//Sends the event to the gui
		this.openGui.handleMouseReleased(event);
	}
	
	
	public void handleMouseMoved(MouseEvent event) {
		//Checks if the game is running
		if(this.isGameRunning()) {
			//Handles the mousemoved event on the world
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
	
	/**
	 * @return the settings
	 */
	public final Settings getSettings() {
		return this.settings;
	}

	/**
	 * @return the player
	 */
	public final EntityPlayer getPlayer() {
		return this.player;
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

	public Renderer getRenderer() {
		return this.renderer;
	}

	/**
	 * @return the world
	 */
	public final World getWorld() {
		return this.world;
	}
}
