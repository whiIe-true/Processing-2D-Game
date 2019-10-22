package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Physics;
import de.whiletrue.processinggame.utils.Skin;

public class PSEntity extends PSObject{

	protected Skin skin;
	protected Physics physics;
	protected Hitbox hitbox;
	protected boolean dead = false;
	
	public PSEntity(Game game,Renderer renderer) {
		super(game,renderer);
		this.skin = new Skin();
		this.physics = new Physics();
	}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		this.skin.renderAt(this.physics.getX(), this.physics.getY());
	}
	
	@Override
	public void handleTick() {
		this.physics.handleTick();
		this.skin.onTick();
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
