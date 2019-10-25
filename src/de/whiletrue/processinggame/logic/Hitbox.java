package de.whiletrue.processinggame.logic;

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
	
	
	public final boolean areBoxesColliding(int x1,int y1,int x2,int y2,Hitbox box2) {
		//Checks if the boxes are colliding on the x achses
		boolean x = (
					x1+this.getFixedX()/2>=x2-box2.getFixedX()/2&&
					x1-this.getFixedX()/2<=x2+box2.getFixedX()/2
				);
		
		//Checks if the boxes are colliding on the y achses
		boolean y = (
					y1>=y2-box2.getFixedY()&&
					y1-this.getFixedY()<=y2
				);
		
		//Returns if both collid
		return x&&y;
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
