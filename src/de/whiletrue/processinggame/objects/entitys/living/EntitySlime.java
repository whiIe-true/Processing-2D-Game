package de.whiletrue.processinggame.objects.entitys.living;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.objects.entitys.BaseStats;

public class EntitySlime extends PSEntityLiving{

	private EntityPlayer player;
	
	@Override
	public void init(LoadFrame loadframe) {
		//References some stuff
		this.player = this.state.getPlayer();
		
		//Loads the hitbox
		this.hitbox = new Hitbox(12, 16, 2.5);
		
		//Loads the physics
		this.physics.init(this.hitbox,.05,.2);
		this.physics.randomMotion(4);
		loadframe.loadPhysics(this.physics);
		
		//Load the skin
		this.animations.init("idle");
		this.animations.loadAnimations("idle", "rsc/slime/idle.png",20);
		this.animations.loadAnimations("falling", "rsc/slime/falling.png",20);
		
		//Inits the rest for the living entitys
		super.init(loadframe);
	}
	
	@Override
	public LoadFrame save() {
		LoadFrame lf = super.save();
		lf.savePhysics(this.physics);
		return lf;
	}
	
	@Override
	public BaseStats initBaseStats() {
		/*
		 * -1 means unused
		 * */
		return new BaseStats(20,1,2,-1,100,20) {
			
			@Override
			protected int speed(int baseSpeed) {
				return baseSpeed;
			}
			
			@Override
			protected int range(int baseRange) {
				return baseRange;
			}
			
			@Override
			protected int maxHealth(int baseMaxHealth) {
				return baseMaxHealth;
			}
			
			@Override
			protected int jumpheight(int baseJumpheight) {
				return baseJumpheight;
			}
			
			@Override
			protected int attackDamage(int baseDamage) {
				return baseDamage;
			}

			@Override
			protected int noDamageTicks(int baseNoDamageTicks) {
				return baseNoDamageTicks;
			}
		};
	}
	
	@Override
	public void handleTick() {
		String anim = this.physics.isOnground()?"idle":"falling";
		//Checks if the new animation is diffrent
		if(!anim.equals(this.animations.getIdleAnimation())) {
			//Sets the new animation and resets the ticks
			this.animations.setIdleAnimation(anim);
			this.animations.resetIdleticks();
		}
		
		//Checks if the slime is colliding with the player
		if(this.isEntityColliding(this.player)) {
			//Gets push direction
			int dir = this.player.getPhysics().getX()-this.physics.getX()>0?1:-1;
			//Damages the player
			this.player.damage(this.getStats().getAttackDamage(),dir*5.5,-1.5);
		}
		
		//Checks if the slime is onground
		if(this.physics.isOnground()) {
			
			//Gets the direction the slime should jump
			int dir = (this.state.getPlayer().getPhysics().getX()-this.physics.getX())>1?1:-1;
			
			//Lets the slime jump towards the player
			this.physics.pushX(dir*this.getStats().getSpeed());
			this.physics.pushY(-this.getStats().getJumpHeight());
		}
		//Sets the direction facing
		this.animations.setReverse(this.state.getPlayer().getPhysics().getX()>this.physics.getX());
		
		//Calls the fallback
		super.handleTick();
	}
}
