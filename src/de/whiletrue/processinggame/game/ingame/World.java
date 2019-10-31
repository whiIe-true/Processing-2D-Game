package de.whiletrue.processinggame.game.ingame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.objects.PSObject;
import de.whiletrue.processinggame.objects.entitys.living.EntityPlayer;
import de.whiletrue.processinggame.utils.Items;

public class World {

	private final int killHeight = 1000,killWeight = 5000;
	
	private EntityPlayer player;
	
	private List<PSObject> objects = new ArrayList<PSObject>(),
			soonAdd = new ArrayList<PSObject>(),
			soonRemove = new ArrayList<PSObject>();
	
	public World() {
		//Gets some references
		this.player = ((StateIngame)Game.getInstance().getState()).getPlayer();
		
		//Registers the items
		Items.init();
	}
	
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {
		//Draws the objects
		this.objects.forEach(i->i.handleRender(mouseX, mouseY, mousePressed));
		
		//Draws the player
		this.player.handleRender(mouseX, mouseY, mousePressed);
	}
	
	public void handleTick() {

		//Checks if the player is lower than the lowest possable position
		if(this.player.getPhysics().getY()>this.killHeight)
			//Damages the player
			this.player.damage(20,0,0);
		
		//Removes all dead entity
		this.soonRemove.addAll(this.objects.stream().filter(i->i instanceof PSEntityLiving&&((PSEntityLiving)i).isDead()).collect(Collectors.toList()));
		//Removes all entitys that are to low
		this.soonRemove.addAll(this.objects.stream().filter(i->i instanceof PSEntity&&((PSEntity)i).getPhysics().getY()>this.killHeight).collect(Collectors.toList()));
		//Removes all entitys that are to far away
		this.soonRemove.addAll(this.objects.stream().filter(i-> i instanceof PSEntity).map(i->(PSEntity)i).filter(i->i.getPhysics().getX()>this.killWeight||i.getPhysics().getX()<-this.killWeight).collect(Collectors.toList()));
		
		//Adds and removes all objects
		this.objects.addAll(this.soonAdd);
		this.objects.removeAll(this.soonRemove);
		
		//Clears both lists
		this.soonAdd.clear();
		this.soonRemove.clear();
		
		//Handles the tick for the player
		this.player.handleTick();
		
		//Handles the tick for the objects
		this.objects.forEach(i->i.handleTick());
	}
	
	/*
	 * Spawns an object in the world
	 * */
	public void spawn(PSObject object) {
		this.soonAdd.add(object);
	}
	
	/*
	 * Removes an object from the world
	 * */
	public void kill(PSObject object) {
		this.soonRemove.add(object);
	}
	
	/**
	 * @return the objects
	 */
	public final List<PSObject> getObjects() {
		return this.objects;
	}
	
	/*
	 * @returns the player
	 * */
	public EntityPlayer getPlayer() {
		return this.player;
	}
}
