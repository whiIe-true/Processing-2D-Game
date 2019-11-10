package de.whiletrue.processinggame.game.ingame.guis;

import java.io.File;
import java.util.Arrays;

import de.whiletrue.processinggame.entitys.PSEntity;
import de.whiletrue.processinggame.entitys.living.EntitySlime;
import de.whiletrue.processinggame.entitys.notliving.EntityChest;
import de.whiletrue.processinggame.entitys.notliving.EntityItem;
import de.whiletrue.processinggame.game.ingame.StateIngame;
import de.whiletrue.processinggame.game.ingame.WorldLoader;
import de.whiletrue.processinggame.game.startmenu.StateStartMenu;
import de.whiletrue.processinggame.userinterface.DefaultGui;
import de.whiletrue.processinggame.userinterface.GuiComponent;
import de.whiletrue.processinggame.userinterface.components.CompoundButton;
import de.whiletrue.processinggame.userinterface.components.CompoundCheckbox;
import de.whiletrue.processinggame.userinterface.components.CompoundList;
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
			Object[] parameters = null;
			Class<? extends PSEntity> entity = null;
			//Shorts players x and y
			int x = this.state.getPlayer().getPhysics().getX(),y = this.state.getPlayer().getPhysics().getY();
			
			//Switches what entity should be spawned
			switch (id) {
			case 0:
				entity = EntitySlime.class;
				parameters = new Object[] {"x",x,"y",y,"health",100,"dead",false};
				break;
			case 1:
				entity = EntityChest.class;
				parameters = new Object[] {"x",x,"y",y,"open",false,"item",4};
				break;
			}
			//Checks if everything needed is given
			if(entity==null||parameters==null)
				return;
			//Spawns that entity
			this.state.getWorld().spawnEntity(entity, parameters);
		},"Slime","Chest");
		
		CompoundList spawnItems = new CompoundList(w/2-150, h/8+10+(20+40)*1, 300, 40,false,"Items",(id,btn)->{
			//Increases the id, because the first item is not listed
			id++;
			
			//Shorts players x and y
			int x = this.state.getPlayer().getPhysics().getX(),y = this.state.getPlayer().getPhysics().getY();
			
			//Gets the item with the matching id
			Items itm = Items.getItemByID(id);
			
			//Checks if the item is invalid
			if(itm.equals(Items.NONE))
				return;
			
			//Spawns the entity
			this.state.getWorld().spawnEntity(EntityItem.class, "x",x,"y",y,"item",itm);
		},Arrays.stream(Items.values()).filter(i->i.getId()!=0).sorted((i1,i2)->i1.getId()>i2.getId()?1:-1).map(i->i.getName()).toArray(String[]::new));
		
		CompoundButton spawntp = new CompoundButton(w/2-100, h/8+10+(20+40)*3,200,40, btnid->{
			if(btnid!=-1) {
				//Teleports the player back to the spawn
				this.state.getPlayer().teleportSpawn();
			}
			return "Tp Spawn";
		});
		
		CompoundButton save = new CompoundButton(w/2-100, h/8+10+(20+40)*4,200,40, btnid->{
			if(btnid!=-1) {
				//Saves the game to the world.json file
				File saveFile = new File("world.json");
				WorldLoader.getInstance().saveWorld(this.state.getWorld(), saveFile);
			}
			return "Save World";
		});
		
		return new GuiComponent[] {endgame,showHitboxes,spawnList,spawntp,spawnItems,close,save};
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
