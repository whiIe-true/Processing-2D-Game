package de.whiletrue.processinggame.objects.entitys.living;

import java.util.Optional;

import de.whiletrue.processinggame.Camera;
import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
import de.whiletrue.processinggame.utils.Item;
import de.whiletrue.processinggame.utils.KeyHandler;

public class Player extends PSEntityLiving{

	private KeyHandler keyhandler;
	private Camera camera;
	
	private int swingticks;
	
	private Item itemHolding;
	private int dropTicks;
	
	public Player(Camera camera,KeyHandler keyhandler) {
		this.keyhandler = keyhandler;
		this.camera = camera;
		
		//Loades the hitbox
		this.hitbox = new Hitbox(25, 30, 2);
		
		//Loads the physics
		this.physics.init(hitbox,game.getWidth()/2, game.getHeight()/2,.2,.2);
		
		//Loads the skin
		this.animations.init(renderer,"idle");
		this.animations.loadAnimations("idle", "rsc/player/idle.png",15);
		this.animations.loadAnimations("walk", "rsc/player/walk.png",10);
		this.animations.loadAnimations("attack", "rsc/player/attack.png",3);
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
		
		//Updates the camera
		this.updateCamera();
	}

	/*
	 * Updates the cameras x and y
	 * */
	private void updateCamera() {
		this.camera.update(this.physics.getX(),this.physics.getY());
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
			//Starts the attack
			this.swingticks=0;
			this.animations.startAnimation("attack");
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
		
		//Updates the dropticks
		if(this.dropTicks>0)
			this.dropTicks--;
		
		//Checks if the player is holding any item
		if(this.itemHolding!=null)
			return;
		
		//Iterates over all items to find a item that the player can pickup
		Optional<EntityItem> pickup = this.game.getObjects().stream()
		//Gets all items
		.filter(i->i instanceof EntityItem)
		.map(i->(EntityItem)i)
		//Checks if the items can be pickedup
		.filter(i->i.canBePickuped())
		//Checks if the player is colliding with that item
		.filter(i->this.isEntityColliding(i))
		.findAny();
		
		//Checks if any item can be pickedup
		if(!pickup.isPresent())
			return;
		
		EntityItem entitm = pickup.get();
		
		//Sets the new item
		this.itemHolding=entitm.getItem();
		
		//Removes the item from the world
		this.game.removeObject(entitm);
	}
	
	/*
	 * Drops the item if the player has any
	 * */
	public void dropItem() {
		
		//Checks if the player can drop something
		if(this.dropTicks>0)
			return;
		
		//Removes the current item if the player is carrying any
		if(this.itemHolding==null)
			return;
		
		//Creates the new entity
		EntityItem thro = new EntityItem(this.itemHolding, this.physics.getX(), this.physics.getY());
		{
			//Sets a new pickup delay
			thro.setPickUpDelay();
			//Adds the throw motion
			thro.getPhysics().setMotionX(5*(this.animations.isReverse()?-1:1));
			thro.getPhysics().setMotionY(-2);
		}
		
		//Adds the item to the world
		this.game.addObject(thro);
		
		//Removes the item
		this.itemHolding=null;
		
		//Resets the drop ticks
		this.dropTicks=10;
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

	/**
	 * @return the itemHolding
	 */
	public final Item getItemHolding() {
		return itemHolding;
	}
}
