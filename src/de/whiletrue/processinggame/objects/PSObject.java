package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.rendering.Renderer;

public abstract class PSObject{
	
	protected StateIngame state;
	protected Renderer renderer;
	
	public PSObject() {
		this.state = (StateIngame) Game.getInstance().getState();
		this.renderer = Renderer.getInstance();
	}
	
	public void handleTick() {}
	
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {}	
	
}
