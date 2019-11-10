package de.whiletrue.processinggame.entitys.living;

import java.util.Optional;

import de.whiletrue.processinggame.entitys.BaseStats;
import de.whiletrue.processinggame.entitys.PSEntity;
import de.whiletrue.processinggame.entitys.PSEntityLiving;
import de.whiletrue.processinggame.entitys.notliving.EntityItem;
import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.player.Camera;
import de.whiletrue.processinggame.utils.Items;

public class EntityPlayer extends PSEntityLiving{
	
	private Camera camera;

	private Items itemHolding;
	private int dropTicks;

	private int swingticks;
	private int spawnX,spawnY;
	
	//Creates a new entityplayer
	public EntityPlayer() {
		//Inits with a empty loadframe
		this.init(new LoadFrame());
	}
	
	@Override
	public void init(LoadFrame loadframe) {
		//Sets some references
		this.camera = ((StateIngame) Game.getInstance().getState()).getCamera();
		
		//Gets spawnx and spawny
		this.spawnX = loadframe.getInt("spawnX");
		this.spawnY = loadframe.getInt("spawny");
		this.swingticks = loadframe.getInt("swingticks");
		this.dropTicks = loadframe.getInt("dropticks");
		this.setItemHolding(loadframe.getItem("item"));
		//Updates the loaded size
		if(this.isHoldingItem())	
			this.itemHolding.getAnimation().getCurrentFrame().updateScale(2);
		
		//Loades the hitbox
		this.hitbox = new Hitbox(20, 30, 2);
		
		//Loads the physics
		this.physics.init(this.hitbox,.2,.2);
		
		//Loads the skin
		this.animations.init("idle");
		this.animations.loadAnimations("idle", "rsc/player/idle.png",15);
		this.animations.loadAnimations("walk", "rsc/player/walk.png",5);
		this.animations.loadAnimations("attack", "rsc/player/attack.png",3);
		
		//Loads the other attributes
		super.init(loadframe);
	}
	
	@Override
	public LoadFrame save() {
		//Saves all important information
		LoadFrame holder = super.save();
		holder.setInt("spawnX", this.spawnX);
		holder.setInt("spawnY", this.spawnY);
		holder.setInt("swingticks", this.swingticks);
		holder.setInt("dropticks", this.dropTicks);
		holder.setItem("item", this.getItemHolding());
		return holder;
	}
	
	@Override
	public BaseStats initBaseStats() {
		return new BaseStats(20,5,5,2,100,20) {
			
			@Override
			protected int speed(int baseSpeed) {
				//Increases the speed by 20% when wearing the boots
				if(itemHolding.equals(Items.BOOTS))
					baseSpeed*=1.2;
				return baseSpeed;
			}
			
			@Override
			protected int jumpheight(int baseJumpheight) {
				//Lets the player jump 20% higher
				if(itemHolding.equals(Items.RING_OF_JUMPING))
					baseJumpheight*=1.2;
				return baseJumpheight;
			}
			
			@Override
			protected int attackDamage(int baseDamage) {
				//Increases the attack damage by 30% when sword equiped
				if(itemHolding.equals(Items.SWORD))
					baseDamage*=1.3;
				return baseDamage;
			}

			@Override
			protected int range(int baseRange) {
				return baseRange;
			}

			@Override
			protected int maxHealth(int baseMaxHealth) {
				return baseMaxHealth;
			}

			@Override
			protected int noDamageTicks(int baseNoDamageTicks) {
				return baseNoDamageTicks;
			}
		};
	}
	

	@Override
	public void handleTick() {
		
		//Updates the item
		this.updateItem();
		
		//Updates the attack
		this.updateAttack();
		
		//Calls the methods callback function
		super.handleTick();
		
		//Updates the camera
		this.updateCamera();
	}

	/*
	 * Teleports the player back to his spawn point
	 * */
	public final void teleportSpawn() {
		this.physics.teleport(this.spawnX, this.spawnY);
	}
	
	/*
	 * Respawns once dead
	 * */
	public final void respawn() {
		
		//Checks if the entity is dead
		if(!this.isDead())
			return;
		//Sets the player back alive
		this.dead = false;
		this.health = this.stats.getMaxHealth();
		//Teleports the player back to the spawn
		this.teleportSpawn();
		//Sets nodamage ticks very hight
		this.nodamageTicks=100;
	}
	
	/*
	 * Lets the player jump
	 * */
	public void jump() {
		//Checks if the player is onground
		if(!this.physics.isOnground())
			return;
		//Sets the vertical motion
		this.getPhysics().pushY(-this.stats.getJumpHeight());
	}
	
	/*
	 * Attack the next entity
	 * */
	public void attack() {
		
		//Checks if the player has animations to process
		if(!this.getAnimations().isAnimationComplet())
			return;
		
		this.swingticks=0;
		this.getAnimations().startAnimation("attack");
	}
	
	/*
	 * Drops the item if the player has any
	 * */
	public void dropItem() {
		
		//Checks if the player can drop something
		if(this.dropTicks>0)
			return;
		
		//Removes the current item if the player is carrying any
		if(!this.isHoldingItem())
			return;
		
		//Craetes the entity
		Optional<PSEntity> optEnt = this.state.getWorld().spawnEntity(EntityItem.class, "x",this.physics.getX(),"y",this.physics.getY(),"item",this.itemHolding);
		//Checks if the item exists
		if(!optEnt.isPresent())
			return;
		
		EntityItem thro = (EntityItem)optEnt.get();
		
		//Sets a new pickup delay
		thro.setPickUpDelay();
		//Adds the throw motion
		thro.getPhysics().setMotionX(5*(this.animations.isReverse()?-1:1));
		thro.getPhysics().setMotionY(-2);
		
		//Removes the item
		this.setItemHolding(null);
		
		//Resets the drop ticks
		this.dropTicks=10;
	}
	
	/*
	 * Updates the cameras x and y
	 * */
	private void updateCamera() {
		this.camera.update(this.physics.getX(),this.physics.getY());
	}
	
	/*
	 * Updates the item that the player is carrying
	 * */
	private void updateItem() {
		
		//Updates the dropticks
		if(this.dropTicks>0)
			this.dropTicks--;
		
		//Checks if the player is holding any item
		if(this.isHoldingItem())
			return;
		
		//Iterates over all items to find a item that the player can pickup
		Optional<EntityItem> pickup = this.state.getWorld().getObjects().stream()
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
		this.setItemHolding(entitm.getItem());
		
		//Removes the item from the world
		this.state.getWorld().kill(entitm);
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
		Optional<PSEntityLiving> hit = this.getEntityToAttack();
		//Checks if that exists
		if(!hit.isPresent())
			return;
		//Gets the entity
		PSEntityLiving ent = hit.get();
		
		//Gets the push direction
		int dir = ent.getPhysics().getX()-this.physics.getX()>0?1:-1;
		
		//Damages the object
		ent.damage(this.stats.getAttackDamage(),dir*2,-.8);
	}
	
	/*
	 * Returns the slime the player has hit
	 * */
	private Optional<PSEntityLiving> getEntityToAttack(){
		//Gets all objects
		return this.state.getWorld().getObjects().stream()
				//Filters any entitys
				.filter(i->i instanceof PSEntityLiving)
				.map(i->(PSEntityLiving)i)
				//Filters the it is not dead
				.filter(i->!i.isDead())
				//Ensures, that the entity can be attacked
				.filter(i->i.getNodamageTicks()<=0)
				//Filters the range y
				.filter(i->Math.abs(i.getPhysics().getY()-this.physics.getY()) < 70)
				//Filters the range x
				.filter(
						i->
						(!this.animations.isReverse()&&
						i.getPhysics().getX()>this.physics.getX()&&
						i.getPhysics().getX()-this.physics.getX()<this.stats.getRange()*20*this.hitbox.getScale())||
						(this.animations.isReverse()&&
						i.getPhysics().getX()<this.physics.getX()&&
						this.physics.getX()-i.getPhysics().getX()<this.stats.getRange()*20*this.hitbox.getScale()))
				//Findes the nearest
				.reduce((e1,e2)->{
					return e1.distanceTo(this)<e2.distanceTo(this)?e1:e2;
				});
	}

	/**
	 * @return the itemHolding
	 */
	public final Items getItemHolding() {
		return this.itemHolding;
	}

	/**
	 * @param itemHolding the itemHolding to set
	 */
	public final void setItemHolding(Items itemHolding) {
		if(itemHolding==null)
			itemHolding=Items.NONE;
		this.itemHolding = itemHolding;
	}
	
	/**
	 * @returns if the player is holding an item or not
	 * */
	public final boolean isHoldingItem() {
		return !this.itemHolding.equals(Items.NONE);
	}
}
