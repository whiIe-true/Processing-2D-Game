package de.whiletrue.processinggame.entitys.notliving;

import java.util.Optional;

import de.whiletrue.processinggame.entitys.PSEntity;
import de.whiletrue.processinggame.entitys.PSEntityLiving;
import de.whiletrue.processinggame.logic.Hitbox;

public class EntityFireball extends PSEntity{

	private int lifespan;
	
	@Override
	public void init(LoadFrame loadframe) {
		//Setslifespan
		this.lifespan = loadframe.getInt("lifespan");
		
		//Loads the texture
		this.animations.init("idle");
		this.animations.loadAnimations("idle", "rsc/enviroment/fireball.png", 5);
		//Loads the hitbox
		this.hitbox = new Hitbox(80, 80, .8);
		
		//Loads the items physics
		this.physics.init(this.hitbox, 0, 0);
		this.physics.setGravity(false);
		
		super.init(loadframe);
	}
	
	@Override
	public LoadFrame save() {
		LoadFrame holder = super.save();
		
		//Sets the values
		holder.setInt("lifespan", this.lifespan);
		
		return holder;
	}
	
	@Override
	public void handleTick() {
		//Checks if the fireball has lived long enought
		if(++this.lifespan>300)
			this.state.getWorld().kill(this);
		
		//Calles the callback
		super.handleTick();

		//Gets the entity that got hit
		Optional<PSEntityLiving> hit = this.state.getWorld().getObjects().stream()
		.filter(i->i instanceof PSEntityLiving)
		.map(i->(PSEntityLiving)i)
		.filter(i->i.isEntityColliding(this))
		.findAny();
		
		//Checks if the entity exists
		if(!hit.isPresent())
			return;
		
		//Damages the entity and despawnes
		hit.get().damage(20,0,0);
		this.state.getWorld().kill(this);
	}
}
