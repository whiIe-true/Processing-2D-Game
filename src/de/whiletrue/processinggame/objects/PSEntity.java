package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Physics;
import de.whiletrue.processinggame.utils.Animation;

public class PSEntity extends PSObject{

	protected Animation animations;
	protected Physics physics;
	protected Hitbox hitbox;
	protected boolean dead = false;
	
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

	/**
	 * @return the physics
	 */
	public final Physics getPhysics() {
		return this.physics;
	}

	/**
	 * @return the dead
	 */
	public final boolean isDead() {
		return dead;
	}

	/**
	 * @param dead the dead to set
	 */
	public final void kill() {
		this.dead = true;
	}

}
