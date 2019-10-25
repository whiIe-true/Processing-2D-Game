package de.whiletrue.processinggame.objects.entitys.items;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.objects.PSItem;
import de.whiletrue.processinggame.rendering.Renderer;

public class ItemKey extends PSItem{

	public ItemKey(Game game, Renderer renderer,int x,int y) {
		super(game, renderer, 30, 30, 2);
		this.animations.init(renderer, "idle", 0);
		this.animations.loadAnimations("idle", "rsc/items/key.png");
		this.animations.start();
		
		this.physics.init(this.hitbox, x, y, .1, .2);
		
		this.physics.randomMotion(10);
	}
}

