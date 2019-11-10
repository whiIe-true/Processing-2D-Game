package de.whiletrue.processinggame.entitys;

public abstract class BaseStats {

	private int attackDamage,speed,jumpHeight,range,maxhealth,noDamageTicks;
	
	public BaseStats(int attackDamage,int speed,int jumpheight,int range,int maxhealth,int noDamageTicks) {
		this.attackDamage = attackDamage;
		this.speed = speed;
		this.range = range;
		this.jumpHeight = jumpheight;
		this.maxhealth = maxhealth;
		this.noDamageTicks = noDamageTicks;
	}

	protected abstract int attackDamage(int baseDamage);
	protected abstract int speed(int baseSpeed);
	protected abstract int jumpheight(int baseJumpheight);
	protected abstract int range(int baseRange);
	protected abstract int maxHealth(int baseMaxHealth);
	protected abstract int noDamageTicks(int baseNoDamageTicks);
	
	public int getAttackDamage() {
		return this.attackDamage(this.attackDamage);
	}
	public int getSpeed() {
		return this.speed(this.speed);
	}
	public int getJumpHeight() {
		return this.jumpheight(this.jumpHeight);
	}
	public int getRange() {
		return this.range(this.range);
	}
	public int getMaxHealth() {
		return this.maxHealth(this.maxhealth);
	}
	public int getNoDamageTicks() {
		return this.noDamageTicks(this.noDamageTicks);
	}
}
