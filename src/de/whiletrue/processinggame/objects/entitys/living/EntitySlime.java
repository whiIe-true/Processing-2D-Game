package de.whiletrue.processinggame.objects.entitys.living;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntityLiving;

public class EntitySlime extends PSEntityLiving{

	public EntitySlime(int x, int y) {

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
	public void handleTick() {
		String anim = this.physics.isOnground()?"idle":"falling";
		//Checks if the new animation is diffrent
		if(!anim.equals(this.animations.getIdleAnimation())) {
			//Sets the new animation and resets the ticks
			this.animations.setIdleAnimation(anim);
			this.animations.resetIdleticks();
		}
		
		//Checks if the slime is onground
		if(this.physics.isOnground()) {
			//Lets the slime jump towards the player
			this.physics.pushX((this.game.getPlayer().getPhysics().getX()-this.physics.getX())>1?1:-1);
			this.physics.pushY(-2);
		}
		//Sets the direction facing
		this.animations.setReverse(this.game.getPlayer().getPhysics().getX()>this.physics.getX());
		
		//Calls the fallback
		super.handleTick();
	}
}
