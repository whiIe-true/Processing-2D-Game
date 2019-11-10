package de.whiletrue.processinggame.game.worldbuilder;

import java.awt.Color;
import java.io.File;

import de.whiletrue.processinggame.game.GameState;
import de.whiletrue.processinggame.game.ingame.WorldLoader;
import de.whiletrue.processinggame.player.Camera;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.KeyHandler;

public class StateWorldbuilder extends GameState{

	private Renderer renderer;
	private KeyHandler keyhandler;
	
	private WorldbuilderWorld world;
	private Camera camera = new Camera();
	
	@Override
	public void init() {
		//References the renderer
		this.renderer = Renderer.getInstance();
		
		//Loads the world
		this.world = new WorldbuilderWorld(WorldLoader.getInstance().loadWorld(new File("world.json")));
	}

	@Override
	public void handleTick() {
		
		//Updates the camera position when the given keys are pessed
		if(this.keyhandler.keyPressedSetting("key_fly_up"))
			this.camera.addUpdate(0, -2);
		if(this.keyhandler.keyPressedSetting("key_fly_down"))
			this.camera.addUpdate(0, 2);
		if(this.keyhandler.keyPressedSetting("key_left"))
			this.camera.addUpdate(-2, 0);
		if(this.keyhandler.keyPressedSetting("key_right"))
			this.camera.addUpdate(2, 0);
		
		super.handleTick();
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
		
	}
	
	
}
