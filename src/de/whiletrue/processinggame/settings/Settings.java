package de.whiletrue.processinggame.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import processing.data.JSONObject;

public class Settings {

	private JSONObject loadedSettings = new JSONObject();
	
	private File saveFile = new File("settings.json");
	
	/*
	 * Trys to edit a value
	 * */
	public boolean edit(String key,Object value) {
		//Checks if that setting exists
		if(!this.loadedSettings.hasKey(key))
			return false;
		
		//Checks if the value has the same type
		if(this.loadedSettings.get(key).getClass() != value.getClass())
			return false;
		
		//Sets the new value
		this.loadedSettings.put(key, value);
		
		return true;
	}
	
	/*
	 * Sets a default value
	 * */
	public void setDefault(String key,Object value) {
		this.loadedSettings.put(key, value);
	}
	
	/*
	 * Loads the settings
	 * */
	public void loadSettings() {
		//Checks if the settings file exists
		if(!this.saveFile.exists()) {
			this.saveSettings();
			return;
		}
		
		//Trys to load the file
		try(BufferedReader br = new BufferedReader(new FileReader(this.saveFile))){
			
			//Reads all file contents
			String ln;
			StringBuffer full = new StringBuffer();
			while((ln=br.readLine())!=null)
				full.append(ln);
			
			//trys to load it as a json object
			JSONObject loaded = JSONObject.parse(full.toString());
			
			//Iterates over all keys
			for(Object key : loaded.keys())
				//Trys to edit the given values
				if(!this.edit((String)key, loaded.get((String)key))) {
					System.out.println("Settings.loadSettings()");
					System.err.printf("Failed to load value: %s",key);
				}
			
		} catch (FileNotFoundException e) {} catch (Exception e) {
			System.out.println("Settings.loadSettings()");
			System.err.printf("Failed to load Settings: %s",e.getMessage());
		};
	}
	
	/*
	 * Saves the settings
	 * */
	public void saveSettings() {
		//Checks if the file exists
		if(!this.saveFile.exists())
			try {
				//Trys to create the file
				this.saveFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Settings.saveSettings()");
				System.err.printf("Error occured: %s",e.getMessage());
			}
		
		//Trys to write to the file
		try(BufferedWriter br = new BufferedWriter(new FileWriter(this.saveFile))){
			br.write(this.loadedSettings.toString());
			br.flush();
		} catch (Exception e) {
			System.out.println("Settings.saveSettings()");
			System.err.printf("Error occured: %s",e.getMessage());
		}
	}
	
	/*
	 * Gets an integer from the settings
	 * */
	public Integer getInt(String name) {
		try {
			return this.loadedSettings.getInt(name);
		} catch (Exception e) {
			System.out.println("Settings.getInt()");
			System.err.printf("Failed to load value: %s",e.getMessage());
			return -1;
		}
	}
	
	/*
	 * Gets an integer from the settings
	 * */
	public Float getFloat(String name) {
		try {
			return this.loadedSettings.getFloat(name);
		} catch (Exception e) {
			System.out.println("Settings.getFloat()");
			System.err.printf("Failed to load value: %s",e.getMessage());
			return -1f;
		}
	}
	
	/*
	 * Gets an boolean from the settings
	 * */
	public Boolean getBool(String name) {
		try {
			return this.loadedSettings.getBoolean(name);
		} catch (Exception e) {
			System.out.println("Settings.getBool()");
			System.err.printf("Failed to load value: %s",e.getMessage());
			return false;
		}
	}
	
	/*
	 * Gets an string from the settings
	 * */
	public String getString(String name) {
		try {
			return this.loadedSettings.getString(name);
		} catch (Exception e) {
			System.out.println("Settings.getString()");
			System.err.printf("Failed to load value: %s",e.getMessage());
			return "";
		}
	}
}
