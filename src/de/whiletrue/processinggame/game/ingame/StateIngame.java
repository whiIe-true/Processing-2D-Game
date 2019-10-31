package de.whiletrue.processinggame.game.ingame;

import java.awt.Color;
import java.io.File;

import de.whiletrue.processinggame.game.GameState;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.player.Camera;
import de.whiletrue.processinggame.player.PlayerController;
import de.whiletrue.processinggame.rendering.Overlay;
import de.whiletrue.processinggame.rendering.Renderer;
import processing.event.KeyEvent;

public class StateIngame extends GameState{

	//Objects
	private World world;
	private Camera camera;
	private EntityPlayer player;
	private PlayerController playercontroller;
	private Overlay gameoverlay;
	
	//Instances
	private Renderer renderer;
	
	@Override
	public void init() {
		//Refrences the renderer
		this.renderer = Renderer.getInstance();
		
		//Creates the camera
		this.camera = new Camera();
		
		//Creates the player object
		this.player = new EntityPlayer(this.camera,0,0);
		
		//Creates the player controller
		this.playercontroller = new PlayerController();
		
		//Creates the overlay
		this.gameoverlay = new Overlay();
		
		//Creates the world
		this.world = WorldLoader.getInstance().loadWorld(new File("world.json"));
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Renders the default background
		this.renderer.window.background(Color.gray.getRGB());
		
		//Renders the game
		this.renderer.push();
		{
			//Handles the camera
			this.camera.doRender();
			
			//Renders the world
			this.world.handleRender(mouseX,mouseY,mousePressed);
		}
		this.renderer.pop();
		
		//Renders the overlay and gui
		this.renderer.push();
		{
			//Renders the overlay
			this.gameoverlay.handleRender(mouseX, mouseY, mousePressed);
			
			//Updates the gui
			super.handleRender(mouseX, mouseY, mousePressed);
		}
		this.renderer.pop();
		
	}

	@Override
	public void handleTick() {
		//Checks if the game is running
		if(!this.isGuiOpen()) {
			
			//Handles the tick for the playercontroller
			this.playercontroller.handleTick();
			
			//Handles the tick for the world
			this.world.handleTick();
		}else {
			//Updates the gui
			super.handleTick();
		}
	}

	@Override
	public void handleKeyPressed(KeyEvent event) {
		//Handles the key pressing for the playercontroller
		this.playercontroller.handleKeyPressed(event);
	}

	@Override
	public void handleKeyReleased(KeyEvent event) {
		//Handles the playercontroller
		this.playercontroller.handleKeyReleased(event);
	}

	/**
	 * @return the player
	 */
	public final EntityPlayer getPlayer() {
		return this.player;
	}

	/**
	 * @return the world
	 */
	public final World getWorld() {
		return this.world;
	}

}
