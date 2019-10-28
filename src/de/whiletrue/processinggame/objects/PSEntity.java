package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.logic.Physics;
import de.whiletrue.processinggame.player.Settings;
import de.whiletrue.processinggame.rendering.animations.Animation;

public abstract class PSEntity extends PSObject{

	protected Animation animations;
	protected Physics physics;
	protected Hitbox hitbox;
	
	public PSEntity() {
		this.animations = new Animation();
		this.physics = new Physics();
	}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Gets the entity cords
		int x = this.physics.getX(),y = this.physics.getY();
		
		//Renders the skin
		this.animations.renderAt(x, y,this.hitbox.getScale());
		//Checks if debugrendering is enabled
		if(Settings.showHitboxes)
			this.hitbox.renderHitbox(this.renderer, x, y);
	}
	
	@Override
	public void handleTick() {
		this.physics.handleTick();
		this.animations.onTick();
	}

	/*
	 * Returns if the entity is colliding with another entity
	 * */
	public boolean isEntityColliding(PSEntity entity) {
		return this.hitbox.areBoxesColliding(this.physics.getX(), this.physics.getY(), entity.physics.getX(), entity.physics.getY(), entity.hitbox);
	}
	
	/*
	 * Returns the distance between two entitys
	 * */
	public double distanceTo(PSEntity entity) {
		return Math.sqrt(Math.pow(this.physics.getX()-entity.physics.getX(), 2)+Math.pow(this.physics.getY()-entity.physics.getY(), 2));
	}
	
	/**
	 * @return the physics
	 */
	public final Physics getPhysics() {
		return this.physics;
	}
	
	/**
	 * @returns the hitbox
	 * */
	public Hitbox getHitbox() {
		return this.hitbox;
	}

	/**
	 * @return the animations
	 */
	public final Animation getAnimations() {
		return animations;
	}

}
