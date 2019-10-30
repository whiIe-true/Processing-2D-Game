package de.whiletrue.processinggame.rendering;

import java.awt.Color;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.rendering.animations.AnimationFrame;
import de.whiletrue.processinggame.utils.Item;
import processing.core.PImage;

public class Overlay {

	private Renderer renderer;
	private EntityPlayer player;
	
	private Game game;
	
	private AnimationFrame itemframe;
	
	public Overlay() {
		this.game = Game.getInstance();
		this.renderer = this.game.getRenderer();
		this.player = this.game.getPlayer();
		
		//Loads the itemframe
		this.itemframe = new AnimationFrame(this.renderer.loadImage("rsc/overlay/itemframe.png"));
		this.itemframe.updateScale(2);
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mouseClicked) {
		
		int w = this.game.getWidth();
		
		//Opens the matrix
		this.renderer.push();
		{
			//Item and Itemframe
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
