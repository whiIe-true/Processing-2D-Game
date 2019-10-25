package de.whiletrue.processinggame.logic;

import java.util.Optional;
import java.util.Random;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.objects.ObjectWall;

public class Physics {
	
	private int x,y;
	private double motionX,motionY,pushX,pushY;
	private boolean onground,gravity = true;
	
	private Hitbox hitbox;
	
	private Random random = new Random();
	
	/*
	 * Mainly the constructor  
	 */
	public final void init(Hitbox hitbox,int x,int y,double pushX,double pushY) {
		this.x = x;
		this.y = y;
		this.pushX=pushX;
		this.pushY=pushY;
		this.hitbox = hitbox;
	}
	
	/*
	 * Executes on every update
	 * */
	public void handleTick() {
		//Checks if the x motion is not equal to 0 and if so pushes the player
		if(this.motionX>0)
			this.motionX-=this.pushX;
		if(this.motionX<0)
			this.motionX+=this.pushX;
		this.x+=this.motionX;
		
		//Checks if the player is colliding with any object
		Optional<ObjectWall> obj = Game.getInstance().getObjects().stream()
		.filter(i->i instanceof ObjectWall)
		.map(i->(ObjectWall)i)
		.filter(i->i.getY()-2<this.y)
		.filter(i->i.getY()+20>this.y)
		.filter(i->i.getX()<this.x+this.hitbox.getFixedX()/2)
		.filter(i->i.getX()+i.getWidth()>this.x-this.hitbox.getFixedX()/2)
		.findAny();
		
		//Checks if the player is in or on the platform
		boolean inplatform = obj.isPresent();
		
		//Sets the default state of onground to false
		this.onground = false;
		
		//Increases the y motion
		this.motionY+=this.pushY;
		
		//Checks if the player is on the floor
		if(inplatform) {
			//Checks if the motion is going down
			if(this.motionY>0)
				this.motionY=0;
			//Checks if the player is inside the ground
			if(y>obj.get().getY())
				//Slowly adds some velocity that pushes the player ontop of the platform
				this.motionY-=0.2;
			else
				this.onground = true;
		}
		
		//Checks if the gravity is enabled
		if(!this.gravity)
			//Sets the motion for Y to 0
			this.motionY=0;
		
		//Adds the motion y to the position y
		this.y+=this.motionY;
	}

	/*
	 * Adds a random motion to the entity
	 * */
	public void randomMotion(int strenght) {
		this.motionX+=this.random.nextInt(strenght)-strenght/2;
		this.motionY+=this.random.nextInt(strenght)-strenght/2;
	}
	
	/**
	 * @return the x
	 */
	public final int getX() {
		return this.x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return this.y;
	}

	/**
	 * @return the motionX
	 */
	public final double getMotionX() {
		return this.motionX;
	}

	/**
	 * @return the motionY
	 */
	public final double getMotionY() {
		return this.motionY;
	}

	/**
	 * @return the onground
	 */
	public final boolean isOnground() {
		return this.onground;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @param x the x to set
	 */
	public final void addX(int x) {
		this.x+= x;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * @param motionX the motionX to set
	 */
	public final void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	/**
	 * @param motionY the motionY to set
	 */
	public final void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	/**
	 * @param onground the onground to set
	 */
	public final void setOnground(boolean onground) {
		this.onground = onground;
	}
	
	public final void pushX(double motionX) {
		this.motionX+=motionX;
	}
	
	public final void pushY(double motionY) {
		this.motionY+=motionY;
	}

	/**
	 * @return the gravity
	 */
	public final boolean hasGravity() {
		return this.gravity;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public final void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
}
