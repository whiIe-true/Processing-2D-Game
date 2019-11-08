package de.whiletrue.processinggame.objects.objects;

import java.awt.Color;

import de.whiletrue.processinggame.objects.PSObject;

public class ObjectWall extends PSObject{
	
	private int x,y,width;
	
	@Override
	public void init(LoadFrame loadframe) {
		this.x = loadframe.getInt("x");
		this.y = loadframe.getInt("y");
		this.width = loadframe.getInt("width");
	}
	
	@Override
	public LoadFrame save() {
		LoadFrame holder = new LoadFrame();
		
		//Sets the values
		holder.setInt("x", this.x);
		holder.setInt("y", this.y);
		holder.setInt("width", this.width);
		
		return holder;
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderRectWithStroke(this.x, this.y, this.width, 10, Color.darkGray.getRGB(), 0, 1);
		}
		//Closes the maxtrix
		this.renderer.pop();
	}

	@Override
	public void handleTick() {}

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
	 * @return the width
	 */
	public final int getWidth() {
		return this.width;
	}
	
}
