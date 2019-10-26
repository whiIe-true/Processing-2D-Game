package de.whiletrue.processinggame;

import processing.core.PApplet;

public class Camera {
	
	private int x,y;
	
	/*
	 * Changes the perspective depending on the cameras position
	 * */
	public void doRender(PApplet window) {
		//Changes the window to the players x and y
		window.translate(window.width/2-this.x, window.height/2-this.y);
	}
	
	/*
	 * Updates the camera
	 * */
	public void update(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
}

