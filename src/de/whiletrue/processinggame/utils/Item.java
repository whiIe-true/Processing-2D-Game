package de.whiletrue.processinggame.utils;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.animations.Animation;

public abstract class Item {

	private String name,path;
	private Animation animation;
	
	public Item(String name,String path) {
		this.name = name;
		this.path = path;
		this.animation = new Animation();
		this.animation.init(Game.getInstance().getRenderer(), "idle");
		this.animation.loadAnimations("idle", path,1);
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return this.path;
	}

	/**
	 * @param path the path to set
	 */
	public final void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the animation
	 */
	public final Animation getAnimation() {
		return this.animation;
	}
	
}
