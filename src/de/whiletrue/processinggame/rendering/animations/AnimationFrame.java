package de.whiletrue.processinggame.rendering.animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import processing.core.PImage;

public class AnimationFrame{

	private double scale = 1;
	private BufferedImage raw;
	private PImage pimage;
	
	public AnimationFrame(BufferedImage raw) {
		this.raw = raw;
		this.pimage = new PImage(raw);
	}

	/**
	 * @param scale the scale to set
	 */
	public final void updateScale(double scale) {
		if(scale==this.scale)
			return;
		//Loads the scale
		this.scale = scale;
		
		//Gets the new size
		int x = (int) (this.raw.getWidth()*scale),y = (int) (raw.getHeight()*this.scale);
		
		//Creates the new images
		Image tmp = this.raw.getScaledInstance(x, y, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    
	    //Loads the new pimage
	    this.pimage = new PImage(dimg);
	}
	
	/**
	 * @return the scale
	 */
	public final double getScale() {
		return this.scale;
	}

	/**
	 * @return the pimage
	 */
	public final PImage getImage() {
		return this.pimage;
	}
}
