package de.whiletrue.processinggame.game.startmenu.guis;

import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.game.startmenu.ParticleEngine;
import de.whiletrue.processinggame.game.worldbuilder.StateWorldbuilder;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;

public class GuiMainmenu extends DefaultGui{
	
	private ParticleEngine particleengine;
	
	public GuiMainmenu() {
		super(false);
		this.particleengine = new ParticleEngine(this.renderer);
	}

	@Override
	public GuiComponent[] addComponents() {
		//Shorts some variables
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		CompoundButton startgame = new CompoundButton(w/2-300, h/2-100, 600, 50, btn->{
			if(btn!=-1)
				this.game.changeState(new StateIngame());
			return "Start Game";
		});
		
		CompoundButton worldbuilder = new CompoundButton(w/2-300, h/2, 600, 50, btn->{
			if(btn!=-1)
				this.game.changeState(new StateWorldbuilder());
			return "Worldbuilder";
		});
		
		return new CompoundButton[] {startgame, worldbuilder};
	}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Renders the particles
		this.particleengine.handleRender(mouseX, mouseY, mousePressed);
		
		super.handleRender(mouseX, mouseY, mousePressed);
	}
	
	@Override
	public void handleTick() {
		//Updates the particles
		this.particleengine.handleTick();
		super.handleTick();
	}
}
