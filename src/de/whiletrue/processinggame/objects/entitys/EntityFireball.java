package de.whiletrue.processinggame.objects.entitys;

import java.util.Optional;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.PSEntityLiving;

public class EntityFireball extends PSEntity{

	public EntityFireball(int x,int y,int motionX) {
		//Loads the items texture
		this.animations.init("idle");
		this.animations.loadAnimations("idle", "rsc/enviroment/fireball.png", 5);
		//Loads the items hitbox
		this.hitbox = new Hitbox(80, 80, .8);
		
		//Loads the items physics
		this.physics.init(this.hitbox, x, y, 0, 0);
		this.physics.setMotionX(motionX);
		this.physics.setGravity(false);
	}
	
	@Override
	public void handleTick() {
		//Calles the callback
		super.handleTick();

		//Gets the entity that got hit
		Optional<PSEntityLiving> hit = this.game.getWorld().getObjects().stream()
		.filter(i->i instanceof PSEntityLiving)
		.map(i->(PSEntityLiving)i)
		.filter(i->i.isEntityColliding(this))
		.findAny();
		
		//Checks if the entity exists
		if(!hit.isPresent())
			return;
		
		//Damages the entity and despawnes
		hit.get().damage(20,0,0);
		this.game.getWorld().kill(this);
	}
	
}
