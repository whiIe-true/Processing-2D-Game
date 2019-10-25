package de.whiletrue.processinggame.rendering.userinterface.components;

import java.awt.Color;
import java.util.function.Function;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.rendering.userinterface.GuiComponent;
import processing.event.MouseEvent;

public class CompoundCheckbox extends GuiComponent{

	private int x,y,width,height;
	private Function<Boolean,String> onclick;
	private boolean checked;
	private String text;
	
	public CompoundCheckbox(Game game,Renderer renderer,int x,int y,boolean checked,Function<Boolean,String> onclick) {
		this(game,renderer,x,y,50,50,checked,onclick);
	}
	
	public CompoundCheckbox(Game game,Renderer renderer,int x,int y,int width,int height,boolean checked,Function<Boolean,String> onclick) {
		super(game,renderer);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.checked = checked;
		this.onclick = onclick;
		this.text = this.onclick.apply(checked);
	}
	
	@Override
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the button is hovered
		if(this.isHovering(event.getX(), event.getY())) {
			//Sets the new state
			this.checked = !this.checked;
			//Executes the onclick with the check state as argument
			this.text = this.onclick.apply(this.checked);
			
		}
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Opens a new layers
		this.renderer.push();
		{		
			
			//Checks if the box is checked
			if(this.checked) {
				this.renderer.renderLine(this.x, this.y, this.x+this.width, this.y+this.height, Color.red.getRGB(), 3);
				this.renderer.renderLine(this.x+this.width, this.y, this.x, this.y+this.height, Color.red.getRGB(), 3);
			}

			//Renders the box
			this.renderer.renderOutline(this.x, this.y, this.width, this.height, Color.black.getRGB(),3);
			
			//Renders the text
			this.renderer.renderText(this.text, this.x+this.width+5, this.y, this.height-8, null, Color.white.getRGB());
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

	/**
	 * @return the x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public final void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the checked
	 */
	public final boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public final void setChecked(boolean checked) {
		this.checked = checked;
	}

}
