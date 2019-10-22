package de.whiletrue.processinggame.objects.psobject;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.utils.Hitbox;
import de.whiletrue.processinggame.utils.Skin.EnumSkinDirection;

public class PSSlime extends PSEntity{

	public PSSlime(Game game, Renderer renderer, int x, int y) {
		super(game,renderer);

		//Loads the hitbox
		this.hitbox = new Hitbox(16, 16, 2);
		
		//Loads the physics
		this.physics.init(this.hitbox,x, y,.05,.2);
		this.physics.randomMotion();
		
		//Load the skin
		this.skin.init(game,renderer,this.hitbox, "idle", 14);
		this.skin.loadAnimations("idle", "rsc/slime/idle.png");
		this.skin.loadAnimations("falling", "rsc/slime/falling.png");
		this.skin.start();
		
	}
	
	@Override
	public void handleTick() {
		String anim = this.physics.isOnground()?"idle":"falling";
		//Checks if the new animation is diffrent
		if(!anim.equals(this.skin.getIdleAnimation())) {
			//Sets the new animation and resets the ticks
			this.skin.setIdleAnimation(anim);
			this.skin.resetIdleticks();
		}
		
		//Checks if the slime is onground
		if(this.physics.isOnground()) {
			//Lets the slime jump towards the player
			this.physics.pushX((this.game.getPlayer().getPhysics().getX()-this.physics.getX())>1?1:-1);
			this.physics.pushY(-2);
		}
		//Sets the direction facing
		this.skin.setSkinDirection(this.game.getPlayer().getPhysics().getX()>this.physics.getX()?EnumSkinDirection.LEFT:EnumSkinDirection.RIGHT);
		
		//Calls the fallback
		super.handleTick();
	}
}
