package de.whiletrue.processinggame.utils;

import java.util.HashSet;
import java.util.Set;

import de.whiletrue.processinggame.rendering.animations.Animation;

public abstract class Item {

	private static Set<Item> registeredItems = new HashSet<>();
	
	private String name,path;
	private Animation animation;
	private int id;
	
	public Item(int id,String name,String path) {
		this.name = name;
		this.path = path;
		this.id = id;
		this.animation = new Animation();
		this.animation.init("idle");
		this.animation.loadAnimations("idle", path,-1);
		registeredItems.add(this);
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

	/**
	 * @return the id
	 */
	public final int getId() {
		return this.id;
	}

	/**
	 * @return the registeredItems
	 */
	public static final Set<Item> getRegisteredItems() {
		return registeredItems;
	}
	
}
