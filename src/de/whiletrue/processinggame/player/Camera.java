package de.whiletrue.processinggame.player;

import de.whiletrue.processinggame.rendering.Renderer;
import processing.core.PApplet;

public class Camera {
	
	private PApplet window;
	
	private int x,y;
	
	public Camera() {
		this.window = Renderer.getInstance().window;
	}
	
	/*
	 * Changes the perspective depending on the cameras position
	 * */
	public void doRender() {
		//Changes the window to the players x and y
		this.window.translate(this.window.width/2-this.x, this.window.height/2-this.y);
	}
	
	/*
	 * Updates the camera
	 * */
	public void update(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Updates the camera by adding the values
	 * */
	public void addUpdate(int x,int y) {
		this.x+=x;
		this.y+=y;
	}
	
	/**
	 * Gets the camera position
	 * @returns int[] {x,y}
	 * */
	public int[] getPosition() {
		return new int[] {this.x,this.y};
	}
	
}

