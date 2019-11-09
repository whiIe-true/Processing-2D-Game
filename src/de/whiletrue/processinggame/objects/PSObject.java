package de.whiletrue.processinggame.objects;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.logic.Physics;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.rendering.animations.Animation;
import de.whiletrue.processinggame.utils.Items;
import processing.data.JSONObject;

public abstract class PSObject{
	
	protected StateIngame state;
	protected Renderer renderer;
	
	public PSObject() {
		//References some stuff
		this.state = (StateIngame) Game.getInstance().getState();
		this.renderer = Renderer.getInstance();
	}
	
	public abstract void init(LoadFrame loadframe);
	public abstract LoadFrame save();
	
	public void handleTick() {}
	
	public void handleRender(int mouseX,int mouseY,boolean mousePressed) {}	

	public static class LoadFrame{
	
		private JSONObject frame;
		
		/*
		 * Loads the given frame and returns default values for datasets that doesnt exist
		 * */
		public LoadFrame(JSONObject frame) {
			this.frame = frame;
		}
		
		public LoadFrame() {
			this(new JSONObject());
		}
		
		public int getInt(String key) {
			return (this.frame.hasKey(key)&&this.frame.get(key) instanceof Number)?((Number)this.frame.get(key)).intValue():0;
		}
		public String getString(String key) {
			return (this.frame.hasKey(key)&&this.frame.get(key) instanceof String)?this.frame.getString(key):"";
		}
		public Items getItem(String key) {
			//Checks if the frame contains the key and if the key has the right type
			if(!this.frame.hasKey(key)||!(this.frame.get(key) instanceof Integer))
				return Items.NONE;
			
			//Returns the item
			return Items.getItemByID(this.frame.getInt(key));
		}
		public double getDouble(String key) {
			return (this.frame.hasKey(key)&&this.frame.get(key) instanceof Number)?((Number)this.frame.get(key)).doubleValue():0;
		}
		public boolean getBool(String key) {
			return (this.frame.hasKey(key)&&this.frame.get(key) instanceof Boolean)?this.frame.getBoolean(key):true;
		}
		public void loadPhysics(Physics physics) {
			physics.teleport(this.getInt("x"), this.getInt("y"));
			physics.setMotionX(this.getDouble("motionx"));
			physics.setMotionY(this.getDouble("motiony"));
		}
		public void loadAnimations(Animation animations) {
			animations.setReverse(this.getBool("direction"));
		}	
	
		public void saveAnimations(Animation animations) {
			this.frame.setBoolean("direction", animations.isReverse());
		}
		public void savePhysics(Physics physics) {
			this.frame.setInt("x", physics.getX());
			this.frame.setInt("y", physics.getY());
			this.frame.setDouble("motionx", physics.getMotionX());
			this.frame.setDouble("motiony", physics.getMotionY());
		}
		public void setInt(String key,int value) {
			this.frame.setInt(key, value);
		}
		public void setDouble(String key,double value) {
			this.frame.setDouble(key, value);
		}
		public void setBool(String key,boolean value) {
			this.frame.setBoolean(key, value);
		}
		public void setString(String key,String value) {
			this.frame.setString(key, value);
		}
		public void setItem(String key,Items value) {
			this.frame.setInt(key, value.getId());
		}
		
		public JSONObject toJson() {
			return this.frame;
		}
	}
}
