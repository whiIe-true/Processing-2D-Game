package de.whiletrue.processinggame.entitys.notliving;

import java.awt.Color;

import de.whiletrue.processinggame.entitys.PSEntity;
import de.whiletrue.processinggame.logic.Physics;

public class EntityWall extends PSEntity{

	private int x,y,width;
	
	@Override
	public void init(LoadFrame loadframe) {
		//Gets x,y and width
		this.x = loadframe.getInt("x");
		this.y = loadframe.getInt("y");
		this.width = loadframe.getInt("width");
		
		//Exits without calling the callback
	}
	
	@Override
	public LoadFrame save() {
		//Creates a new loadframe, because the wall has no animations or physics
		LoadFrame holder = new LoadFrame();
		holder.setInt("x", this.x);
		holder.setInt("y", this.y);
		holder.setInt("width", this.width);
		return holder;
	}
	
	@Override
	public void handleTick() {
		//Doesnt uses the callback function
	}
	
	@Override
	public boolean isEntityColliding(PSEntity entity) {
		//Returns if the entity should be pushed up on the platform
		return this.isEntityColliding(entity.getPhysics());
	}
	
	/*
	 * Returns if the physics are colliding
	 * */
	public boolean isEntityColliding(Physics entity) {
		//Returns if the entity should be pushed up on the platform
		return this.y-2<entity.getY()&&
				this.y+20>entity.getY()&&
				this.x<entity.getX()+entity.getHitbox().getFixedX()/2&&
				this.x+this.width>entity.getX()-entity.getHitbox().getFixedX()/2;
	}
	
	@Override
	public double distanceTo(PSEntity entity) {
		//Overrides the function, because no physics or animations are used
		return Math.sqrt(Math.pow(this.x-entity.getPhysics().getX(), 2)+Math.pow(this.y-entity.getPhysics().getY(), 2));
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Renders the wall
		this.renderer.renderRectWithStroke(this.x, this.y, this.width, 10, Color.darkGray.getRGB(), 0, 1);
		//Exits without calling the callback
	}

	/**
	 * @return the x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return y;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}
	
}
