package de.whiletrue.processinggame.game.ingame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.PSEntityLiving;
import de.whiletrue.processinggame.objects.PSObject;
import de.whiletrue.processinggame.objects.PSObject.LoadFrame;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
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
		//Removes all items, that are none
		this.soonRemove.addAll(this.objects.stream().filter(i->i instanceof EntityItem).map(i->(EntityItem)i).filter(i->i.getItem().equals(Items.NONE)).collect(Collectors.toList()));
		
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
	 * Spawns the given entity with the given parameters
	 * The parameters are given like key, argument, key, argument
	 * */
	public Optional<PSEntity> spawnEntity(Class<? extends PSEntity> clazz,Object... parameters){
		//Checks if the parameters are valid
		if(parameters.length%2!=0)
			return Optional.empty();
		
		//Creates the loadframe
		LoadFrame lf = new LoadFrame();

		//Iterates over every key/value pair
		for(int i = 0; i < parameters.length; i+=2) {
			//Checks if the key is valid
			if(!(parameters[i] instanceof String))
				return Optional.empty();

			//Defines the key and value
			String key = (String) parameters[i];
			Object value = parameters[i+1];
			
			//TODO FIX THIS CRAP
			if(parameters[i+1].getClass().equals(String.class))
				lf.setString(key, (String) value);

			else if(parameters[i+1].getClass().equals(Integer.class))
				lf.setInt(key, (Integer) value);

			else if(parameters[i+1].getClass().equals(Boolean.class))
				lf.setBool(key, (Boolean) value);

			else if(parameters[i+1].getClass().equals(Double.class))
				lf.setDouble(key, (Double) value);

			else if(parameters[i+1].getClass().equals(Items.class))
				lf.setItem(key, (Items) value);
			else
				return Optional.empty();
		}
		
		//Trys to create the entity
		try {
			//Creates the entity
			PSEntity enti = clazz.newInstance();
			//Inits the entity
			enti.init(lf);
			//Spawns the entity
			this.spawn(enti);
			return Optional.of(enti);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Returns error
		return Optional.empty();
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
