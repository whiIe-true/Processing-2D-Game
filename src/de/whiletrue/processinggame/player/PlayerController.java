package de.whiletrue.processinggame.player;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.userinterface.guis.GuiPause;
import de.whiletrue.processinggame.utils.Items;
import de.whiletrue.processinggame.utils.KeyHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class PlayerController {

	private EntityPlayer player;
	private Game game;
	private KeyHandler keyhandler;
	
	public PlayerController(KeyHandler keyhandler) {
		this.game = Game.getInstance();
		this.keyhandler = keyhandler;
		this.player = this.game.getPlayer();
	}
	
	/*
	 * Executes on every tick
	 * */
	public void handleTick() {
		
		//Checks if the key for shift is pressed and if the ring is currently hold
		if(this.keyhandler.keyPressed(/*Shift*/16)&&this.player.getItemHolding()==Items.ring_of_flying)
			//Lets the player fly
			this.player.getPhysics().setMotionY(-8);
		
		//Checks if the key for forward is pressed and if the player can move
		if(this.keyhandler.keyPressed(68/*Key D*/)){
			//Sets the skin direction
			this.player.getAnimations().setReverse(false);
			//Sets the motion
			this.player.getPhysics().addX(this.game.getSettings().speed);
		}
		
		//Checks if the key for backward is pressed and if the player can move
		if(this.keyhandler.keyPressed(65/*Key A*/)){
			//Sets the skin direction
			this.player.getAnimations().setReverse(true);
			//Sets the motion
			this.player.getPhysics().addX(-this.game.getSettings().speed);
		}
		
		//Checks if the key for jump is pressed, the player is onground and if the player can move
		if(this.keyhandler.keyPressed(32/*Key Spacebar*/)){
			this.player.jump();
		}
		
		//Checks if the key for attack is pressed and no attack is going and if the player can move
		if(this.keyhandler.keyPressed(87/*Key W*/)) {
			//Starts the attack
			this.player.attack();
		}
		
		//Drops the item
		if(this.keyhandler.keyPressed(70/*Key F*/)) {
			//Drops the current carrying item
			this.player.dropItem();
		}
		
		//Sets the skin idling or moving depending if the player moves or not
		this.player.getAnimations().setIdleAnimation(this.keyhandler.anyPressed(65,68)?"walk":"idle");
	}
	
	/*
	 * Handles whenever a key is pressed
	 * */
	public void handleKeyPressed(KeyEvent event,boolean gamerunning) {
		
		//Checks if the key is the esc key
		if(event.getKey() == PApplet.ESC) {
			//Checks if a gui is already open
			if(gamerunning)
				//Opens the gui
				this.game.openGui(new GuiPause());
			else
				//Closes the gui
				this.game.openGui(null);
		}
	}
	
	/*
	 * Handles whenever a key goes up
	 * */
	public void handleKeyReleased(KeyEvent event,boolean gamerunning) {
		
	}
	
}
