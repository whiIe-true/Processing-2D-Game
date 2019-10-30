package de.whiletrue.processinggame.objects.entitys;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.utils.Item;
import de.whiletrue.processinggame.utils.Items;

public class EntityChest extends PSEntity{

	private Item inside;
	private boolean open;
	
	private EntityPlayer player;
	
	public EntityChest(int x,int y,Item inside) {
		this.inside = inside;
		this.player = this.state.getPlayer();
		
		//Loads the animations
		this.animations.init("closed");
		this.animations.loadAnimations("closed", "rsc/enviroment/chest/closed.png", -1);
		this.animations.loadAnimations("open", "rsc/enviroment/chest/open.png", -1);
	
		//Loads the chests hitbox
		this.hitbox = new Hitbox(16, 15, 2);
		
		//Sets the chests physics
		this.physics.init(this.hitbox, x, y, 0, 0);
		this.physics.setGravity(false);
		this.physics.setMovable(false);
	}
	
	@Override
	public void handleTick() {
		//Checks if the chest is allready open
		if(this.open)
			return;

		//Checks if the player has the key
		if(this.player.getItemHolding()!=Items.key)
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
		EntityItem drop = new EntityItem(this.inside, this.getPhysics().getX(), this.physics.getY());
		drop.setPickUpDelay();
		drop.getPhysics().setMotionY(-2);
		drop.getPhysics().setMotionX(0);
		
		this.state.getWorld().spawn(drop);
		
	}
	
}
