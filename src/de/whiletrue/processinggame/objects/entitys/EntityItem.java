package de.whiletrue.processinggame.objects.entitys;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.utils.Items;

public class EntityItem extends PSEntity{

	private Items item;
	
	private double hoverTicks;
	private boolean hoverDirection;
	private int pickupdelay;
	
	@Override
	public void init(LoadFrame loadframe) {
		//Gets item
		this.setItem(loadframe.getItem("item"));
		//Gets the ticks
		this.pickupdelay = loadframe.getInt("pickupticks");
		
		//Loads the animations
		this.animations = this.item.getAnimation();
		
		//Loads the items hitbox
		this.hitbox = new Hitbox(20, 20, 2);
		
		//Loads the items physics
		this.physics.init(this.hitbox, .1, .2);
		
		super.init(loadframe);
	}
	
	@Override
	public LoadFrame save() {
		//Creates the holder
		LoadFrame holder = super.save();
		
		//Sets the values
		holder.setInt("pickupticks", this.pickupdelay);
		holder.setItem("item", this.item);
		
		return holder;
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		
		//Gets the items location
		int x = this.physics.getX(),y = this.physics.getY();

		//Renders the item
		this.animations.renderAt(x, (int) (y+this.hoverTicks), this.hitbox.getScale());
		
		//Checks if debugrendering is enabled
		if(Game.getInstance().getSettings().getBool("showHitboxes"))
			this.hitbox.renderHitbox(x, y);
	}
	
	@Override
	public void handleTick() {
		//Handles the physcis
		this.physics.handleTick();
		
		//Adds the hover hight
		this.hoverTicks+=this.hoverDirection?.3:-.3;
		
		//Checks for the hovermax to change to direction
		if(this.hoverTicks>=0)
			this.hoverDirection=false;
		if(this.hoverTicks<=-10)
			this.hoverDirection=true;
		
		//Updates the pickupdelay
		if(this.pickupdelay>0)
			this.pickupdelay--;
	}

	/*
	 * Sets the item notpickup
	 * */
	public void setPickUpDelay() {
		this.pickupdelay = 80;
	}
	
	/**
	 * @returns whether or not the item can be pickuped
	 * */
	public boolean canBePickuped() {
		return this.pickupdelay<=0;
	}

	/**
	 * @return the item
	 */
	public final Items getItem() {
		return this.item;
	}

	/**
	 * @param item the item to set
	 */
	public final void setItem(Items item) {
		if(item==null)
			item=Items.NONE;
		this.item = item;
	}

}
