package de.whiletrue.processinggame.objects.objects;

import java.awt.Color;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.PSObject;
import de.whiletrue.processinggame.rendering.Renderer;

public class ObjectWall extends PSObject{
	
	private int x,y,width;
	
	public ObjectWall(Game game,Renderer renderer,int x,int y,int width) {
		super(game,renderer);
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
	}

	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderRectWithStroke(this.x, this.y, this.width, 10, Color.darkGray.getRGB(), 0, 1);
		}
		//Closes the maxtrix
		this.renderer.pop();
	}

	@Override
	public void handleTick() {}

	/**
	 * @return the x
	 */
	public final int getX() {
		return this.x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return this.y;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return this.width;
	}
	
}
