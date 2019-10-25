package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.GameEvents;

public abstract class PSObject implements GameEvents{
	
	protected Game game;
	protected Renderer renderer;
	
	public PSObject(Game game,Renderer renderer) {
		this.game = game;
		this.renderer = renderer;
	}
	
}
