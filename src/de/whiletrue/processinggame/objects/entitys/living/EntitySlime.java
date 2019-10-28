package de.whiletrue.processinggame.objects.entitys.living;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.objects.entitys.BaseStats;

public class EntitySlime extends PSEntityLiving{

	private EntityPlayer player;
	
	public EntitySlime(int x, int y) {
		this.player = this.game.getPlayer();
		
		//Loads the hitbox
		this.hitbox = new Hitbox(16, 16, 2.5);
		
		//Loads the physics
		this.physics.init(this.hitbox,x, y,.05,.2);
		this.physics.randomMotion(4);
		
		//Load the skin
		this.animations.init("idle");
		this.animations.loadAnimations("idle", "rsc/slime/idle.png",20);
		this.animations.loadAnimations("falling", "rsc/slime/falling.png",20);
	}
	
	@Override
	public BaseStats initBaseStats() {
		return new BaseStats(20,1,2,0,100) {
			
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
			//Pushes the player away
			this.player.getPhysics().pushY(-.8);
			this.player.getPhysics().pushX(this.player.getPhysics().getX()-this.physics.getX()>0?1:-1);
			//Damages the player
			this.player.damage(this.getStats().getAttackDamage());
		}
		
		//Checks if the slime is onground
		if(this.physics.isOnground()) {
			
			//Gets the direction the slime should jump
			int dir = (this.game.getPlayer().getPhysics().getX()-this.physics.getX())>1?1:-1;
			
			//Lets the slime jump towards the player
			this.physics.pushX(dir*this.getStats().getSpeed());
			this.physics.pushY(-this.getStats().getJumpHeight());
		}
		//Sets the direction facing
		this.animations.setReverse(this.game.getPlayer().getPhysics().getX()>this.physics.getX());
		
		//Calls the fallback
		super.handleTick();
	}
}
