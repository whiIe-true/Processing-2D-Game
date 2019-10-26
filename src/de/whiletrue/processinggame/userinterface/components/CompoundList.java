package de.whiletrue.processinggame.userinterface.components;

import java.awt.Color;
import java.util.Arrays;

import de.whiletrue.processinggame.userinterface.GuiComponent;
import processing.event.MouseEvent;

public class CompoundList extends GuiComponent{

	private int x,y,width,height,appletWidth;
	private boolean out = false;
	private String[] list = {"Test1","Test2","Test3"};
	private ClickApplet onclick;
	private String text;
	
	public CompoundList(int x,int y,int width,int height,String text,ClickApplet onclick,String... list) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.onclick = onclick;
		this.list = list;
		this.appletWidth = Arrays.stream(list)
				.map(i->this.renderer.getTextWidth(i, height-8))
				.reduce((i,i1)->i>i1?i:i1)
				.get()+5;
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		
		boolean hovermain = this.isHoveringMain(mouseX, mouseY);
		
		//Sets the new hovering state
		this.out = hovermain||(this.isHoveringApplet(mouseX, mouseY));
		
		//Gets the color the button should have depending on the button is clicked or hovered
		Color color = new Color(hovermain?0x757575:0x595959);
		
		//Renders the main button
		this.renderer.renderRectWithStroke(this.x, this.y, this.width, this.height, color.getRGB(),0,2);
		//Renders the text
		this.renderer.renderTextCenter(this.text, this.x+this.width/2, this.y, this.height-8, null, Color.WHITE.getRGB());
		
		//Renders the out icon
		this.renderer.renderTextCenter(">", this.x+this.width-this.height/2, this.y, this.height-8, null, Color.white.getRGB());
		
		//Checks if the applet is out
		if(this.out)
			//Iterates over every applet button
			for(int i = 0; i < this.list.length; i++) {
				boolean hoveringApplet = this.isHoveringApplet(mouseX, mouseY, i);
				//Generates the applet button color
				Color appletColor = new Color(mousePressed&&hoveringApplet?0xFB3030:
					hoveringApplet?0x757575:
						0x595959);
				//Renders the applet
				this.renderer.renderRectWithStroke(this.x+this.width, this.y-this.height*i, this.appletWidth, this.height, appletColor.getRGB(), 0, 2);
				//Renders the text
				this.renderer.renderTextCenter(this.list[i], this.x+this.width+this.appletWidth/2, this.y-this.height*i, this.height-8, null, Color.white.getRGB());
			}	
	}
	
	@Override
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the applet is out
		if(!this.out)
			return;
		
		//Iterates over every applet button
		for(int i = 0; i < this.list.length; i++)
			//Checks if the click is on the applet
			if(this.isHoveringApplet(event.getX(), event.getY(), i)) {
				//Executes the onclick
				this.onclick.execute(i, event.getButton());
				return;
			}
		
	}
	
	/*
	 * Returns if the mouse is hovering over the main button
	 * */
	private final boolean isHoveringMain(int mouseX,int mouseY) {
		return mouseX >= this.x &&
				mouseX <= this.x+this.width &&
				mouseY >= this.y &&
				mouseY <= this.y+this.height;
	}
	
	/*
	 * Returns if the mouse is hovering over the applet
	 * */
	private final boolean isHoveringApplet(int mouseX,int mouseY) {
		return mouseX >= this.x+this.width&&
				mouseX <= this.x+this.width+this.appletWidth&&
				mouseY>=this.y-this.height*(this.list.length-1)&&
				mouseY<=this.y+this.height*(this.list.length-1);
	}
	
	/*
	 * Returns if the mouse is hovering over the given applet
	 * */
	private final boolean isHoveringApplet(int mouseX,int mouseY,int applet) {
		return mouseX >= this.x+this.width&&
				mouseX<=this.x+this.width+this.appletWidth&&
				mouseY>=this.y-this.height*applet&&
				mouseY<=this.y-this.height*(applet-1);
	}
	
	@FunctionalInterface
	public interface ClickApplet{
		public void execute(int id,int btn);
	}
	
	
}
