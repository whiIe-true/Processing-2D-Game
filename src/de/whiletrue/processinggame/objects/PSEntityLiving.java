package de.whiletrue.processinggame.objects;

public abstract class PSEntityLiving extends PSEntity{

	protected boolean dead = false;
	protected int health=100,maxHealth = 100;
	
	protected int nodamageTicks;
	
	@Override
	public void handleTick() {
		//Decreases the nodamagetime
		if(this.nodamageTicks>0)
			this.nodamageTicks--;
		
		//Calles the fallback
		super.handleTick();
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
	
	/*
	 * Healts the entity
	 * */
	public final void heal(int health) {
		//Checks if the health goes higher than maxhealth
		if(this.health+health>this.maxHealth)
			//Sets the health to maxhealth
			this.health = this.maxHealth;
		else
			//Heals the health
			this.health+=health;
	}
	
	/*
	 * Attacks the entity
	 * */
	public final void damage(int damage) {
		//Checks if the entity can be damaged
		if(this.nodamageTicks>0)
			return;
		
		//Sets the nodamage ticks
		this.nodamageTicks = 10;
		
		//Removes the health from the entity
		this.health-=damage;
		//Checks if the entity is dead
		if(this.health<=0) {
			this.dead=true;
			this.health = 0;
		}
	}

	/**
	 * @return the health
	 */
	public final int getHealth() {
		return this.health;
	}

	/**
	 * @return the maxHealth
	 */
	public final int getMaxHealth() {
		return this.maxHealth;
	}
	
	/**
	 * @return the maxHealth
	 */
	public final float getHealthLeft() {
		return (float)this.health/(float)this.maxHealth;
	}
}
