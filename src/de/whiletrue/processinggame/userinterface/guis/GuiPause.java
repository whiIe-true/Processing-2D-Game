package de.whiletrue.processinggame.userinterface.guis;

import de.whiletrue.processinggame.Game;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.entitys.EntityChest;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
import de.whiletrue.processinggame.objects.entitys.living.EntitySlime;
import de.whiletrue.processinggame.player.Settings;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;
import de.whiletrue.processinggame.userinterface.components.CompoundCheckbox;
import de.whiletrue.processinggame.userinterface.components.CompoundList;
import de.whiletrue.processinggame.userinterface.components.CompoundSlider;
import de.whiletrue.processinggame.utils.Items;

public class GuiPause extends DefaultGui{

	private float scale = .2f;
	
	public GuiPause() {
		super(true);
	}
	
	@Override
	public GuiComponent[] addComponents() {
		
		//Shorts the game width and height
		int w = this.game.getWidth(),h = this.game.getHeight();;
		
		CompoundSlider jumpheight = new CompoundSlider(w/8+10, h/8+10+(20+40)*0,w/8*3-20,40,2,10,Settings.jumpHeight,i->{
			Settings.jumpHeight=i;
			return "Jumphight: "+i;
		});
		
		CompoundSlider speed = new CompoundSlider(w/8+10, h/8+10+(20+40)*1,w/8*3-20,40,2,10,Settings.speed,i->{
			Settings.speed=i;
			return "Speed: "+i;
		});
		
		CompoundSlider maxhealth = new CompoundSlider(w/8+10, h/8+10+(20+40)*2,w/8*3-20,40,10,1000,Settings.maxHealth,i->{
			Settings.maxHealth=i;
			return "MaxHealth: "+i;
		});
		
		CompoundSlider range = new CompoundSlider(w/8+10, h/8+10+(20+40)*3,w/8*3-20,40,1,10,Settings.range,i->{
			Settings.range=i;
			return "Range: "+i;
		});
		
		CompoundSlider damage = new CompoundSlider(w/8+10, h/8+10+(20+40)*4,w/8*3-20,40,20,100,Settings.damage,i->{
			Settings.damage=i;
			return "Damage: "+i;
		});

		CompoundButton close = new CompoundButton(w/2-100, h/8*6, 200, 50, i->{
			Game.getInstance().openGui(null);
			return "Close";
		});
		
		CompoundCheckbox showHitboxes = new CompoundCheckbox(w/2+20, h/8+10+(20+40)*1, 40,40, Settings.showHitboxes, i->{
			Settings.showHitboxes=i;
			return "Show Hitboxes";
		});
		
		CompoundList spawnList = new CompoundList(w/2+20, h/8+10+(20+40)*0, 300, 40,false,"Spawn",(id,btn)->{
			PSEntity spawn = null;
			//Shorts players x and y
			int x = this.game.getPlayer().getPhysics().getX(),y = this.game.getPlayer().getPhysics().getY();
			
			switch (id) {
			case 0:
				spawn = new EntitySlime(x, y-100);
				break;
			case 1:
				spawn = new EntityChest(x, y, Items.egg);
				break;
			case 2:
				spawn = new EntityItem(Items.key, x, y);
				break;
			case 3:
				spawn = new EntityItem(Items.ring_of_flying, x, y);
				break;
			case 4:
				spawn = new EntityItem(Items.egg, x, y);
				break;
			}
			//Checks if a entity is given
			if(spawn!=null)
				//Spawns that entity
				this.game.getWorld().spawn(spawn);
		},"Slime","Chest","Item/Key","Item/Ring of flying","Item/Egg");
		
		CompoundButton spawntp = new CompoundButton(w/2+20, h/8+10+(20+40)*2,200,40, btnid->{
			if(btnid!=-1) {
				//Teleports the player back to the spawn
				this.game.getPlayer().teleportSpawn();
			}
			return "Tp Spawn";
		});
		
		return new GuiComponent[] {close,jumpheight,speed,range,showHitboxes,spawnList,spawntp,damage,maxhealth};
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
