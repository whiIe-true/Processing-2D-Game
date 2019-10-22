package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.GameObject;

public abstract class PSObject implements GameObject{
	
	protected Game game;
	protected Renderer renderer;
	
	public PSObject(Game game,Renderer renderer) {
		this.game = game;
		this.renderer = renderer;
	}
	
}
