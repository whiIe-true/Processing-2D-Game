package de.whiletrue.processinggame.rendering;

import de.whiletrue.processinggame.utils.Hitbox;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Renderer {

	public PApplet window;
	
	public Renderer(PApplet window) {
		this.window = window;
	}
	
	/*
	 * Loads subimages from one image
	 * */
	public PImage[] loadImagesSeperatedBy(Hitbox hitbox,String path,int images) {
		
		//Loades the full image
		PImage img = this.window.loadImage(path);

		//Calculates the width of every frame
		int imageLength = img.width/images;
		
		//Creates the array with all subimages
		PImage[] subimages = new PImage[images];
		
		//Goes through every subimage
		for(int i = 0; i < images; i++) {
			//Cutes the subimage
			PImage clone = img.get(i*imageLength, 0, imageLength, img.height);
			//Resizes the image to hit the hitbox
			clone.resize(hitbox.getRawX(), hitbox.getRawY());
			//Adds the image
			subimages[i] = clone;
		}
		return subimages;
	}
	
	/*
	 * Opens a matrix
	 * */
	public void push() {
		this.window.pushMatrix();
	}
	
	/*
	 * Closes a matrix
	 * */
	public void pop() {
		this.window.popMatrix();
	}
	
	/*
	 * Renders a cirle
	 * */
	public void renderCirle(float x,float y,float radius,int color) {
		this.window.noStroke();
		this.window.fill(color);
		this.window.circle(x, y, radius);
	}
	
	/*
	 * Renders a cirle
	 * */
	public void renderCirleWithStroke(float x,float y,float radius,int color,int strokeColor,float strokesize) {
		this.window.stroke(strokeColor);
		this.window.strokeWeight(strokesize);
		this.window.fill(color);
		this.window.circle(x, y, radius);
	}
	
	/*
	 * Renders the given text on the screen
	 * */
	public void renderText(String text,float x,float y,float size,PFont font,int color) {
		this.window.fill(color);
		this.window.textAlign(PApplet.LEFT,PApplet.TOP);
		this.window.textSize(size);
		
		//Checks if the font is defined
		if(font!=null)
			this.window.textFont(font,size);
		

		//Renders the text
		this.window.text(text, x, y);
	}
	
	/*
	 * Renders the given text on the screen
	 * */
	public void renderTextCenter(String text,float x,float y,float size,PFont font,int color) {
		this.window.fill(color);
		this.window.textAlign(PApplet.CENTER,PApplet.TOP);
		this.window.textSize(size);
		
		//Checks if the font is defined
		if(font!=null)
			this.window.textFont(font,size);
		

		//Renders the text
		this.window.text(text, x, y);
	}
	
	/*
	 * Renders the given rect with a stroke
	 * */
	public void renderRectWithStroke(float x,float y,float width,float height,int color,int colorStroke,float strokesize) {
		this.renderRectWithStroke(x, y, width, height, color, 200, colorStroke, strokesize);
	}
	
	/*
	 * Renders the given rect with a stroke
	 * */
	public void renderRectWithStroke(float x,float y,float width,float height,int color,int transparency,int colorStroke,float strokesize) {
		this.window.stroke(colorStroke);
		this.window.strokeWeight(strokesize);
		this.window.fill(color,transparency);
		this.window.rect(x, y, width, height);
	}
	
	/*
	 * Renders a rect
	 * */
	public void renderRect(float x,float y,float width,float height,int color) {
		this.renderRect(x, y, width, height, color, 200);
	}
	
	/*
	 * Renders a rect
	 * */
	public void renderRect(float x,float y,float width,float height,int color,int transparency) {
		this.window.noStroke();
		this.window.fill(color,transparency);
		this.window.rect(x, y, width, height);
	}
	
	/*
	 * Renders a rect
	 * */
	public void renderOutline(float x,float y,float width,float height,int color,float weight) {
		this.window.noFill();
		this.window.stroke(color);
		this.window.strokeWeight(weight);
		this.window.rect(x, y, width, height);
	}
	
	/*
	 * Renders a line
	 * */
	public void renderLine(float x,float y,float x2,float y2,int color,float width) {
		this.window.stroke(color);
		this.window.strokeWeight(width);
		this.window.line(x, y, x2, y2);
	}
}
