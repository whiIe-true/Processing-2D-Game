package de.whiletrue.processinggame.game.startmenu;

import de.whiletrue.processinggame.game.GameState;
import de.whiletrue.processinggame.game.startmenu.guis.GuiMainmenu;

public class StateStartMenu extends GameState{

	public StateStartMenu() {
		//Opens the default gui
		this.openGui(new GuiMainmenu());
	}

	@Override
	public void init() {}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		super.handleRender(mouseX, mouseY, mousePressed);
	}

}
