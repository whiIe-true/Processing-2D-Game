package de.whiletrue.processinggame.rendering;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.rendering.animations.AnimationFrame;
import de.whiletrue.processinggame.utils.Item;
import processing.core.PImage;

public class Overlay {

	private Renderer renderer;
	private EntityPlayer player;
	
	private AnimationFrame itemframe;
	
	public Overlay() {
		Game game = Game.getInstance();
		this.renderer = game.getRenderer();
		this.player = game.getPlayer();
		
		//Loads the itemframe
		this.itemframe = new AnimationFrame(renderer.loadImage("rsc/overlay/itemframe.png"));
		this.itemframe.updateScale(2);
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		//Opens the matrix
		this.renderer.push();
		{
			//Renders the itemframe
			itemframe:{
				//Render the background
				this.renderer.renderImage(this.itemframe.getImage(), 10, 10);
				
				Item holding = this.player.getItemHolding();
				
				//Checks if the player holds an item
				if(holding==null)
					break itemframe;
				
				PImage itm = holding.getAnimation().getCurrentFrame().getImage();
				
				int x = this.itemframe.getImage().width/2-itm.width/2,
					y = this.itemframe.getImage().height/2-itm.height/2;
				
				//Renders the item
				this.renderer.renderImage(itm, 10+x, 10+y);
			}
			
		}
		//Closes the matrix
		this.renderer.pop();
	}
}
