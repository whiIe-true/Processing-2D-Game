package de.whiletrue.processinggame.game.ingame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.whiletrue.processinggame.entitys.PSEntity;
import de.whiletrue.processinggame.entitys.PSEntity.LoadFrame;
import de.whiletrue.processinggame.entitys.living.EntitySlime;
import de.whiletrue.processinggame.entitys.notliving.EntityChest;
import de.whiletrue.processinggame.entitys.notliving.EntityFireball;
import de.whiletrue.processinggame.entitys.notliving.EntityItem;
import de.whiletrue.processinggame.entitys.notliving.EntityWall;
import de.whiletrue.processinggame.game.Game;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class WorldLoader {
	
	private static WorldLoader instance;
	private static Map<String, Class<? extends PSEntity>> loadNames = new HashMap<String, Class<? extends PSEntity>>();
	static{
		loadNames.put("wall", EntityWall.class);
		loadNames.put("slime", EntitySlime.class);
		loadNames.put("chest", EntityChest.class);
		loadNames.put("fireball", EntityFireball.class);
		loadNames.put("item", EntityItem.class);
	}
	
	public WorldLoader() {
		instance = this;
	}
	
	public void saveWorld(World world,File outputFile) {
		JSONArray actions = new JSONArray();
		//Iterates over all existing objects
		for(PSEntity obj : world.getObjects()) {
			JSONObject json = new JSONObject();
			//Sets the action to spawn a object (so)
			json.setString("action", "so");
			
			JSONObject entdata = new JSONObject();
			{
				entdata.setString("type", getNameByClass(obj.getClass()));
				entdata.setJSONObject("data", obj.save().toJson());
			}
			//Gets the saved data from the frame
			json.setJSONObject("data", entdata);
			//Appends the action to the array of actions
			actions.append(json);
		}
		
		//Creates the player entity
		JSONObject playerJson = new JSONObject();
		{
			//Sets the action to spawnPlayer or more likely updatePlayer
			playerJson.setString("action", "sp");
			//Appends the saved data from the player
			playerJson.setJSONObject("data", world.getPlayer().save().toJson());
			//Adds the spawnPlayer action to the array of actions
			actions.append(playerJson);
		}
		
		//Prints the full stack to the file
		this.printJsonToFile(outputFile, actions);
		
	}
	
	/*
	 * Loads a world frame a json file
	 * */
	public World loadWorld(File inputWorldFile) {
		
		//Creates the world
		World w = new World();
		
		//Converts the world file into a json
		JSONArray object = this.loadJsonFromFile(inputWorldFile);
		
		//Iterates over every action
		loop:for(int i = 0; i < object.size(); i++) {
			Object obj = object.get(i);
			//Checks if the key is a valid object
			if(!(obj instanceof JSONObject))
				continue;
			
			JSONObject json = (JSONObject) obj;
			
			//Checks the action
			if(!json.hasKey("action")||!(json.get("action") instanceof String))
				continue;
			
			//Checks if the action has data
			if(!json.hasKey("data")||!(json.get("data") instanceof JSONObject))
				continue;
			
			//Gets the action
			switch (json.getString("action")) {
			//SpawnObject
			case "so":
				//Gets the object that should spawn
				Optional<PSEntity> tospawn = this.spawnObject(json.getJSONObject("data"));
				//Checks if the object can spawn
				if(!tospawn.isPresent())
					continue loop;
				
				w.spawn(tospawn.get());
				break;
			//Spawn Player
			case "sp":
				//Spawns the player
				this.spawnPlayer(json.getJSONObject("data"));
				break;
			}
		}
		
		return w;
	}
	
	/*
	 * Spawns and initalizes the player
	 * */
	private void spawnPlayer(JSONObject data) {
		//Inits the player
		((StateIngame) Game.getInstance().getState()).getPlayer().init(new LoadFrame(data));
	}
	
	/*
	 * Spawns an object
	 * */
	private Optional<PSEntity> spawnObject(JSONObject data) {
		
		//Checks if the given type is present
		if(!data.hasKey("type")||!(data.get("type") instanceof String))
			return Optional.empty();
		
		//Checks if the given data is present
		if(!data.hasKey("data")||!(data.get("data") instanceof JSONObject))
			return Optional.empty();
		
		//Creates the object
		try {
			//Generates the object
			PSEntity obj = loadNames.get(data.getString("type")).newInstance();
			//Inits the object
			obj.init(new LoadFrame(data.getJSONObject("data")));
			//Returns the loaded object
			return Optional.of(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Returns that an error occured while loading the entity
		return Optional.empty();
	}
	
	/*
	 * Returns a jsonobject from a file
	 * */
	private JSONArray loadJsonFromFile(File file) {
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String out="",ln;
			
			while((ln=br.readLine())!=null)
				out+=ln;
			
			//Parses the json
			JSONArray json = JSONArray.parse(out);
			//Checks if the json is loaded completely
			if(json==null)
				throw new NullPointerException();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			//Returns a empty json object
			return new JSONArray();
		}
	}
	
	/*
	 * Prints a given json object to a file
	 * */
	private void printJsonToFile(File file,JSONArray obj) {
		try(BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
			br.write(obj.toString());
			br.flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to save world.");
		}
	}
	
	public static String getNameByClass(Class<? extends PSEntity> clazz) {
		Optional<String> out = loadNames.entrySet().stream().filter(i->i.getValue().equals(clazz)).map(i->i.getKey()).findAny();
		return out.isPresent()?out.get():null;
	}
	
	/**
	 * @return the instance
	 */
	public static final WorldLoader getInstance() {
		return instance;
	}
	
}
