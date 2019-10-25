package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;

public abstract class PSEntityLiving extends PSEntity{

	protected boolean dead = false;
	
	public PSEntityLiving(Game game, Renderer renderer) {
		super(game, renderer);
	}

	/**
	 * @return the dead
	 */
	public final boolean isDead() {
		return this.dead;
	}

	/**
	 * @param dead the dead to set
	 */
	public final void kill() {
		this.dead = true;
	}
	
}
