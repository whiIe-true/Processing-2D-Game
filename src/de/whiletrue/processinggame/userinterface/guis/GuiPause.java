package de.whiletrue.processinggame.userinterface.guis;

import de.whiletrue.processinggame.game.Game;
import de.whiletrue.processinggame.game.Settings;
import de.whiletrue.processinggame.objects.entitys.living.EntitySlime;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;
import de.whiletrue.processinggame.userinterface.components.CompoundCheckbox;
import de.whiletrue.processinggame.userinterface.components.CompoundSlider;

public class GuiPause extends DefaultGui{

	private float scale = .2f;
	private Settings settings;
	
	public GuiPause(Game game,Renderer renderer) {
		super(game,renderer);
		this.settings = game.getSettings();
	}
	
	@Override
	public GuiComponent[] addComponents() {
		
		
		CompoundSlider jumpheight = new CompoundSlider(this.game,this.renderer, this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*0,this.game.getWidth()/8*3-20,40,2,10,this.settings.jumpHeight,i->{
			this.settings.jumpHeight=i;
			return "Jumphight: "+i;
		});
		
		CompoundSlider speed = new CompoundSlider(this.game,this.renderer, this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*1,this.game.getWidth()/8*3-20,40,2,10,this.settings.speed,i->{
			this.settings.speed=i;
			return "Speed: "+i;
		});
		
		CompoundSlider size = new CompoundSlider(this.game,this.renderer, this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*2,this.game.getWidth()/8*3-20,40,1,10,this.settings.size,i->{
			this.settings.size=i;
			return "Size: "+i;
		});
		
		CompoundSlider range = new CompoundSlider(this.game,this.renderer, this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*3,this.game.getWidth()/8*3-20,40,1,10,this.settings.range,i->{
			this.settings.range=i;
			return "Range: "+i;
		});

		CompoundButton close = new CompoundButton(this.game,this.renderer, this.game.getWidth()/2-100, this.game.getHeight()/8*6, 200, 50, i->{
			Game.getInstance().openGui(null);
			return "Close";
		});
		
		CompoundButton spawnSlime = new CompoundButton(this.game,this.renderer, this.game.getWidth()/2+20, this.game.getHeight()/8+10+(20+40)*0, 300, 40, i->{
			if(i!=-1)
				this.game.addObject(new EntitySlime(this.game,this.renderer, this.game.getPlayer().getPhysics().getX(), this.game.getPlayer().getPhysics().getY()));
			return "Spawn Slime";
		});

		CompoundCheckbox showHitboxes = new CompoundCheckbox(this.game,this.renderer, this.game.getWidth()/2+20, this.game.getHeight()/8+10+(20+40)*1, 40,40, this.game.getSettings().showHitboxes, i->{
			this.game.getSettings().showHitboxes=i;
			return "Show Hitboxes";
		});
		
		CompoundCheckbox showOverlay = new CompoundCheckbox(this.game,this.renderer, this.game.getWidth()/2+20, this.game.getHeight()/8+10+(20+40)*2, 40,40, this.game.getSettings().renderOverlay, i->{
			this.game.getSettings().renderOverlay=i;
			return "Show Overlay";
		});
		
		return new GuiComponent[] {close,jumpheight,speed,size,range,spawnSlime,showHitboxes,showOverlay};
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderRectWithStroke(this.game.getWidth()/8*this.scale, this.game.getHeight()/8*this.scale, this.game.getWidth()/8*6*this.scale, this.game.getHeight()/8*6*this.scale, 0, 130, 0, 3);
		}
		//Closes the matrix
		this.renderer.pop();
		
		super.handleRender(mouseX, mouseY, mousePressed);
	}
	
	@Override
	public void handleTick() {
		//Checks if the scale is to large
		if(this.scale<1)
			//Increases the scale
			this.scale+=.1;
		
		super.handleTick();
	}
	
}
