package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Animation;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Physics;

public abstract class PSItem extends PSEntity{

	private double hoverTicks;
	private boolean hoverDirection;
	private int pickupdelay;
	
	public PSItem(Game game, Renderer renderer,int width,int height,double scale) {
		super(game, renderer);
		this.physics = new Physics();
		this.animations = new Animation();
		this.hitbox = new Hitbox(width, height, scale);
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		int x = this.physics.getX(),y = this.physics.getY();
		
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
	
}
