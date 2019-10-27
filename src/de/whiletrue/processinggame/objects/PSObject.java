package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;

public abstract class PSObject{
	
	protected Game game;
	protected Renderer renderer;
	
	public PSObject() {
		this.game = Game.getInstance();
		this.renderer = this.game.getRenderer();
	}
	
	public void handleTick() {}
	
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {}	
	
}
