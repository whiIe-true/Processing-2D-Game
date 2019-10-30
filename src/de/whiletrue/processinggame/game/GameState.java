package de.whiletrue.processinggame.game;

import de.whiletrue.processinggame.userinterface.DefaultGui;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class GameState {
	
	private DefaultGui openGui;
	
	public abstract void init();
	
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {
		//Checks if a gui is open
		if(!this.isGuiOpen())
			return;
		//Draws the gui
		this.openGui.handleRender(mouseX, mouseY, mousePressed);
	}
	public void handleTick() {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		
		//Handles the tick for the gui
		this.openGui.handleTick();
	}
	public void handleMouseClicked(MouseEvent event) {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		
		//Sends the event to the gui
		this.openGui.handleMouseClicked(event);
	}
	public void handleMouseDragged(MouseEvent event) {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		//Sends the event to the gui
		this.openGui.handleMouseDragged(event);
	}
	public void handleMousePressed(MouseEvent event) {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		//Sends the event to the gui
		this.openGui.handleMousePressed(event);
	}
	public void handleMouseReleased(MouseEvent event) {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		//Sends the event to the gui
		this.openGui.handleMouseReleased(event);
	}
	public void handleMouseMoved(MouseEvent event) {
		//Checks if the gui is open
		if(!this.isGuiOpen())
			return;
		
		//Sends the event to the gui
		this.openGui.handleMouseMoved(event);
	}
	public void handleKeyPressed(KeyEvent event) {}
	public void handleKeyReleased(KeyEvent event) {}
	
	/*
	 * Opens a gui
	 * */
	public final void openGui(DefaultGui gui) {
		//Checks if gui should close
		if(gui==null) {
			this.openGui=null;
			return;
		}
		//Prepares the gui
		gui.init();
		//Opens the gui
		this.openGui = gui;
	}
	
	/**
	 * @returns if the gui is open
	 * */
	public final boolean isGuiOpen() {
		return this.openGui!=null;
	}
	
	/**
	 * @return the gui
	 * */
	public DefaultGui getOpenGui() {
		return this.openGui;
	}
}
