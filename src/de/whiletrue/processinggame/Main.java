package de.whiletrue.processinggame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import de.whiletrue.processinggame.game.Game;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Main extends PApplet{

	/*
	 * Application startup
	 * */
	
	public static void main(String[] args) {
		//Starts the processing application
		PApplet.main(Main.class);
	}
	
	private Game game;
	
	@Override
	public void settings() {
		//Starts the game
		this.game = new Game(this);
	}
	
	@Override
	public void setup() {
		//Starts the game
		this.game.init();
		
		//Helps to close the window and dont let the application running
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//Closes the game
				game.closeGame();
			}
		});
	}
	
	@Override
	public void draw() {
		this.game.handleRender();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		this.game.handleKeyPressed(event);
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		this.game.handleKeyReleased(event);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		this.game.handleMouseClicked(event);
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		this.game.handleMouseDragged(event);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		this.game.handleMousePressed(event);
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		this.game.handleMouseReleased(event);
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		this.game.handleMouseMoved(event);
	}
	
}
