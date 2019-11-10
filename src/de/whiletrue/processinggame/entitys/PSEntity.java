package de.whiletrue.processinggame.entitys;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.logic.Hitbox;
import de.whiletrue.processinggame.logic.Physics;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.rendering.animations.Animation;
import de.whiletrue.processinggame.utils.Items;
import processing.data.JSONObject;

public abstract class PSEntity{

	protected StateIngame state;
	protected Renderer renderer;
	
	protected Animation animations;
	protected Physics physics;
	protected Hitbox hitbox;
	
	
	public PSEntity() {
		//References some stuff
		this.state = (StateIngame) Game.getInstance().getState();
		this.renderer = Renderer.getInstance();
		
		//Creates the animations and physics
		this.animations = new Animation();
		this.physics = new Physics();
	}
	
	/*
	 * Function that loads the entity and should be used
	 * */
	public void init(LoadFrame loadframe) {
		loadframe.loadPhysics(this.physics);
		loadframe.loadAnimations(this.animations);
	}
	
	/*
	 * Function that saves the entity and should be used
	 * */
	public LoadFrame save() {
		LoadFrame lf = new LoadFrame();
		lf.savePhysics(this.physics);
		lf.saveAnimations(this.animations);
		return lf;
	}

	/*
	 * Function that renders the entity and should be used
	 * */
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Gets the entity cords
		int x = this.physics.getX(),y = this.physics.getY();
		
		//Renders the skin
		this.animations.renderAt(x, y,this.hitbox.getScale());
		//Checks if debugrendering is enabled
		if(Game.getInstance().getSettings().getBool("showHitboxes"))
			this.hitbox.renderHitbox(x, y);
	}
	
	/*
	 * Function that handles every tick and should be used
	 * */
	public void handleTick() {
		this.physics.handleTick();
		this.animations.onTick();
	}

	/*
	 * Returns if the entity is colliding with another entity
	 * */
	public boolean isEntityColliding(PSEntity entity) {
		return this.hitbox.areBoxesColliding(this.physics.getX(), this.physics.getY(), entity.physics.getX(), entity.physics.getY(), entity.hitbox);
	}
	
	/*
	 * Returns the distance between two entitys
	 * */
	public double distanceTo(PSEntity entity) {
		return Math.sqrt(Math.pow(this.physics.getX()-entity.physics.getX(), 2)+Math.pow(this.physics.getY()-entity.physics.getY(), 2));
	}
	
	/**
	 * @return the physics
	 */
	public final Physics getPhysics() {
		return this.physics;
	}
	
	/**
	 * @returns the hitbox
	 * */
	public Hitbox getHitbox() {
		return this.hitbox;
	}

	/**
	 * @return the animations
	 */
	public final Animation getAnimations() {
		return this.animations;
	}
	
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
