package de.whiletrue.processinggame.utils;

import java.util.Arrays;
import java.util.Optional;

import de.whiletrue.processinggame.rendering.animations.Animation;

public enum Items {

	//Placeholder Item for nothing
	NONE(0,"None",null),
	
	//Normal Items
	KEY(1,"Key","rsc/items/key.png"),
	RING_OF_JUMPING(2,"Ring of jumping","rsc/items/ring_of_jumping.png"),
	HEAL_POTION(3,"Heal Potion","rsc/items/heal_potion.png"),
	SWORD(4,"Sword","rsc/items/sword.png"),
	BOOTS(5,"Boots","rsc/items/boots.png"),
	FIREBALL_WAND(6,"Fireball Wand","rsc/items/fireball_wand.png");
	
	private int id;
	private String name,path;
	private Animation animation;
	
	private Items(int id,String name,String path) {
		this.name = name;
		this.path = path;
		this.id = id;

		//Creates the animation
		this.animation = new Animation();
		
		//Checks if the animation should load
		if(path==null)
			return;
		
		//Loads the items skin
		this.animation = new Animation();
		this.animation.init("idle");
		this.animation.loadAnimations("idle", path,-1);
	}

	public static Items getItemByID(int id) {
		Optional<Items> optFound = Arrays.stream(Items.values()).filter(i->i.getId()==id).findFirst();
		return optFound.isPresent()?optFound.get():Items.NONE;
	}
	
	/**
	 * @return the id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * @return the animation
	 */
	public final Animation getAnimation() {
		return this.animation;
	}
	
}
