package de.whiletrue.processinggame.userinterface.components;

import java.awt.Color;
import java.util.function.Function;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import processing.event.MouseEvent;

public class CompoundButton extends GuiComponent{

	private int x,y,width,height;
	private String text;
	private Function<Integer,String> onclick;
	
	public CompoundButton(Game game,Renderer renderer,int x,int y,int width,int height,Function<Integer,String> onclick) {
		super(game,renderer);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.onclick = onclick;
		this.text = onclick.apply(-1);
	}
	
	@Override
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the button is hovered
		if(this.isHovering(event.getX(), event.getY()))
			//Executes the onclick with the button as argument
			this.text = this.onclick.apply(event.getButton());
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Gets the color the button should have depending on the button is clicked or hovered
		Color color = new Color(mousePressed&&this.isHovering(mouseX, mouseY)?0xFB3030:
			this.isHovering(mouseX, mouseY)?0x757575:
				0x595959);
		
		//Opens a new layers
		this.renderer.push();
		{			
			//Renders the button
			this.renderer.renderRectWithStroke(this.x, this.y, this.width, this.height, color.getRGB(), Color.black.getRGB(), 3);

			//Renders the text
			this.renderer.renderTextCenter(this.text, this.x+this.width/2, this.y, this.height-8, null, Color.white.getRGB());
		}
		//Removes the new layer
		this.renderer.pop();
	}
	
	/*
	 * Returns if the mouse is hovering over the button
	 * */
	private final boolean isHovering(int mouseX,int mouseY) {
		return mouseX >= this.x &&
				mouseX <= this.x+this.width &&
				mouseY >= this.y &&
				mouseY <= this.y+this.height;
	}
	
}
