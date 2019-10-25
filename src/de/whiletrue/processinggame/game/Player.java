package de.whiletrue.processinggame.game;

import java.util.Optional;

import de.whiletrue.processinggame.items.EntityItem;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;

public class Player extends PSEntityLiving{

	private KeyHandler keyhandler;
	
	private int swingticks;
	
	private EntityItem carryingItem;
	
	public Player(Game game,Renderer renderer,KeyHandler keyhandler) {
		super(game,renderer);
		
		this.keyhandler = keyhandler;

		//Loades the hitbox
		this.hitbox = new Hitbox(25, 30, 2);
		
		//Loads the physics
		this.physics.init(hitbox,game.getWidth()/2, game.getHeight()/2,.2,.2);
		
		//Loads the skin
		this.animations.init(renderer,"idle",10);
		this.animations.loadAnimations("idle", "rsc/player/idle.png");
		this.animations.loadAnimations("walk", "rsc/player/walk.png");
		this.animations.loadAnimations("attack", "rsc/player/attack.png");
		this.animations.start();
	}

	@Override
	public void handleTick() {
		
		//Updates the item
		this.updateItem();
		
		//Updates the attack
		this.updateAttack();
		
		//Updates key handling
		this.updateKeyPresses();
		
		//Updates the size propery from the settings
		this.hitbox.setScale(this.game.getSettings().size);
		
		//Sets the skin idling or moving depending if the player moves or not
		this.animations.setIdleAnimation(this.keyhandler.anyPressed(65,68)?"walk":"idle");
		
		//Calls the methods callback function
		super.handleTick();
	}

	/*
	 * Handles all key pressing stuff
	 * */
	public void updateKeyPresses() {
		//Checks if the key for forward is pressed and if the player can move
		if(this.keyhandler.keyPressed(68/*Key D*/)){
			//Sets the skin direction
			this.animations.setReverse(false);
			//Sets the motion
			this.physics.addX(this.game.getSettings().speed);
		}
		
		//Checks if the key for backward is pressed and if the player can move
		if(this.keyhandler.keyPressed(65/*Key A*/)){
			//Sets the skin direction
			this.animations.setReverse(true);
			//Sets the motion
			this.physics.addX(-this.game.getSettings().speed);
		}
		
		//Checks if the key for jump is pressed, the player is onground and if the player can move
		if(this.keyhandler.keyPressed(32/*Key Spacebar*/) && this.physics.isOnground()){
			//Sets the vertical motion
			this.physics.pushY(-this.game.getSettings().jumpHeight);
		}
		
		//Checks if the key for attack is pressed and no attack is going and if the player can move
		if(this.keyhandler.keyPressed(87/*Key W*/)&&this.animations.isAnimationComplet()) {
//			//Starts the attack
			this.swingticks=0;
			this.animations.startAnimation("attack",3);
		}
		
		if(this.keyhandler.keyPressed(70/*F*/)) {
			//Drops the current carrying item
			this.dropItem();
		}
	}
	
	/*
	 * Updates the item that the player is carrying
	 * */
	private void updateItem() {
		
		//Updates the current carrying item if it exists
		if(this.carryingItem!=null) {
			this.carryingItem.getPhysics().setX(this.physics.getX());
			this.carryingItem.getPhysics().setY(this.physics.getY()-this.getHitbox().getFixedY());
		}
		
		//Iterates over all items to find a item that the player can pickup
		Optional<EntityItem> pickup = this.game.getObjects().stream()
		//Gets all items
		.filter(i->i instanceof EntityItem)
		.map(i->(EntityItem)i)
		//Checks that the player isnot carrying the item
		.filter(i->i!=this.carryingItem)
		//Checks if the items can be pickedup
		.filter(i->i.canBePickuped())
		//Checks if the player is colliding with that item
		.filter(i->this.isEntityColliding(i))
		.findAny();
		
		//Checks if any item can be pickedup
		if(!pickup.isPresent())
			return;
		
		//Drops the old item
		this.dropItem();
		
		//Sets the new item
		this.carryingItem=pickup.get();
		//Removes any gravity
		this.carryingItem.getPhysics().setGravity(false);
	}
	
	/*
	 * Drops the item if the player has any
	 * */
	public void dropItem() {
		//Removes the current item if the player is carrying any
		if(this.carryingItem==null)
			return;
		//Adds the gravity
		this.carryingItem.getPhysics().setGravity(true);
		//Sets a new pickup delay
		this.carryingItem.setPickUpDelay();
		//Adds the throw motion
		this.carryingItem.getPhysics().setMotionX(5*(this.animations.isReverse()?-1:1));
		this.carryingItem.getPhysics().setMotionY(-2);
		//Removes the item
		this.carryingItem=null;
	}
	
	/*
	 * Updates the swingticks and attacks the entity
	 * */
	private void updateAttack() {
		//Checks if the player swings his sword
		if(this.swingticks==-1)
			return;
		
		//Updates the swingticks
		this.swingticks++;
		
		//Checks if the swinganimation is far enought
		if(this.swingticks<30)
			return;
		
		//Stops the swingticks
		this.swingticks = -1;
		
		//Gets the nearest entity to hit;
		Optional<PSEntityLiving> hit = this.attackEntity();
		//Checks if that exists
		if(!hit.isPresent())
			return;
		//Kills the object
		hit.get().kill();
	}
	
	/*
	 * Returns the slime the player has hit
	 * */
	private Optional<PSEntityLiving> attackEntity(){
		//Gets all objects
		return this.game.getObjects().stream()
				//Filters any entitys
				.filter(i->i instanceof PSEntityLiving)
				.map(i->(PSEntityLiving)i)
				//Filters the it is not dead
				.filter(i->!i.isDead())
				//Filters the range y
				.filter(i->Math.abs(i.getPhysics().getY()-this.physics.getY()) <70)
				//Filters the range x
				.filter(
						i->
						(!this.animations.isReverse()&&
						i.getPhysics().getX()>this.physics.getX()&&
						i.getPhysics().getX()-this.physics.getX()<this.game.getSettings().range*40)||
						(this.animations.isReverse()&&
						i.getPhysics().getX()<this.physics.getX()&&
						this.physics.getX()-i.getPhysics().getX()<this.game.getSettings().range*40))
				//Returns the match
				.findAny();
	}
}
