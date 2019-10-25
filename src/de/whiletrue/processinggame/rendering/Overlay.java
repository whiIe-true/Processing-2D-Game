package de.whiletrue.processinggame.rendering;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.entitys.living.Player;
import de.whiletrue.processinggame.rendering.animations.AnimationFrame;

public class Overlay {

	private Renderer renderer;
	private Player player;
	
	private AnimationFrame itemframe;
	
	public Overlay(Renderer renderer) {
		this.renderer = renderer;
		this.player = Game.getInstance().getPlayer();
		
		//Loads the itemframe
		this.itemframe = new AnimationFrame(renderer.loadImage("rsc/overlay/itemframe.png"));
		this.itemframe.updateScale(4);
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderImage(this.itemframe.getImage(), 10, 10);
		}
		//Closes the matrix
		this.renderer.pop();
	}
}
