package de.whiletrue.processinggame.rendering.userinterface;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.GameEvents;

public abstract class GuiComponent implements GameEvents{
	
	protected Game game;
	protected Renderer renderer;
	
	public GuiComponent(Game game,Renderer renderer) {
		this.game = game;
		this.renderer = renderer;
	}
	
}
