package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.GameEvents;

public abstract class PSObject implements GameEvents{
	
	protected Game game;
	protected Renderer renderer;
	
	public PSObject() {
		this.game = Game.getInstance();
		this.renderer = this.game.getRenderer();
	}
	
}
