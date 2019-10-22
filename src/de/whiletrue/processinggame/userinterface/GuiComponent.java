package de.whiletrue.processinggame.userinterface;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.GameObject;

public abstract class GuiComponent implements GameObject{
	
	protected Game game;
	protected Renderer renderer;
	
	public GuiComponent(Game game,Renderer renderer) {
		this.game = game;
		this.renderer = renderer;
	}
	
}
