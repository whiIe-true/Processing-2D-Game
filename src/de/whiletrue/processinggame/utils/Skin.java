package de.whiletrue.processinggame.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.Settings;
import de.whiletrue.processinggame.rendering.Renderer;
import processing.core.PImage;

public class Skin {

	private Settings settings;
	private Renderer renderer;
	private Hitbox hitbox;
	
	private Map<String, PImage[]> animations = new HashMap<>();
	private int animationframe,animationticks,maxanimationticks;
	private String animation;
	
	private String idleanimation;
	private int idleticks,idleticksmax;
	
	private PImage currentImage;
	private int ticks;
	private EnumSkinDirection direction;
	
	/*
	 * Mainly the constructor
	 * */
	public final void init(Game game,Renderer renderer,Hitbox hitbox,String idleanimation,int idleticks) {
		this.renderer = renderer;
		this.idleanimation = idleanimation.toLowerCase();
		this.idleticksmax = idleticks;
		this.direction = EnumSkinDirection.RIGHT;
		this.settings = game.getSettings();
		this.hitbox = hitbox;
	}
	
	/*
	 * Loads some animations
	 * */
	public void loadAnimations(String name,String path,int pixels) {
		this.animations.put(name,this.renderer.loadImagesSeperatedBy(this.hitbox,path,pixels));
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
	public void renderAt(int x,int y) {
		
		//Gets the width and height
		int w = (int) (this.hitbox.getFixedX());
		int h = (int) (this.hitbox.getFixedY());
		
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.window.fill(Color.DARK_GRAY.getRGB());
			//Checks the render direction
			if(this.direction.equals(EnumSkinDirection.RIGHT)) {
				//Renders normal
				this.renderer.window.image(this.currentImage,x-w/2,y-h,w,h);
			}else {
				//Renders reverse
				this.renderer.window.translate(w, 0);
				this.renderer.window.scale(-1,1);
				this.renderer.window.image(this.currentImage,-x+w/2,y-h,w,h);
			}
		}
		//Closes the matrix
		this.renderer.pop();
		
		if(this.settings.debugrendering) {
			//Opens the debug matrix
			this.renderer.push();
			{				
				//Renders the cirle
				this.renderer.renderCirle(x, y, 5, Color.red.getRGB());
				
				//Renders the outline
				this.renderer.renderOutline(x-w/2, y-h, w, h, Color.red.getRGB(),1);
			}
			//Closes the debug matrix
			this.renderer.pop();
		}
		
		//Checks if debug rendering is enabled
		if(this.settings.debugrendering) {
			//Renders the exact x/y cordinate
			this.renderer.window.fill(Color.red.getRGB());
			this.renderer.window.circle(x, y, 5);
		}
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
	
//	/*
//	 * Returns the skin height
//	 * */
//	public int getHeight() {
//		return (int) (this.asImage().height*this.size);
//	}
//	
//	/*
//	 * Returns the skin weight
//	 * */
//	public int getWidth() {
//		return (int) (this.asImage().width*this.size);
//	}
//	
//	/*
//	 * Returns the current skin
//	 * */
//	public PImage asImage() {
//		return this.currentImage;
//	}
	
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
	public final void setSkinDirection(EnumSkinDirection direction) {
		this.direction = direction;
	}
	
	/*
	 * Returns the direction facing
	 * */
	public EnumSkinDirection getSkinDirection() {
		return this.direction;
	}

//	/**
//	 * @return the size
//	 */
//	public final double getSize() {
//		return size;
//	}
//
//	/**
//	 * @param size the size to set
//	 */
//	public final void setSize(double size) {
//		this.size = size;
//	}

	public enum EnumSkinDirection{
		RIGHT,LEFT;
	}
}
