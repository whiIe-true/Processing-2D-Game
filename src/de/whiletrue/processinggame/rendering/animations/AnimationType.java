package de.whiletrue.processinggame.rendering.animations;

import de.whiletrue.processinggame.rendering.Renderer;

public class AnimationType {

	private AnimationFrame[] frames;
	private int frame=0,ticks,ticksperframe;
	private ReadyHandler handler;
	
	public AnimationType(Renderer renderer,String path,int ticksPerFrame,ReadyHandler handler) {
		this.frames = renderer.loadImagesSeperatedBy(path);
		this.ticksperframe = ticksPerFrame;
		this.handler = handler;
	}
	
	/*
	 * Restarts the animation
	 * */
	public void restart() {
		this.ticks = 0;
		this.frame = 0;
	}
	
	/*
	 * Updates the animation
	 * */
	public void handleTick() {
		//Checks if any animations should be played
		if(this.ticksperframe==-1)
			return;
		
		//Checks if the ticks are done
		if(++this.ticks<this.ticksperframe)
			return;
		this.ticks=0;
		//Checks if the animation is complete
		if(++this.frame<frames.length)
			return;
		//Resets the animation
		this.frame = 0;
		this.handler.execute();
	}
	
	/**
	 * @returns the current frame
	 */
	public AnimationFrame getCurrentFrame() {
		return this.frames[this.frame];
	}
	
	@FunctionalInterface
	public interface ReadyHandler{
		public void execute();
	}
}
