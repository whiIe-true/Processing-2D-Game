package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Physics;
import de.whiletrue.processinggame.utils.Animation;

public abstract class PSEntity extends PSObject{

	protected Animation animations;
	protected Physics physics;
	protected Hitbox hitbox;
	
	public PSEntity(Game game,Renderer renderer) {
		super(game,renderer);
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
		if(this.game.getSettings().showHitboxes)
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

}
