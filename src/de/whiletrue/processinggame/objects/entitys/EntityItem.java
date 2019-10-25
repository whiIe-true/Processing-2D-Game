package de.whiletrue.processinggame.objects.entitys;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Item;

public class EntityItem extends PSEntity{

	private Item item;
	
	private double hoverTicks;
	private boolean hoverDirection;
	private int pickupdelay;
	
	public EntityItem(Game game, Renderer renderer,Item item,int x,int y) {
		super(game, renderer);
		
		//Loads the items texture
		this.animations.init(renderer, "item", 0);
		this.animations.loadAnimations("item", item.getPath(),1);
		
		//Loads the items hitbox
		this.hitbox = new Hitbox(20, 20, 2);
		
		//Loads the items physics
		this.physics.init(this.hitbox, x, y, .1, .2);
		this.physics.randomMotion(10);
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		
		//Gets the items location
		int x = this.physics.getX(),y = this.physics.getY();

		//Renders the item
		this.animations.renderAt(x, (int) (y+this.hoverTicks), this.hitbox.getScale());
		
		//Checks if debugrendering is enabled
		if(this.game.getSettings().showHitboxes)
			this.hitbox.renderHitbox(this.renderer, x, y);
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
	public final Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public final void setItem(Item item) {
		this.item = item;
	}

}
