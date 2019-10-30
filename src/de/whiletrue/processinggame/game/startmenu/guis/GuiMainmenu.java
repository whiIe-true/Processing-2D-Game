package de.whiletrue.processinggame.game.startmenu.guis;

import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;

public class GuiMainmenu extends DefaultGui{
	
	public GuiMainmenu() {
		super(false);
	}

	@Override
	public GuiComponent[] addComponents() {
		//Shorts some variables
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		CompoundButton startgame = new CompoundButton(w/2-300, h/2-100, 600, 120, btn->{
			this.game.changeState(new StateIngame());
			return "Start Game";
		});
		return new CompoundButton[] {startgame};
	}

}
