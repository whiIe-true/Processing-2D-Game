package de.whiletrue.processinggame.rendering;

import processing.core.PApplet;
import processing.core.PFont;

public class Fonts {

	public static void init(PApplet window) {
		//Loads all fonts
		Consolas = window.createFont("rsc/fonts/Consolas.ttf",60);
	}
	
	public static PFont Consolas;
	
}
