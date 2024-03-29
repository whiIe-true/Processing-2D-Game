package de.whiletrue.processinggame.rendering.animations;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import de.whiletrue.processinggame.rendering.Renderer;
import processing.core.PImage;

public class Animation {

	private Renderer renderer;
	
	private Map<String, AnimationType> animations = new HashMap<>();

	private boolean reverse,blinking;
	private int blinkticks;
	
	private String idleanimation;
	private String current;
	
	/*
	 * Mainly the constructor
	 * */
	public final void init(String idleanimation) {
		this.renderer = Renderer.getInstance();
		this.idleanimation = idleanimation.toLowerCase();
		this.reverse = false;
	}
	
	/*
	 * Loads some animations
	 * */
	public void loadAnimations(String name,String path,int ticksPerFrame) {
		//Loads the animation
		this.animations.put(name,new AnimationType(this.renderer, path, ticksPerFrame,()->this.current=null));
	}
	
	/*
	 * Renders the skin on the applet
	 * */
	public void renderAt(int x,int y,double scale) {
		
		//Checks if any animations are loaded
		if(this.animations.size()<=0)
			return;
		
		//Updates the scale
		this.getCurrentFrame().updateScale(scale);
		
		//Gets the image
		PImage frame = this.getCurrentFrame().getImage();
		
		//Gets the width and height
		int w = (int) (frame.width);
		int h = (int) (frame.height);
		
		//Opens the matrix
		this.renderer.push();
		{
			//Checks if blinking is enabled and the image is ready to blink
			if(this.blinking&&this.blinkticks>=5)
				//Makes the image transparent
				this.renderer.window.tint(255,10);
			
			this.renderer.window.fill(Color.DARK_GRAY.getRGB());
			//Checks the render direction
			if(this.reverse) {
				//Renders reverse
				this.renderer.window.translate(w, 0);
				this.renderer.window.scale(-1,1);
				this.renderer.renderImage(frame,-x+w/2,y-h);
			}else {
				//Renders normal
				this.renderer.renderImage(frame,x-w/2,y-h);
			}
		}
		//Closes the matrix
		this.renderer.pop();
		
	}
	
	/*
	 * Executes on every tick to change costume
	 * */
	public void onTick() {
		//Checks if blinking is enabled
		if(this.blinking) {
			//Increases the ticks and checks if they are to high
			if(++this.blinkticks>10)
				this.blinkticks=0;
		}
		
		//Checks if a animation is played
		if(this.current==null)
			//Handles the tick event
			this.animations.get(this.idleanimation).handleTick();
		else
			//Handles the tick event
			this.animations.get(this.current).handleTick();
	}
	
	/*
	 * Starts a animation
	 * */
	public boolean startAnimation(String animation) {
		//Checks if the animation exists
		if(!this.animations.containsKey(animation))
			return false;
		
		//Sets the animation and resets the frames
		this.current = animation;
		this.animations.get(animation).restart();
		return true;
	}
	
	/*
	 * Returns if the current animation is complet
	 * */
	public boolean isAnimationComplet() {
		return this.current==null;
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
		this.animations.get(this.idleanimation).restart();
	}
	
	/*
	 * Returns the idle animation
	 * */
	public String getIdleAnimation() {
		return this.idleanimation;
	}
	
	public AnimationFrame getCurrentFrame() {
		return this.animations.get(this.isAnimationComplet()?this.idleanimation:this.current).getCurrentFrame();
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

	/**
	 * @return the blinking
	 */
	public final boolean isBlinking() {
		return this.blinking;
	}

	/**
	 * @param blinking the blinking to set
	 */
	public final void setBlinking(boolean blinking) {
		this.blinking = blinking;
	}
}
