package de.whiletrue.processinggame.userinterface;

import processing.event.MouseEvent;

public interface GuiEvents {

	public default void handleMouseClicked(MouseEvent event) {}
	public default void handleMouseDragged(MouseEvent event) {}
	public default void handleMouseMoved(MouseEvent event) {}
	public default void handleMouseReleased(MouseEvent event) {}
	public default void handleMousePressed(MouseEvent event) {}

	public default void handleTick() {}
	
	public default void handleRender(int mouseX,int mouseY,boolean mousePressed) {}	
}
