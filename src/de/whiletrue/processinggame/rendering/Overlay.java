package de.whiletrue.processinggame.rendering;

import java.awt.Color;

import de.whiletrue.processinggame.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.rendering.animations.AnimationFrame;
import de.whiletrue.processinggame.utils.Items;
import processing.core.PImage;

public class Overlay {

	private Renderer renderer;
	private EntityPlayer player;
	
	private AnimationFrame itemframe;
	
	public Overlay() {
		this.renderer = Renderer.getInstance();
		this.player = ((StateIngame)Game.getInstance().getState()).getPlayer();
		
		//Loads the itemframe
		this.itemframe = new AnimationFrame(this.renderer.loadImage("rsc/overlay/itemframe.png"));
		this.itemframe.updateScale(2);
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		
		int w = this.renderer.window.width;
		
		//Opens the matrix
		this.renderer.push();
		{
			//Item and Itemframe
			itemframe:{
				//Render the background
				this.renderer.renderImage(this.itemframe.getImage(), 10, 10);
				
				Items holding = this.player.getItemHolding();
				
				//Checks if the player holds an item
				if(holding.equals(Items.NONE))
					break itemframe;
				
				PImage itm = holding.getAnimation().getCurrentFrame().getImage();
				
				int x = this.itemframe.getImage().width/2-itm.width/2,
					y = this.itemframe.getImage().height/2-itm.height/2;
				
				//Renders the item
				this.renderer.renderImage(itm, 10+x, 10+y);
			}
		
			//Healthbar
			{
				//Renders the healthbar empty
				this.renderer.renderRect(w/2+w/4-10, 10, w/4, 20, Color.GRAY.getRGB());
				//Renders the health that is left
				this.renderer.renderRect(w/2+w/4-10+1, 10, w/4*this.player.getHealthLeft(), 20, Color.red.getRGB());
				//Renders the outline for the health
				this.renderer.renderOutline(w/2+w/4-10, 10, w/4, 20, 0, 3);
			}
			
		}
		//Closes the matrix
		this.renderer.pop();
	}
}
