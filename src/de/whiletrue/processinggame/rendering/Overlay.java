package de.whiletrue.processinggame.rendering;

import java.awt.Color;

public class Overlay {

	private Renderer renderer;
	
	public Overlay(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderText("Version 1b", 5, 5, 35, null, Color.red.getRGB());
		}
		//Closes the matrix
		this.renderer.pop();
	}
}
