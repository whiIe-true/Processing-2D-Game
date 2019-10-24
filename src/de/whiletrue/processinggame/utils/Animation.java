package de.whiletrue.processinggame.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import de.whiletrue.processinggame.rendering.Renderer;
import processing.core.PImage;

public class Animation {

	private Renderer renderer;
	
	private Map<String, PImage[]> animations = new HashMap<>();
	private int animationframe,animationticks,maxanimationticks;
	private String animation;
	
	private String idleanimation;
	private int idleticks,idleticksmax;
	
	private PImage currentImage;
	private int ticks;
	private boolean reverse;
	
	/*
	 * Mainly the constructor
	 * */
	public final void init(Renderer renderer,String idleanimation,int idleticks) {
		this.renderer = renderer;
		this.idleanimation = idleanimation.toLowerCase();
		this.idleticksmax = idleticks;
		this.reverse = false;
	}
	
	/*
	 * Loads some animations
	 * */
	public void loadAnimations(String name,String path) {
		this.animations.put(name,this.renderer.loadImagesSeperatedBy(path));
	}
	
	/*
	 * Starts all animations
	 * */
	public void start() {
		this.currentImage = this.animations.get(this.idleanimation)[0];
	}
	
	/*
	 * Renders the skin on the applet
	 * */
	public void renderAt(int x,int y,double scale) {
		
		//Gets the width and height
		int w = (int) (this.currentImage.width*scale);
		int h = (int) (this.currentImage.height*scale);
		
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.window.fill(Color.DARK_GRAY.getRGB());
			//Checks the render direction
			if(this.reverse) {
				//Renders reverse
				this.renderer.window.translate(w, 0);
				this.renderer.window.scale(-1,1);
				this.renderer.window.image(this.currentImage,-x+w/2,y-h,w,h);
			}else {
				//Renders normal
				this.renderer.window.image(this.currentImage,x-w/2,y-h,w,h);
			}
		}
		//Closes the matrix
		this.renderer.pop();
		
	}
	
	/*
	 * Executes on every tick to change costume
	 * */
	public void onTick() {
		//Checks if it has animations to play
		special:if(this.animation!=null) {
			//Checks if the ticks are done
			if(++this.animationticks >= this.maxanimationticks) {
				this.animationticks=0;
				//Checks if the animation is complete
				if(this.animationframe>=this.animations.get(this.animation).length) {
					//Resets the animation
					this.animation = null;
					break special;
				}
				//Sets the next image to render
				this.currentImage = this.animations.get(this.animation)[this.animationframe++];
				return;
			}
			return;
		}
		
		//Checks if its time to set the next skin
		if(++this.ticks >= this.idleticksmax) {
			//Resets the counter
			this.ticks=0;
			//Sets the new skin
			this.idleticks++;
			//Checks if the skin is to high and if so resets it
			if(this.idleticks >= this.animations.get(this.idleanimation).length)
				this.idleticks = 0;
			//Sets the render image
			this.currentImage = this.animations.get(this.idleanimation)[this.idleticks];
		}
	}
	
	/*
	 * Starts a animation
	 * */
	public boolean startAnimation(String animation,int ticks) {
		//Checks if the animation exists
		if(!this.animations.containsKey(animation))
			return false;
		
		//Sets the animation and resets the frames
		this.animation = animation;
		this.animationframe=0;
		this.maxanimationticks = ticks;
		this.animationticks=0;
		
		return true;
	}
	
	/*
	 * Returns if the current animation is complet
	 * */
	public boolean isAnimationComplet() {
		return this.animation==null;
	}
	
	/**
	 * @param idleanimation the idleanimation to set
	*/
	public void setIdleAnimation(String idleanimation) {
		this.idleanimation = idleanimation;
	}
	
	/*
	 * Resets the idle ticks
	 * */
	public void resetIdleticks() {
		this.idleticks = 0;
	}
	
	/*
	 * Returns the idle animation
	 * */
	public String getIdleAnimation() {
		return this.idleanimation;
	}
	
	/**
	 * @param direction the direction to set
	 */
	public final void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	
	/**
	 * @returns if the rendering is flipped
	 * */
	public boolean isReverse() {
		return this.reverse;
	}
}
