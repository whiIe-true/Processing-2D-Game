package de.whiletrue.processinggame.objects;

import java.awt.Color;

import de.whiletrue.processinggame.objects.entitys.BaseStats;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;

public abstract class PSEntityLiving extends PSEntity{

	protected BaseStats stats;
	
	protected boolean dead = false;
	protected int health;
	
	protected int nodamageTicks;

	public PSEntityLiving() {
		this.stats = this.initBaseStats();
		this.health = this.stats.getMaxHealth();
	}
	
	public abstract BaseStats initBaseStats();
	
	
	@Override
	public void handleTick() {
		//Makes sure, that maxhealth has no bugs
		if(this.health>this.stats.getMaxHealth())
			this.health = this.stats.getMaxHealth();
		
		//Checks if the entity is imune because of no damage time
		if(this.nodamageTicks>0) {
			this.nodamageTicks--;
			//Sets the animations blinking
			this.animations.setBlinking(true);
		}else
			//Removes the blinking from the skin
			this.animations.setBlinking(false);
		
		//Calles the fallback
		super.handleTick();
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		
		//Shorts the variables
		int x = this.physics.getX(),y = this.physics.getY()-this.hitbox.getFixedY()-8;
		//Healthbar
		healthbar:{
			//Checks if the current entity is the player
			if(this instanceof EntityPlayer)
				//Prevents the healthbar from rendering
				break healthbar;
			
			//Background
			this.renderer.renderRect(x-20, y, 40, 5, Color.gray.getRGB());
			//Health
			this.renderer.renderRect(x-20, y, 40*this.getHealthLeft(), 5, Color.red.getRGB());
			//Outline
			this.renderer.renderOutline(x-20, y, 40, 5, 0, 1);
			
		}
		
		super.handleRender(mouseX, mouseY, mousePressed);
	}
	
	/**
	 * @return the dead
	 */
	public boolean isDead() {
		return this.dead;
	}

	/**
	 * @param dead the dead to set
	 */
	public void kill() {
		this.dead = true;
	}
	
	/*
	 * Healts the entity
	 * */
	public void heal(int health) {
		//Checks if the health goes higher than maxhealth
		if(this.health+health>this.stats.getMaxHealth())
			//Sets the health to maxhealth
			this.health = this.stats.getMaxHealth();
		else
			//Heals the health
			this.health+=health;
	}
	
	/*
	 * Attacks the entity
	 * */
	public void damage(int damage,double knockbackX,double knockbackY) {
		//Checks if the entity can be damaged
		if(this.nodamageTicks>0)
			return;
		
		//Sets the nodamage ticks
		this.nodamageTicks = this.stats.getNoDamageTicks();
		
		//Removes the health from the entity
		this.health-=damage;
		//Pushes the entity back
		this.physics.pushX(knockbackX);
		this.physics.pushY(knockbackY);
		
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
	public final float getHealthLeft() {
		return (float)this.health/(float)this.stats.getMaxHealth();
	}

	/**
	 * @return the stats
	 */
	public final BaseStats getStats() {
		return this.stats;
	}

	/**
	 * @return the nodamageTicks
	 */
	public final int getNodamageTicks() {
		return this.nodamageTicks;
	}
}
