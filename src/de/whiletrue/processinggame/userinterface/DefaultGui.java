package de.whiletrue.processinggame.userinterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.rendering.Renderer;
import processing.event.MouseEvent;

public abstract class DefaultGui implements GuiEvents{

	private List<GuiComponent> components = new ArrayList<>();
	protected Game game;
	protected Renderer renderer;
	
	public DefaultGui() {
		this.game = Game.getInstance();
		this.renderer = this.game.getRenderer();
	}
	
	/*
	 * Called when the gui opens
	 * */
	public final void init() {
		//Gets the componets
		GuiComponent[] comps = this.addComponents();
		//Checks if there are given
		if(comps!=null)
			//Adds theme to the list
			this.components.addAll(Arrays.asList(comps));
	}
	
	/*
	 * This is the method where all compounds should be added
	 * */
	public abstract GuiComponent[] addComponents();
	
	@Override
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {
		this.components.forEach(i->i.handleRender(mouseX, mouseY, mousePressed));
	}
	@Override
	public void handleMouseClicked(MouseEvent event) {
		this.components.forEach(i->i.handleMouseClicked(event));
	}
	@Override
	public void handleTick() {
		this.components.forEach(i->i.handleTick());
	}
	@Override
	public void handleMouseDragged(MouseEvent event) {
		this.components.forEach(i->i.handleMouseDragged(event));
	}
	@Override
	public void handleMouseMoved(MouseEvent event) {
		this.components.forEach(i->i.handleMouseMoved(event));
	}
	@Override
	public void handleMousePressed(MouseEvent event) {
		this.components.forEach(i->i.handleMousePressed(event));
	}
	@Override
	public void handleMouseReleased(MouseEvent event) {
		this.components.forEach(i->i.handleMouseReleased(event));
	}
	
}
