package de.whiletrue.processinggame.game.ingame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import de.whiletrue.processinggame.objects.PSObject;
import de.whiletrue.processinggame.objects.entitys.EntityChest;
import de.whiletrue.processinggame.objects.entitys.EntityFireball;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
import de.whiletrue.processinggame.objects.entitys.living.EntitySlime;
import de.whiletrue.processinggame.objects.objects.ObjectWall;
import de.whiletrue.processinggame.utils.Item;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class WorldLoader {
	
	private static WorldLoader instance;
	
	public WorldLoader() {
		instance = this;
	}
	
	public World loadWorld(File inputWorldFile) {
		
		//Converts the world file into a json
		JSONArray object = this.loadJsonFromFile(inputWorldFile);
		
		//Creates the world object
		World w = new World();
		
		//Iterates over all keys from the object
		for(int i = 0; i < object.size(); i++) {
			//Gets the object to spawn
			JSONObject entity = object.getJSONObject(i);
			
			String action = entity.getString("action");
			switch (action) {
			//EntitySpawn
			case "es":
				this.spawnEntityFrom(w,entity);
				break;
			//PlayerPosition
			case "pp":
				w.getPlayer().getPhysics().teleport(entity.getInt("x"), entity.getInt("y"));
				break;
			}
		}
		
		return w;
	}
	
	/*
	 * Returns a jsonobject from a file
	 * */
	private JSONArray loadJsonFromFile(File file) {
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String out="",ln;
			
			while((ln=br.readLine())!=null)
				out+=ln;
			
			return JSONArray.parse(out);
		} catch (Exception e) {
			e.printStackTrace();
			//Returns a empty json object
			return new JSONArray();
		}
	}
	
	/*
	 * Spawnes an entity or object in the world
	 * */
	private void spawnEntityFrom(World w,JSONObject entity) {
		try {
			//Gets the real object
			Optional<PSObject> tospawn = this.getObjectFromJson(entity);
			//Spawns the entity in the world
			w.spawn(tospawn.get());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to load Object");
		}
		
	}
	
	/*
	 * Gets an entity/object from the given json
	 * */
	private Optional<PSObject> getObjectFromJson(JSONObject object){
		PSObject tospawn = null;
		
		switch (object.getString("type")) {
		//EntitySlime
		case "s":
			tospawn = new EntitySlime(object.getInt("x"), object.getInt("y"));
			break;
		//EntityChest
		case "c":
			tospawn = new EntityChest(object.getInt("x"), object.getInt("y"), Item.getItemByID(object.getInt("itemid")).get());
			break;
		//Wall
		case "w":
			tospawn = new ObjectWall(object.getInt("x"), object.getInt("y"),object.getInt("width"));
			break;
		//Item
		case "i":
			tospawn = new EntityItem(Item.getItemByID(object.getInt("itemid")).get(), object.getInt("y"), object.getInt("x"));
			break;
		//Fireball
		case "f":
			tospawn = new EntityFireball(object.getInt("y"), object.getInt("x"),object.getInt("m"));
			break;
		}
		
		return Optional.ofNullable(tospawn);
	}

	/**
	 * @return the instance
	 */
	public static final WorldLoader getInstance() {
		return instance;
	}
	
}
