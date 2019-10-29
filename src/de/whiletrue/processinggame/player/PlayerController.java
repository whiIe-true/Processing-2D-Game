package de.whiletrue.processinggame.player;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.entitys.EntityFireball;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.userinterface.guis.GuiDeathscreen;
import de.whiletrue.processinggame.userinterface.guis.GuiPause;
import de.whiletrue.processinggame.utils.Item;
import de.whiletrue.processinggame.utils.Items;
import de.whiletrue.processinggame.utils.KeyHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class PlayerController {

	private EntityPlayer player;
	private Game game;
	private KeyHandler keyhandler;
	
	private int fireballTicks;
	
	public PlayerController(KeyHandler keyhandler) {
		this.game = Game.getInstance();
		this.keyhandler = keyhandler;
		this.player = this.game.getPlayer();
	}
	
	/*
	 * Executes on every tick
	 * */
	public void handleTick() {
		//Updates all ticks
		this.updateTicks();
		
		//Handles the key inputs
		this.handleKeyInputs();
		
		//Handles if the player is dead
		if(this.player.isDead())
			this.game.openGui(new GuiDeathscreen());
	}
	
	private void handleKeyInputs() {
		//Checks if the key for shift is pressed and if the ring is currently hold
		if(this.keyhandler.keyPressed(/*Shift*/16))
			//Handles the itemused
			this.handleUseItem(this.player.getItemHolding());
		
		//Checks if the key for forward is pressed and if the player can move
		if(this.keyhandler.keyPressed(68/*Key D*/)){
			//Sets the skin direction
			this.player.getAnimations().setReverse(false);
			//Sets the motion
			this.player.getPhysics().addX(this.player.getStats().getSpeed());
		}
		
		//Checks if the key for backward is pressed and if the player can move
		if(this.keyhandler.keyPressed(65/*Key A*/)){
			//Sets the skin direction
			this.player.getAnimations().setReverse(true);
			//Sets the motion
			this.player.getPhysics().addX(-this.player.getStats().getSpeed());
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
			
			//Checks if the gui is closeable
			else if(this.game.getOpenGui().isCloseable())
					//Closes the gui
					this.game.openGui(null);
		}
	}
	
	/*
	 * Handles whenever a key goes up
	 * */
	public void handleKeyReleased(KeyEvent event,boolean gamerunning) {}
	
	/*
	 * Handles everytime the useitem key is pressed
	 * */
	public void handleUseItem(Item item) {
		
		//Checks if the item exitsts
		if(item==null)
			return;
		
		//Heal potion
		if(item==Items.heal_potion) {
			//Checks if the player still has full hearts
			if(this.player.getHealthLeft()>=1)
				return;
			//Heals the player for 20 hearts
			this.player.heal(20);
			//Removes the item
			this.player.setItemHolding(null);
		}
		
		if(item==Items.fireball_wand) {
			//Checks if the fireballticks are done
			if(this.fireballTicks>0)
				return;
			//Resets the ticks
			this.fireballTicks=40;
			//Spawn the entity
			EntityFireball spawn = new EntityFireball(this.player.getPhysics().getX(),this.player.getPhysics().getY(),this.player.getAnimations().isReverse()?-2:2);
			this.game.getWorld().spawn(spawn);
		}
	}
	
	/*
	 * Updates all ticks
	 * */
	private void updateTicks() {
		if(this.fireballTicks>0)
			this.fireballTicks--;
	}
	
}
