package de.whiletrue.processinggame.game.ingame.guis;

import java.awt.Color;

import de.whiletrue.processinggame.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;

public class GuiDeathscreen extends DefaultGui{

	private StateIngame state;
	
	public GuiDeathscreen(StateIngame state) {
		super(false);
		this.state = state;
	}

	@Override
	public GuiComponent[] addComponents() {
		
		EntityPlayer player = this.state.getPlayer();
		
		//Shorts the width and height variable
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		CompoundButton respawn = new CompoundButton(w/2-200, h/2+h/6, 400, 50, id->{
			if(id!=-1) {
				//Lets the player respawn
				player.respawn();
				this.state.openGui(null);
			}
			return "Respawn";
		});
		return new GuiComponent[] {respawn};
	}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		//Renders the red screen
		this.renderer.renderRect(0, 0, w, h, new Color(255,0,0,150).getRGB());
		
		//Deathtext
		String text = "Du bist gestorben";
		
		//Renders the you are dead text
		this.renderer.renderTextWithShadow(text, w/2-this.renderer.getTextWidth(text, 60)/2, h/4, 60, null, Color.white.getRGB());

		super.handleRender(mouseX, mouseY, mousePressed);
	}
	
	
}
