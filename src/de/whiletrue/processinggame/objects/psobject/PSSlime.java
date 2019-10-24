package de.whiletrue.processinggame.objects.psobject;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Animation.EnumSkinDirection;

public class PSSlime extends PSEntity{

	public PSSlime(Game game, Renderer renderer, int x, int y) {
		super(game,renderer);

		//Loads the hitbox
		this.hitbox = new Hitbox(16, 16, 2.5);
		
		//Loads the physics
		this.physics.init(this.hitbox,x, y,.05,.2);
		this.physics.randomMotion();
		
		//Load the skin
		this.animations.init(renderer, "idle", 14);
		this.animations.loadAnimations("idle", "rsc/slime/idle.png");
		this.animations.loadAnimations("falling", "rsc/slime/falling.png");
		this.animations.start();
		
	}
	
	@Override
	public void handleTick() {
		String anim = this.physics.isOnground()?"idle":"falling";
		//Checks if the new animation is diffrent
		if(!anim.equals(this.animations.getIdleAnimation())) {
			//Sets the new animation and resets the ticks
			this.animations.setIdleAnimation(anim);
			this.animations.resetIdleticks();
		}
		
		//Checks if the slime is onground
		if(this.physics.isOnground()) {
			//Lets the slime jump towards the player
			this.physics.pushX((this.game.getPlayer().getPhysics().getX()-this.physics.getX())>1?1:-1);
			this.physics.pushY(-2);
		}
		//Sets the direction facing
		this.animations.setSkinDirection(this.game.getPlayer().getPhysics().getX()>this.physics.getX()?EnumSkinDirection.LEFT:EnumSkinDirection.RIGHT);
		
		//Calls the fallback
		super.handleTick();
	}
}
