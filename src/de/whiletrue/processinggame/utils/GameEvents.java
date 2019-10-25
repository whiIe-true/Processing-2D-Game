package de.whiletrue.processinggame.utils;

import processing.event.MouseEvent;

public interface GameEvents {

	public default void handleMouseClicked(MouseEvent event) {}
	public default void handleMouseDragged(MouseEvent event) {}
	public default void handleMouseMoved(MouseEvent event) {}
	public default void handleMouseReleased(MouseEvent event) {}
	public default void handleMousePressed(MouseEvent event) {}

	public default void handleTick() {}
	
	public default void handleRender(int mouseX,int mouseY,boolean mousePressed) {}	
}
