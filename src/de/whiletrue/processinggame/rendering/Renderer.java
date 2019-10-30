package de.whiletrue.processinggame.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.whiletrue.processinggame.Main;
import de.whiletrue.processinggame.rendering.animations.AnimationFrame;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Renderer {

	private static Map<String, BufferedImage> loadedImages = new HashMap<>();
	private static Renderer instance;
	
	public PApplet window;
	private BufferedImage invalidFile;
	
	public Renderer(PApplet window) {
		instance = this;
		this.window = window;
		
		//Creates the invalidFile image
		this.invalidFile = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB) {
			@Override
			public Graphics2D createGraphics() {
				Graphics2D g = super.createGraphics();
				g.setColor(Color.ORANGE);
				g.fillRect(0, 0, 8, 8);
				g.fillRect(8, 8, 8, 8);

				g.setColor(Color.CYAN);
				g.fillRect(0, 8, 8, 8);
				g.fillRect(8, 0, 8, 8);
				g.dispose();
				return g;
			}
		};
		this.invalidFile.createGraphics();
	}
	
	/*
	 * Returns the width of the given text
	 * */
	public int getTextWidth(String text,int fontsize) {
		this.window.textSize(fontsize);
		return (int)this.window.textWidth(text);
	}
	
	/*
	 * Loads a given image
	 * */
	public BufferedImage loadImage(String path) {
		//Checks if the image is still loaded
		if(loadedImages.containsKey(path))
			return loadedImages.get(path);
		
		try {
			//Workes, when not using an IDE
			return ImageIO.read(Main.class.getClassLoader().getResourceAsStream(path));
		} catch (Exception e) {
			//Workes, when debugging in IDE
			try {
				return ImageIO.read(new File(path));
			} catch (Exception e2) {
				return this.invalidFile;
			}
		}
	}
	
	/*
	 * Loads subimages from one image
	 * */
	public AnimationFrame[] loadImagesSeperatedBy(String path) {
		
		//Loades the full image
		BufferedImage img = this.loadImage(path);

		int width = img.getHeight();
		
		//Gets the rest that will be cut if the image wont fit perfectly
		int cut = img.getWidth()%width;
		
		//Gets how many subimages will be returned
		int imageLength = (img.getWidth()-cut)/width;
		
		//Creates the array with all subimages
		AnimationFrame[] frames = new AnimationFrame[imageLength];
		
		//Goes through every subimage
		for(int i = 0; i < imageLength; i++) {
			//Cutes the subimage and resize it to the given size
			BufferedImage clone = img.getSubimage(i*width, 0, width, img.getHeight());
			
			//Adds the frame
			frames[i] = new AnimationFrame(clone);
		}
		return frames;
	}
	
	/*
	 * Opens a matrix
	 * */
	public void push() {
		this.window.pushMatrix();
		this.window.push();
		this.window.pushStyle();
	}
	
	/*
	 * Closes a matrix
	 * */
	public void pop() {
		this.window.popMatrix();
		this.window.pop();
		this.window.popStyle();
	}
	
	/*
	 * Renders the given image
	 * */
	public void renderImage(PImage frame,int x,int y) {
		this.window.image(frame, x, y);
	}
	
	/*
	 * Renders the given image
	 * */
	public void renderImage(PImage frame,int x,int y,int width,int height) {
		this.window.image(frame, x, y,width,height);
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
	 * Renders the given text on the screen with a shadow
	 * */
	public void renderTextWithShadow(String text,float x,float y,float size,PFont font,int color) {

		//Defines the dark color of the text
		final int textdarking = 100;
		
		//Gets the actual colors
		int r = Math.max(0,((color&0xFF0000)>>>16)-textdarking);
		int g = Math.max(0,((color&0x00FF00)>>>8)-textdarking);
		int b = Math.max(0,((color&0x0000FF))-textdarking);
		
		//Gets the darker color
		Color darker = new Color(r,g,b);
		
		//Prepares
		this.window.textAlign(PApplet.LEFT,PApplet.TOP);
		this.window.textSize(size);
		//Sets the color
		this.window.fill(darker.getRGB());
		//Renders the shadow text
		this.window.text(text,x+2,y+2);
		//Renders the real text
		this.renderText(text, x, y, size, font, color);
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
		this.renderRect(x, y, width, height, color, 1000);
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

	/**
	 * @return the instance
	 */
	public static final Renderer getInstance() {
		return instance;
	}
}
