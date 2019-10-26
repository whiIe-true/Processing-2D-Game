package de.whiletrue.processinggame.userinterface.guis;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.Settings;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
import de.whiletrue.processinggame.objects.entitys.living.EntitySlime;
import de.whiletrue.processinggame.rendering.Renderer;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;
import de.whiletrue.processinggame.userinterface.components.CompoundCheckbox;
import de.whiletrue.processinggame.userinterface.components.CompoundList;
import de.whiletrue.processinggame.userinterface.components.CompoundSlider;
import de.whiletrue.processinggame.utils.Items;

public class GuiPause extends DefaultGui{

	private float scale = .2f;
	private Settings settings;
	
	public GuiPause(Game game,Renderer renderer) {
		super(game,renderer);
		this.settings = game.getSettings();
	}
	
	@Override
	public GuiComponent[] addComponents() {
		
		
		CompoundSlider jumpheight = new CompoundSlider(this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*0,this.game.getWidth()/8*3-20,40,2,10,this.settings.jumpHeight,i->{
			this.settings.jumpHeight=i;
			return "Jumphight: "+i;
		});
		
		CompoundSlider speed = new CompoundSlider(this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*1,this.game.getWidth()/8*3-20,40,2,10,this.settings.speed,i->{
			this.settings.speed=i;
			return "Speed: "+i;
		});
		
		CompoundSlider size = new CompoundSlider(this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*2,this.game.getWidth()/8*3-20,40,1,10,this.settings.size,i->{
			this.settings.size=i;
			return "Size: "+i;
		});
		
		CompoundSlider range = new CompoundSlider(this.game.getWidth()/8+10, this.game.getHeight()/8+10+(20+40)*3,this.game.getWidth()/8*3-20,40,1,10,this.settings.range,i->{
			this.settings.range=i;
			return "Range: "+i;
		});

		CompoundButton close = new CompoundButton(this.game.getWidth()/2-100, this.game.getHeight()/8*6, 200, 50, i->{
			Game.getInstance().openGui(null);
			return "Close";
		});
		
		CompoundCheckbox showHitboxes = new CompoundCheckbox(this.game.getWidth()/2+20, this.game.getHeight()/8+10+(20+40)*1, 40,40, this.game.getSettings().showHitboxes, i->{
			this.game.getSettings().showHitboxes=i;
			return "Show Hitboxes";
		});
		
		CompoundList spawnList = new CompoundList(this.game.getWidth()/2+20, this.game.getHeight()/8+10+(20+40)*0, 300, 40,"Spawn",(id,btn)->{
			PSEntity spawn = null;
			switch (id) {
			case 0:
				spawn = new EntitySlime(this.game.getPlayer().getPhysics().getX(), this.game.getPlayer().getPhysics().getY());
				break;
			case 1:
				spawn = new EntityItem(Items.key, this.game.getPlayer().getPhysics().getX(), this.game.getPlayer().getPhysics().getY());
				break;
			case 2:
				spawn = new EntityItem(Items.ring, this.game.getPlayer().getPhysics().getX(), this.game.getPlayer().getPhysics().getY());
				break;
			}
			//Checks if a entity is given
			if(spawn!=null)
				//Spawns that entity
				this.game.addObject(spawn);
		},"Slime","Item/Key","Item/Ring");
		
		return new GuiComponent[] {close,jumpheight,speed,size,range,showHitboxes,spawnList};
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
