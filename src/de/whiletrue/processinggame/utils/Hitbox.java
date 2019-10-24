package de.whiletrue.processinggame.utils;

import java.awt.Color;

import de.whiletrue.processinggame.rendering.Renderer;

public class Hitbox {

	private final int sizeX,sizeY;
	private double scale;
	
	public Hitbox(int sizeX,int sizeY,double scale) {
		this.sizeX=sizeX;
		this.sizeY=sizeY;
		this.scale = scale;
	}

	/*
	 * Renders the hitbox
	 * */
	public final void renderHitbox(Renderer renderer,int x,int y) {
		//Renders the hitbox
		renderer.renderOutline(x-this.getFixedX()/2, y-this.getFixedY(), this.getFixedX(), this.getFixedY(), Color.blue.getRGB(), 1);
	}
	
	/**
	 * @return the sizeX
	 */
	public final int getFixedX() {
		return (int) (this.sizeX*this.scale);
	}
	
	/**
	 * @return the sizeY
	 */
	public final int getFixedY() {
		return (int) (this.sizeY*this.scale);
	}
	
	/**
	 * @return the sizeX
	 */
	public final int getRawX() {
		return this.sizeX;
	}

	/**
	 * @return the sizeY
	 */
	public final int getRawY() {
		return this.sizeY;
	}

	/**
	 * @return the scale
	 */
	public final double getScale() {
		return this.scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public final void setScale(double scale) {
		this.scale = scale;
	}
	
}
