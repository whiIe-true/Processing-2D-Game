package de.whiletrue.processinggame.game.ingame.guis;

import java.util.Optional;

import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.game.startmenu.StateStartMenu;
import de.whiletrue.processinggame.objects.PSEntity;
import de.whiletrue.processinggame.objects.entitys.EntityChest;
import de.whiletrue.processinggame.objects.entitys.EntityItem;
import de.whiletrue.processinggame.objects.entitys.living.EntitySlime;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;
import de.whiletrue.processinggame.userinterface.components.CompoundCheckbox;
import de.whiletrue.processinggame.userinterface.components.CompoundList;
import de.whiletrue.processinggame.utils.Item;
import de.whiletrue.processinggame.utils.Items;

public class GuiPause extends DefaultGui{

	private float scale = .2f;
	private StateIngame state;
	
	public GuiPause(StateIngame state) {
		super(true);
		this.state = state;
	}
	
	@Override
	public GuiComponent[] addComponents() {
		
		//Shorts the game width and height
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		CompoundButton close = new CompoundButton(w/2-225, h/8*6, 200, 50, i->{
			if(i!=-1)
				this.state.openGui(null);
			return "Close";
		});
		
		CompoundButton endgame = new CompoundButton(w/2+25, h/8*6, 200, 50, i->{
			if(i!=-1)
				this.game.changeState(new StateStartMenu());
			return "End game";
		});
		
		CompoundCheckbox showHitboxes = new CompoundCheckbox(w/2-140, h/8+10+(20+40)*2, 40,40, this.game.getSettings().getBool("showHitboxes"), i->{
			this.game.getSettings().edit("showHitboxes", i);
			return "Show Hitboxes";
		});
		
		CompoundList spawnList = new CompoundList(w/2-150, h/8+10+(20+40)*0, 300, 40,false,"Spawn",(id,btn)->{
			PSEntity spawn = null;
			//Shorts players x and y
			int x = this.state.getPlayer().getPhysics().getX(),y = this.state.getPlayer().getPhysics().getY();
			
			switch (id) {
			case 0:
				spawn = new EntitySlime(x, y-100);
				break;
			case 1:
				spawn = new EntityChest(x, y, Items.egg);
				break;
			}
			//Checks if a entity is given
			if(spawn!=null)
				//Spawns that entity
				this.state.getWorld().spawn(spawn);
		},"Slime","Chest");
		
		CompoundList spawnItems = new CompoundList(w/2-150, h/8+10+(20+40)*1, 300, 40,false,"Items",(id,btn)->{
			//Shorts players x and y
			int x = this.state.getPlayer().getPhysics().getX(),y = this.state.getPlayer().getPhysics().getY();
			
			//Gets the item with the matching id
			Optional<Item> itm = Item.getRegisteredItems().stream().filter(i->i.getId()==id).findFirst();
			//Checks if that item exists
			if(!itm.isPresent())
				return;
			this.state.getWorld().spawn(new EntityItem(itm.get(), x, y));
		},Item.getRegisteredItems().stream().sorted((i1,i2)->i1.getId()>i2.getId()?1:-1).map(i->i.getName()).toArray(String[]::new));
		
		CompoundButton spawntp = new CompoundButton(w/2-100, h/8+10+(20+40)*3,200,40, btnid->{
			if(btnid!=-1) {
				//Teleports the player back to the spawn
				this.state.getPlayer().teleportSpawn();
			}
			return "Tp Spawn";
		});
		
		return new GuiComponent[] {endgame,showHitboxes,spawnList,spawntp,spawnItems,close};
	}
	
	@Override
	public void handleRender(int mouseX, int mouseY, boolean mousePressed) {
		//Shorts some variables
		int w = this.renderer.window.width,h = this.renderer.window.height;
		
		//Opens the matrix
		this.renderer.push();
		{
			this.renderer.renderRectWithStroke(w/8*this.scale, h/8*this.scale, w/8*6*this.scale, h/8*6*this.scale, 0, 130, 0, 3);
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
