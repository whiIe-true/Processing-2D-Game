package de.whiletrue.processinggame.objects.entitys;

import java.util.Optional;

import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.PSEntityLiving;

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
		loadframe.loadPhysics(this.physics);
	}
	
	@Override
	public LoadFrame save() {
		LoadFrame holder = new LoadFrame();
		
		//Sets the values
		holder.setInt("lifespan", this.lifespan);
		holder.savePhysics(this.physics);
		
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
