package de.whiletrue.processinggame.objects.entitys;

import java.util.Optional;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.utils.Items;

public class EntityChest extends PSEntity{

	private Items inside;
	private boolean open;
	
	private EntityPlayer player;
	
	@Override
	public void init(LoadFrame loadframe) {
		//Gets item, open
		this.inside = loadframe.getItem("item");
		this.open = loadframe.getBool("open");
		
		//Refrences some stuff
		this.player = this.state.getPlayer();
		
		//Loads the animations
		this.animations.init(this.open?"open":"closed");
		this.animations.loadAnimations("closed", "rsc/enviroment/chest/closed.png", -1);
		this.animations.loadAnimations("open", "rsc/enviroment/chest/open.png", -1);
	
		//Loads the chests hitbox
		this.hitbox = new Hitbox(16, 15, 2);
		
		//Sets the chests physics
		this.physics.init(this.hitbox, 0, 0);
		this.physics.setGravity(false);
		this.physics.setMovable(false);
		
		super.init(loadframe);
	}
	
	@Override
	public LoadFrame save() {
		LoadFrame holder = super.save();
		holder.setItem("item", this.inside);
		holder.setBool("open", this.open);
		return holder;
	}
	
	
	@Override
	public void handleTick() {
		//Checks if the chest is allready open
		if(this.open)
			return;

		//Checks if the player has the key
		if(this.player.getItemHolding()!=Items.KEY)
			return;
		
		//Checks if the player is colliding with the chest
		if(!this.isEntityColliding(this.player))
			return;
		
		//Removes the key
		this.player.setItemHolding(null);
		//Opens the chest
		this.open=true;
		this.animations.setIdleAnimation("open");
		
		//Drops the item
		Optional<PSEntity> optDrop = this.state.getWorld().spawnEntity(EntityItem.class, "x",this.physics.getX(),"y",this.physics.getY(),"item",this.inside);
		//Checks if the item can be dropped
		if(!optDrop.isPresent())
			return;
		
		//Gets the real entity
		EntityItem drop = (EntityItem) optDrop.get();
		
		drop.setPickUpDelay();
		drop.getPhysics().setMotionY(-2);
		drop.getPhysics().setMotionX(0);
	}
}
