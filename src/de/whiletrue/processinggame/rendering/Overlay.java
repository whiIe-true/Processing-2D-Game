package de.whiletrue.processinggame.rendering;

import java.awt.Color;

import de.whiletrue.processinggame.game.Game;

public class Overlay {

	private Renderer renderer;
	
	public Overlay(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderTextWithShadow("X> "+Game.getInstance().getPlayer().getPhysics().getX(), 5, 5, 35, null, Color.red.getRGB());
			this.renderer.renderTextWithShadow("Y> "+Game.getInstance().getPlayer().getPhysics().getY(), 5, 5+35*1, 35, null, Color.red.getRGB());
		}
		//Closes the matrix
		this.renderer.pop();
	}
}
