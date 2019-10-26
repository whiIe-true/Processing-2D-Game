package de.whiletrue.processinggame.objects;

public abstract class PSEntityLiving extends PSEntity{

	protected boolean dead = false;
	
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
