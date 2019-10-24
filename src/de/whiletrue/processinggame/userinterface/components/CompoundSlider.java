package de.whiletrue.processinggame.userinterface.components;

import java.awt.Color;
import java.util.function.Function;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import processing.event.MouseEvent;

public class CompoundSlider extends GuiComponent{

	private int x,y,width,height,min,max,currentValue;
	private double state=0;
	private String text;
	private Function<Integer,String> onchange;
	
	private boolean draged = false;
	
	public CompoundSlider(Game game,Renderer renderer,int x,int y,int width,int height,int min,int max,int value,Function<Integer,String> onchange) {
		super(game,renderer);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		this.onchange = onchange;
		
		this.setValue(value);

		this.text = this.onchange.apply(this.currentValue);
	}
	
	public CompoundSlider(Game game,Renderer renderer,int x,int y,int min,int max,int state,Function<Integer,String> onchange) {
		this(game,renderer,x,y,200,40,min,max,state,onchange);
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Opens the maxtrix
		this.renderer.push();
		{
			//Renders the filled
			this.renderer.renderRectWithStroke(this.x, this.y, (float) (this.width*this.state), this.height, new Color(0xFB3030).getRGB(), 0, 1);
			//Renders the empty
			this.renderer.renderRectWithStroke((float) (this.x+this.width*this.state), this.y, (float) (this.width-this.width*this.state), this.height, new Color(0x595959).getRGB(), 0, 1);
			//Renders the text
			this.renderer.renderTextCenter(this.text, this.x+this.width/2, this.y, this.height-14, null, Color.white.getRGB());
		}
		//Closes the matrix
		this.renderer.pop();
		
		super.handleRender(mouseX, mouseY, mousePressed);
	}
	
	@Override
	public void handleMouseReleased(MouseEvent event) {
		this.draged=false;
	}
	
	@Override
	public void handleMouseDragged(MouseEvent event) {
		if(this.draged)
			this.setSlider(event.getX());
	}
	
	@Override
	public void handleMousePressed(MouseEvent event) {
		if(this.isHovering(event.getX(), event.getY())) {
			this.draged=true;
			this.setSlider(event.getX());
		}
	}

	/*
	 * Sets the slider depending on the current mouse state
	 * */
	private void setSlider(int mouseX) {
		double calc = (double)(mouseX-this.x)/this.width;
		//Checks if the new state is valid
		if(calc<0||calc>1)
			return;
		
		//Calculates the percentual state of the slider
		this.setState(calc);
		//Executes the onchange callback and sets the new text
		this.text = this.onchange.apply(this.currentValue);
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

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return this.height;
	}

	/**
	 * @return the min
	 */
	public final int getMin() {
		return this.min;
	}

	/**
	 * @return the max
	 */
	public final int getMax() {
		return this.max;
	}

	/**
	 * @return the currentValue
	 */
	public final int getValue() {
		return this.currentValue;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * @param width the width to set
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public final void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param min the min to set
	 */
	public final void setMin(int min) {
		this.min = min;
	}

	/**
	 * @param max the max to set
	 */
	public final void setMax(int max) {
		this.max = max;
	}

	/**
	 * @param text the text to set
	 */
	public final void setText(String text) {
		this.text = text;
	}
	
	/*
	 * @param state the state to set
	 * */
	public void setState(double state) {
		this.state = state;
		this.currentValue = (int)((this.max-this.min)*state+this.min);
	}
	
	/*
	 * @param value the value to set
	 * */
	public void setValue(int value) {
		this.currentValue = value;
		this.state = (double)(value-this.min)/(this.max-this.min);
	}
}
