package de.whiletrue.processinggame.userinterface;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;

public abstract class GuiComponent implements GuiEvents{
	
	protected Game game;
	protected Renderer renderer;
	
	public GuiComponent() {
		this.game = Game.getInstance();
		this.renderer = this.game.getRenderer();
	}
	
}
